package scraper

import data.Course
import data.CourseDef
import data.CourseDef.Lecturer
import data.Repository
import data.Repository.Minor.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import tornadofx.runAsync
import tornadofx.runLater
import java.lang.IllegalStateException
import java.time.DayOfWeek

data class CourseData(var startIndex: Int, var duration: Int, var title: String, var lecturer: String, var classroom: String) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is CourseData) return false
        return title == other.title && lecturer == other.lecturer && classroom == other.classroom
    }

    /* Automatski generisan hashCode */
    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + lecturer.hashCode()
        result = 31 * result + classroom.hashCode()
        return result
    }
}

fun fetchCourseListTask(minor: Repository.Minor, years: List<Repository.YearOfStudy>) = runAsync {

    years.flatMap { year ->
        // U ovom koraku, prikupljaju se svi sufiksi veb strana rasporeda, u jednu listu : List<String>
        when (year) {
            Repository.YearOfStudy.FIRST -> when (minor) {
                L, M, R, N, V -> forms(6..13)
                I, AI -> listOf("index.html", *forms(1..5).toTypedArray())
            }
            Repository.YearOfStudy.SECOND -> when (minor) {
                L, R -> forms(20..23)
                M, N, V -> forms(18, 19)
                I, AI -> forms(14..17)
            }
            Repository.YearOfStudy.THIRD -> when (minor) {
                L -> forms(26)
                M -> forms(27)
                R -> forms(29)
                N -> forms(28)
                V -> forms(30)
                I, AI -> forms(24, 25)
            }
            Repository.YearOfStudy.FOURTH -> when (minor) {
                L -> forms(33)
                M -> forms(34)
                R -> forms(36)
                N -> forms(35)
                V -> forms(37)
                I, AI -> forms(31)
            }

            Repository.YearOfStudy.MASTERS -> when (minor) {
                L -> forms(39)
                M -> forms(40)
                R -> forms(42)
                N -> forms(41)
                V -> forms(43)
                I, AI -> forms(38)
            }
        }
    }.flatMap {
        Jsoup.connect("http://poincare.matf.bg.ac.rs/~kmiljan/raspored/sve/$it").get()
            .selectFirst("table")
            .selectFirst("tbody")
            .selectFirst("tr")
            .selectFirst("td:eq(1)")
            .selectFirst("table")
            .selectFirst("tbody")
            .children(startIndex = 2)
    }.flatMap {
        // U ovom koraku, parsiraju se prve kolone svakog reda, tako da se asocira dan u nedelji sa listom
        val dayOfWeek = when (it.selectFirst("td").text()) {
            "Понедељак" -> DayOfWeek.MONDAY
            "Уторак" -> DayOfWeek.TUESDAY
            "Среда" -> DayOfWeek.WEDNESDAY
            "Четвртак" -> DayOfWeek.THURSDAY
            "Петак" -> DayOfWeek.FRIDAY
            else -> throw IllegalStateException("Nije prepoznat dan u nedelji:${it.selectFirst("td").text()}")
        }

        val courseData = coursesFromRawData(it.children(startIndex = 1))

        courseData.map { data -> Pair(dayOfWeek, data) }
    }.map {
        // Ovo će pretvoriti u klasu Course koju zapravo koristimo u projektu
        val courseData = it.second
        val type = when {
            courseData.title.contains("(вежбе)") -> {
                courseData.title = courseData.title.replace("(вежбе)", "").trim()
                Course.Type.EXERCISE
            }

            courseData.title.contains("(практикум)") -> {
                courseData.title = courseData.title.replace("(практикум)", "").trim()
                Course.Type.PRACTICUM
            }

            else -> Course.Type.LECTURE
        }

        val classroom = when {
            courseData.classroom.startsWith("ЈАГ") -> Course.Classroom.JAG
            courseData.classroom.startsWith("Н") -> Course.Classroom.N
            else -> Course.Classroom.TRG
        }

        // Usput, dodaj ovaj kurs u skup definicija kurseva
        val courseDef = CourseDef(courseData.title)
        courseDef.lecturers.add(Lecturer(courseData.lecturer))
        addCourseDefToRepository(courseDef)

        Course(
            courseData.title,
            type,
            it.first,
            classroom,
            courseData.classroom,
            courseData.startIndex,
            courseData.duration,
            courseData.lecturer
        )
    }.let { list ->
        runLater {
            Repository.courses.addAll(list)
        }
    }

}

private fun addCourseDefToRepository(courseDef: CourseDef) {
    runLater {
        val existing = Repository.courseDefs.find { it == courseDef }

        if (existing != null) {
            // Ako već postoji taj predavač, ne dodavati ga
            existing.lecturers.addUnique(courseDef.lecturers)
        } else {
            Repository.courseDefs.add(courseDef)
        }
    }
}

private fun coursesFromRawData(rawList: List<Element>): Set<CourseData> {

    var startIndex = 8

    return rawList.map {
        //Ovaj korak će dodeliti odgovarajuće početne časove i trajanja svakom od časova

        val duration = it.attr("colspan").toIntOrNull() ?: 1
        val returnVal = Triple(startIndex, duration, it)

        // Kada je neko predavanje 2h ili 3h, početni indeks treba dodatno da se poveća.
        startIndex += duration

        returnVal

    }.flatMap { triple: Triple<Int, Int, Element> ->
        // Ovo će razdvojiti časove na pojedinačne, ako se u isto vreme održava više časova na istom rasporedu
        when {

            // Ako je pronađena tabela, radi se o tabeli vežbi, ili praznoj tabeli
            triple.third.selectFirst("table") != null ->
                triple.third.selectFirst("table").selectFirst("tbody").select("tr").map {
                    Triple(triple.first, triple.second, it)
                }

            // Ako element ima `colspan` atribut, znači da je element predavanja
            triple.third.hasAttr("colspan") -> listOf(triple)

            // Nije prepoznat element
            else -> emptyList<Triple<Int, Int, Element>>()
        }
    }.filter {
        // Ovo će ukloniti "prazne" časove
        !it.third.text().isNullOrBlank()
    }.map {
        // Ovo će pretvoriti "sirovo" Triple<Int, Int, Element> u CourseData
        val innerHtml = it.third.selectFirst("small")?.html() ?: it.third.html()
        val dataList = innerHtml.split("<br>").map { str -> str.trimIndent() }
        val title = dataList[0]
        // Nekada postoji i informacija o grupi ("GR1", "GR2"...), pa ima 4 elementa umesto 3.
        val lecturer = if (dataList.size == 3) dataList[1] else dataList[2]
        val classroom = if (dataList.size == 3) dataList[2] else dataList[3]

        CourseData(it.first, it.second, title, lecturer, classroom)
    }.fold(mutableSetOf()) { acc, current ->
        // Ovo će spojiti više časova vežbi u jedan čas vežbi
        val existing = acc.find { it == current }

        if (existing != null) {
            existing.duration += current.duration
        } else {
            acc.add(current)
        }

        acc
    }
}

private fun Element.children(startIndex: Int, endIndex: Int = children().size): List<Element> {
    return children().subList(startIndex, endIndex)
}

private fun forms(vararg indices: Int) = indices.map { "form_${it.toString().padStart(3, '0')}.html" }

private fun forms(indices: IntRange) = forms(*indices.toList().toIntArray())

// Vremenska kompleksnost O(n * m), ali nije problematično za male kolekcije.
private fun  MutableCollection<CourseDef.Lecturer>.addUnique(other: Collection<CourseDef.Lecturer>) {
    other.forEach { element ->
        if (this.find { it.name == element.name } == null) {
            this.add(element)
        }
    }
}
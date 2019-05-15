package data

import gui.view.welcome.WelcomeScreenWizard
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import scheduler.Timetable
import scheduler.generateTimetablesTask
import scraper.fetchCourseListTask
import tornadofx.*
import java.io.File
import java.io.FileNotFoundException
import javax.json.Json

/** Objekat koji će služiti da se u njemu čuvaju Kolekcije podataka iz različitih izvora, uključujući
 *  podatke koje generiše korisnik, podatke sa mreže, i podatke iz baze podataka.
 *
 */
object Repository {

    /**
     * Iako ispod grma leži jednostavna String informacija, koristeći nabrojivu klasu garantujemo veću
     * bezbednost u fazi kompilacije, kao i zaobilaženje nekih greški poput slučajne greške u kucanju
     * pri sirovom upoređivanju String-ova. npr. "Informatika == informatika".
     */
    enum class Major {
        COMP_SCI, MATH, ASTRONOMY;

        override fun toString() = when (this) {
            COMP_SCI -> "Informatika"
            MATH -> "Matematika"
            ASTRONOMY -> "Astronomija"
        }
    }

    enum class Minor {
        L, M, R, N, V, I, AI;

        override fun toString() = when(this) {
            L -> "Profesor matematike i računarstva"
            M -> "Teorijska matematika i primene"
            R -> "Računarstvo i informatika"
            N -> "Primenjena matematika"
            V -> "Statistika, aktuarska matematika i finansijska matematika"
            I -> "Informatika"
            AI -> "Astroinformatika"
        }
    }

    enum class IntermediaryPauses {
        PREFER, AVOID, NONE
    }

    enum class YearOfStudy {
        FIRST, SECOND, THIRD, FOURTH, MASTERS;

        override fun toString() = when (this) {
            FIRST -> "Prva godina OAS"
            SECOND -> "Druga godina OAS"
            THIRD -> "Treća godina OAS"
            FOURTH -> "Četvrta godina OAS"
            MASTERS -> "Master studije"
        }
    }

    // Objekat kojem može globalno da se pristupi
    object StudentPreference {
        var intermediaryPauses: IntermediaryPauses = IntermediaryPauses.AVOID
    }

    val majors = observableList(Major.COMP_SCI, Major.MATH, Major.ASTRONOMY)

    var bestTimetableProperty = SimpleObjectProperty<Timetable>().apply {
        onChange {
            if (it != null) {
                saveToSavefile(it)
            }
        }
    }
    var bestTimetable: Timetable?
        get() {
            return bestTimetableProperty.value
        }
        set(value) {
            bestTimetableProperty.value = value
        }

    // Potreban je ekstraktor da bi se reagovalo na promenu svojstva unutar CourseDef
    val courseDefs: ObservableList<CourseDef> = FXCollections.observableArrayList<CourseDef> {
        arrayOf(it.selectedProperty, it.lecturers)
    }

    val selectedCourseDefs: FilteredList<CourseDef> = courseDefs.filtered(CourseDef::selected)

    val courses = observableList<Course>()

    fun generateTimetables(courses: List<Course>, intermediaryPauses: IntermediaryPauses) {
        StudentPreference.intermediaryPauses = intermediaryPauses
        generateTimetablesTask(courses)
    }

    fun updateCourseList(minor: Minor, years: List<YearOfStudy>) {
        courseDefs.clear()
        courses.clear()
        fetchCourseListTask(minor, years)
    }

    fun loadFromSavefile() = runAsync {

        try {
            val file = File("savefile.json")

            val jsonReader = Json.createReader(file.inputStream().buffered())

            val jsonObject = jsonReader.readObject()

            val timetable = Timetable()

            runLater {
                timetable.updateModel(jsonObject)
                bestTimetable = timetable
            }
        } catch (e: Exception) {
            // Ako iz bilo kog razloga ne uspe učitavanje rasporeda iz datoteke, prikaži Wizard ponovo.
            ui {
                find<WelcomeScreenWizard>().openModal()
            }
        }
    }

    private fun saveToSavefile(timetable: Timetable) = runAsync {
        if (timetable.courseList.any { it.assignedDay != null }) {
            File("savefile.json").writeText(timetable.toJSON().toPrettyString())
        }

    }
}
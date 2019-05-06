package data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import scheduler.Timetable
import scheduler.generateTimetablesTask
import scraper.fetchCourseListTask
import tornadofx.observableList

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
        L, M, R, N, V, MA, I, AF, AI;

        override fun toString() = when(this) {
            L -> "Profesor matematike i računarstva"
            M -> "Teorijska matematika i primene"
            R -> "Računrastvo i informatika"
            N -> "Primenjena matematika"
            V -> "Statistika, aktuarska matematika i finansijska matematika"
            I -> "Informatika"
            MA -> "Astronomija"
            AF -> "Astrofizika"
            AI -> "Astroinformatika"
        }
    }
    
    enum class ArrangementPreference {
        EVEN, GROUPED, NONE
    }

    enum class IntermediaryPauses {
        PREFER, AVOID, NONE
    }

    enum class YearOfStudy {
        FIRST, SECOND, THIRD, FOURTH, MASTERS, DOCTORATE;

        override fun toString() = when (this) {
            FIRST -> "Prva godina OAS"
            SECOND -> "Druga godina OAS"
            THIRD -> "Treća godina OAS"
            FOURTH -> "Četvrta godina OAS"
            MASTERS -> "Master studije"
            DOCTORATE -> "Doktorske studije"
        }
    }

    // Objekat kojem može globalno da se pristupi
    object StudentPreference {
        var intermediaryPauses: IntermediaryPauses = IntermediaryPauses.AVOID
        var arrangementPreference: ArrangementPreference = ArrangementPreference.NONE
    }

    val majors = observableList(Major.COMP_SCI, Major.MATH, Major.ASTRONOMY)
    val notifications = observableList<Notification>()
    val timetables = observableList<Timetable>()

    // Potreban je ekstraktor da bi se reagovalo na promenu svojstva unutar CourseDef
    val courseDefs: ObservableList<CourseDef> = FXCollections.observableArrayList<CourseDef> {
        arrayOf(it.selectedProperty, it.lecturers)
    }

    val selectedCourseDefs: FilteredList<CourseDef> = courseDefs.filtered(CourseDef::selected)

    val courses = observableList<Course>()

    init {
        // Test podaci
        notifications.addAll((0..10).map { Notification("Obaveštenje $it", "Neki opis") })
        timetables.add(Timetable(listOf(), listOf()))
    }

    fun generateTimetables(courses: List<Course>, intermediaryPauses: IntermediaryPauses, arrangementPreference: ArrangementPreference) {
        StudentPreference.intermediaryPauses = intermediaryPauses
        StudentPreference.arrangementPreference = arrangementPreference
        generateTimetablesTask(courses)
    }

    fun updateCourseList(minor: Minor, year: YearOfStudy) {
        courseDefs.clear()
        fetchCourseListTask(minor, year)
    }
}
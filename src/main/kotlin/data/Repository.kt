package data

import gui.view.welcome.WelcomeScreenWizard
import scheduler.Timetable
import scheduler.generateTimetablesTask
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
        L, M, R, P, S, MA, I, AF, AI;

        override fun toString() = when(this) {
            L -> "Profesor matematike i računarstva"
            M -> "Teorijska matematika i primene"
            R -> "Računrastvo i informatika"
            P -> "Primenjena matematika"
            S -> "Statistika, aktuarska matematika i finansijska matematika"
            I -> "Informatika"
            MA -> "Astronomija"
            AF -> "Astrofizika"
            AI -> "Astroinformatika"
        }
    }

    enum class TimeFramePreference {
        BEFORE_NOON, NOON, AFTERNOON, NONE
    }
    
    enum class ArrangementPreference {
        EVEN, GROUPED, NONE
    }

    enum class IntermediaryPauses {
        PREFER, AVOID, NONE
    }

    val majors = observableList(Major.COMP_SCI, Major.MATH, Major.ASTRONOMY)
    val notifications = observableList<Notification>()
    val timetables = observableList<Timetable>()

    val allAvailableCourses = observableList<CourseDef>()

    init {
        // Test podaci
        notifications.addAll((0..10).map { Notification("Obaveštenje $it", "Neki opis") })
        timetables.add(Timetable(listOf(), listOf()))
    }

    fun generateTimetables(viewModel: WelcomeScreenWizard.ViewModel) {
        generateTimetablesTask(viewModel)
    }
}
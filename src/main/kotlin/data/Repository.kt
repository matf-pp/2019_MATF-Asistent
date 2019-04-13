package data

import gui.view.welcome.WelcomeScreenWizard
import scheduler.Timetable
import scheduler.generateTimetablesTask
import scraper.fetchCourseListTask
import tornadofx.observableList
import tornadofx.runAsync
import java.sql.Connection
import java.sql.DriverManager

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

    val allAvailableCourses = observableList<Course>()

    init {
        // Test podaci
        notifications.addAll((0..10).map { Notification("Obaveštenje $it", "Neki opis") })
        timetables.add(Timetable(listOf(), listOf()))
    }

    fun updateCourseList(major: Major) {
        // TODO učitavati keširane podatke iz baze podataka ako su oni prisutni
        allAvailableCourses.clear()
        fetchCourseListTask(major)
    }

    fun generateTimetables(viewModel: WelcomeScreenWizard.ViewModel) {
        generateTimetablesTask(viewModel)
    }

    private val databaseConnection: Connection by lazy {
        // TODO program trenutno ne obrađuje slučaj kada baza podataka nije pronađena
        val dbPathname = "database.db"
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite:$dbPathname")
    }

    private fun loadFromDatabase() {
        runAsync {
            var statement = databaseConnection.createStatement()
            // TODO učitavanje iz baze podataka
        }
    }

}
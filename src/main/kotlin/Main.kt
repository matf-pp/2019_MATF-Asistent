import gui.CustomStylesheet
import gui.view.welcome.WelcomeScreenWizard
import javafx.application.Application
import scraper.fetchCourseListTask
import tornadofx.*


/** MatfAsisentApp nasleđuje klasu App (iz biblioteke TornadoFX) koja nasleđuje javafx.application.Application.
 *  Main funkcija poziva Application::launch na klasičan JavaFX način.
 */

/* TODO kontrolisati da li se inicijalno prikazuje Wizard ili MainView, na osnovu toga da li je korisnik
       uneo podatke, ili po prvi put otvara aplikaciju.
 */
class MatfAsistentApp : App(WelcomeScreenWizard::class, CustomStylesheet::class) {
    init {
        // Ovaj zadatak mora da se pokrene tek nakon što nastane nit aplikacije, da bi Platform::runLater poziv
        // radio ispravno.
        fetchCourseListTask()
    }
}

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
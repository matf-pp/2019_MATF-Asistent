import gui.CustomStylesheet
import gui.view.MainView
import javafx.application.Application
import tornadofx.App


/** MatfAsisentApp nasleđuje klasu App (iz biblioteke TornadoFX) koja nasleđuje javafx.application.Application.
 *  Main funkcija poziva Application::launch na klasičan JavaFX način.
 */
class MatfAsistentApp : App(MainView::class, CustomStylesheet::class)

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
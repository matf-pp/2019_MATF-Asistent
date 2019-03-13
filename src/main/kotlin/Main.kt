import javafx.application.Application
import tornadofx.App
import tornadofx.View
import tornadofx.borderpane

class MatfAsistentApp : App(MainView::class)

class MainView: View("MatfAsistent") {
    override val root = borderpane {

    }
}

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
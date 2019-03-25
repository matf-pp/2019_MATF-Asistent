import gui.view.welcome.WelcomeScreenWizard
import javafx.application.Application
import javafx.scene.paint.Color
import tornadofx.*

class MatfAsistentApp : App(WelcomeScreenWizard::class, CustomStylesheet::class)

class CustomStylesheet : Stylesheet() {

    init {
        with (Stylesheet) {
            root {
                //backgroundColor = multi(c("#6A6A6A"))
            }
            label {
                //textFill = Color.LIGHTGRAY
            }
            button {
                //borderColor += box(topColor, rightColor, leftColor, bottomColor)
//                backgroundRadius = multi(box(0.px))
//                backgroundColor = multi(Color.MIDNIGHTBLUE)
//                textFill = Color.LIGHTGRAY
            }
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
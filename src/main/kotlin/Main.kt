import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class MatfAsistentApp : App(MainView::class)

class MainView: View("MatfAsistent") {
    override val root = borderpane {
        prefWidth = 800.0
        prefHeight = 600.0


        center {
            vbox {
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                alignment = Pos.CENTER
                label("Sadržaj")
            }
        }

        left {
            vbox {
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                alignment = Pos.CENTER
                label("Navigacija")
            }
        }

        right {
            vbox {
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                alignment = Pos.CENTER
                label("Obaveštenja")
            }
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
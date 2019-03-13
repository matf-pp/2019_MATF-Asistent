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

        left {
            vbox(10) {
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                alignment = Pos.CENTER
                label("Navigacija")

                (0..5).forEach {
                    button("Dugme $it")
                }
            }
        }

        center {
            vbox {
                stackpane {
                    maxWidth = Double.MAX_VALUE
                    prefHeight = 300.0
                    style {
                        border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                    }

                    label("Raspored")
                }
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                label("Sadržaj")
            }
        }

        right {
            vbox {
                style {
                    border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(0.0), BorderWidths.DEFAULT))
                }
                alignment = Pos.CENTER
                label("Obaveštenja")
                listview(observableList(*((0..10).map {"Obaveštenje $it"}.toTypedArray())))
            }
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MatfAsistentApp::class.java, *args)
}
package gui.view

import data.Repository
import javafx.geometry.Pos
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MainView: View("MatfAsistent") {
    override val root = borderpane {
        prefWidth = 800.0
        prefHeight = 600.0

        left {
            vbox(10) {
                paddingAll = 10
                style {
                    border = Border(
                        BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            CornerRadii(0.0),
                            BorderWidths.DEFAULT
                        )
                    )
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
                        border = Border(
                            BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                CornerRadii(0.0),
                                BorderWidths.DEFAULT
                            )
                        )
                    }

                    label("Raspored")
                }
                style {
                    border = Border(
                        BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            CornerRadii(0.0),
                            BorderWidths.DEFAULT
                        )
                    )
                }
                label("Sadržaj")
            }
        }

        right {
            vbox {
                style {
                    border = Border(
                        BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            CornerRadii(0.0),
                            BorderWidths.DEFAULT
                        )
                    )
                }
                alignment = Pos.CENTER
                label("Obaveštenja")
                listview(Repository.notifications) {
                    cellFormat {
                        graphic = cache {
                            vbox {
                                label(it.title) {
                                    style {
                                        fontSize = 16.px
                                        fontWeight = FontWeight.BOLD
                                    }
                                }
                                label(it.content)
                            }
                        }
                    }
                }
            }
        }
    }
}
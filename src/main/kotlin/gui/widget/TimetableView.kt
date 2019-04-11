package gui.widget

import javafx.geometry.Pos
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import scheduler.Timetable
import tornadofx.*
import java.text.DecimalFormat

class TimetableView(timetable: Timetable) : VBox() {

    init {
        gridpane {

            style {
                // Ivice su ostvarene tako što tabela ima određenu boju, i ta boja se prikazuje
                // jedino na margini i između vrsta i kolona.
                backgroundColor = multi(Color.DARKGRAY)
                val borderThickness = 2.px
                vgap = borderThickness
                hgap = borderThickness
                padding = box(borderThickness)
            }

            addHeaders()

            for (i in 1..5) {
                row {
                    for (j in 1..13) {
                        pane {
                            style {
                                backgroundColor = multi(Color.WHITE)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun GridPane.addHeaders() {
        addColumn(0, *(arrayOf("", "PON", "UTO", "SRE", "ČET", "PET").map { DayPaneCell(it) }.toTypedArray()))

        row {
            for (i in 1..13) {
                stackpane {
                    alignment = Pos.CENTER
                    style {
                        backgroundColor = multi(Color.WHITE)
                    }

                    gridpaneConstraints {
                        hGrow = Priority.ALWAYS
                    }

                    val df = DecimalFormat("##")

                    label("${df.format(7+i)}:15 - ${df.format(7+i+1)}:00")
                }
            }
        }
    }

    class DayPaneCell(day: String) : Pane() {
        init {
            label(day)

            style {
                backgroundColor = multi(Color.WHITE)
                fontSize = 16.px
            }
        }
    }

}
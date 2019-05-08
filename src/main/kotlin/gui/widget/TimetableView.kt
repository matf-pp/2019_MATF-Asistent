package gui.widget

import data.Course
import javafx.geometry.Pos
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import scheduler.Timetable
import tornadofx.*
import java.text.DecimalFormat
import java.time.DayOfWeek

class TimetableView : GridPane() {

    init {

        minWidth = Region.USE_PREF_SIZE
        prefWidth = 1100.0

        minHeight = 400.0

        style {
            // Ivice su ostvarene tako što tabela ima određenu boju, i ta boja se prikazuje
            // jedino na margini i između vrsta i kolona.
            backgroundColor = multi(Color.DARKGRAY)
            val borderThickness = 2.px
            vgap = borderThickness
            hgap = borderThickness
            padding = box(borderThickness)
        }

        // Prva kolona (sa imenima radnih dana) treba da bude fiksirane širine
        constraintsForColumn(0).apply {
            minWidth = USE_PREF_SIZE
            prefWidth = 50.0
            maxWidth = USE_PREF_SIZE
            isFillWidth = true
        }
    }


    private fun GridPane.addHeaders() {
        pane {
            style {
                backgroundColor = multi(Color.WHITE)
            }

            gridpaneConstraints {
                columnRowIndex(0, 0)
            }
        }

        for (i in 1..13) {
            stackpane {
                alignment = Pos.CENTER
                style {
                    backgroundColor = multi(Color.WHITE)
                }

                gridpaneConstraints {
                    hGrow = Priority.ALWAYS
                    columnRowIndex(i, 0)

                }

                val df = DecimalFormat("##")

                label("${df.format(7+i)}:15 - ${df.format(7+i+1)}:00")
            }
        }
    }

    class DayPaneCell(day: DayOfWeek) : StackPane() {
        init {

            alignment = Pos.CENTER
            paddingAll = 5

            val text = when (day) {
                DayOfWeek.MONDAY -> "PON"
                DayOfWeek.TUESDAY -> "UTO"
                DayOfWeek.WEDNESDAY -> "SRE"
                DayOfWeek.THURSDAY -> "ČET"
                DayOfWeek.FRIDAY -> "PET"
                else -> throw IllegalStateException()
            }

            label(text) {
                textAlignment = TextAlignment.CENTER
            }

            style {
                backgroundColor = multi(Color.WHITE)
                fontSize = 16.px
            }

            gridpaneConstraints {
                columnRowIndex(0, day.value)
            }
        }
    }

    @Synchronized
    fun setTimetable(timetable: Timetable) {

        this.clear()

        addHeaders()

        val courseListsByDays = timetable.courseList.groupBy(Course::dayOfWeek)


        courseListsByDays.forEach { (day, courseList: List<Course>) ->

            add(DayPaneCell(day))

            var startIndex = 8

            courseList.filter { it.assignedDay != null }.sortedBy(Course::start).forEach { course ->

                while (startIndex < course.start) {
                    pane {
                        style {
                            backgroundColor = multi(Color.LIGHTGRAY)
                        }

                        gridpaneConstraints {
                            columnRowIndex(startIndex - 7, day.value)
                        }
                    }
                    startIndex++
                }

                vbox {
                    paddingAll = 10
                    alignment = Pos.CENTER

                    label(course.title) {
                        isWrapText = true
                        textAlignment = TextAlignment.CENTER
                    }
                    label(course.lecturer) {
                        isWrapText = true
                        textAlignment = TextAlignment.CENTER

                    }
                    label(course.classroomText) {
                        isWrapText = true
                        textAlignment = TextAlignment.CENTER

                    }
                    style {
                        backgroundColor = if (course.type == Course.Type.LECTURE) {
                            multi(course.getColor())
                        } else {
                            multi(course.getColor().brighter())
                        }
                    }
                    useMaxWidth = true
                    gridpaneConstraints {
                        columnSpan = course.duration
                        columnRowIndex(startIndex - 7, day.value)
                    }
                }

                startIndex += course.duration
            }

            // Popuniti ostatak dana sa praznim poljima
            while (startIndex <= 20) {
                pane {
                    style {
                        backgroundColor = multi(Color.LIGHTGRAY)
                    }

                    gridpaneConstraints {
                        columnRowIndex(startIndex - 7, day.value)
                    }
                }
                startIndex++
            }
        }
    }
}
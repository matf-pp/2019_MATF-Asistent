package gui.widget

import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import scheduler.Timetable
import tornadofx.*

class TimetableViewWrapper(timetableProperty: ObservableValue<Timetable>) : StackPane() {

    init {

        alignment = Pos.CENTER

        val timetableView = TimetableView()
        timetableView.isVisible = false

        add(timetableView)

        val indicator = progressindicator {
            setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
            setPrefSize(128.0, 128.0)
            setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
        }

        val errorMessage = label("Nije moguće napraviti raspored za date kurseve") {
            isVisible = false
            style {
                textFill = Color.DARKRED
            }
        }

        fun updateTimetable(timetable: Timetable?) {
            // Sakrij sve, a onda vidi koje ćeš prikazati
            timetableView.isVisible = false
            indicator.isVisible = false
            errorMessage.isVisible = false

            if (timetable == null) {
                indicator.isVisible = true
            } else {
                if (timetable.courseList.isEmpty()) {
                    errorMessage.isVisible = true
                } else {
                    indicator.isVisible = true
                    timetableView.setTimetable(timetable)
                    timetableView.isVisible = true
                    indicator.isVisible = false
                }
            }
        }

        if (timetableProperty.value != null) {
            updateTimetable(timetableProperty.value)
        }

        timetableProperty.onChange(::updateTimetable)
    }
}
package gui.widget

import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import scheduler.Timetable
import tornadofx.add
import tornadofx.onChange
import tornadofx.progressindicator

class TimetableViewWrapper(timetable: ObservableValue<Timetable>) : StackPane() {

    init {

        alignment = Pos.CENTER

        val timetableView = TimetableView()

        add(timetableView)

        val indicator = progressindicator {
            setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
            setPrefSize(128.0, 128.0)
            setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)

        }

        if (timetable.value != null) {
            timetableView.setTimetable(timetable.value)
            children.remove(indicator)
        }

        timetable.onChange {
            if (it != null) {
                if (indicator !in children) {
                    children.add(indicator)
                }
                timetableView.setTimetable(timetable.value)
                children.remove(indicator)
            }
        }

    }
}
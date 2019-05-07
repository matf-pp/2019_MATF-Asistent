package gui.view

import data.Repository
import gui.widget.TimetableView
import tornadofx.View
import tornadofx.vbox

class MainView: View("MatfAsistent") {

    override val root = vbox {
        setPrefSize(900.0, 600.0)
        add(TimetableView(Repository.bestTimetableProperty))
    }
}
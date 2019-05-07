package gui.view.welcome

import data.Repository
import gui.widget.TimetableView
import tornadofx.View
import tornadofx.vbox

class TimetableConfirmDialog : View("Potvrda rasporeda") {

    override val root = vbox {
        add(TimetableView(Repository.bestTimetableProperty))
    }

}
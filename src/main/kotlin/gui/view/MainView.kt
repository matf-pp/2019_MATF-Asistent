package gui.view

import data.Repository
import gui.view.welcome.WelcomeScreenWizard
import gui.widget.TimetableView
import tornadofx.View
import tornadofx.runLater
import tornadofx.vbox

class MainView: View("MatfAsistent") {

    init {
        runLater {
            if (Repository.bestTimetable == null) {
                find<WelcomeScreenWizard>().openModal()
            }
        }
    }

    override val root = vbox {
        setPrefSize(900.0, 600.0)
        add(TimetableView(Repository.bestTimetableProperty))
    }
}
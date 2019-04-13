package gui.view.welcome

import data.Repository
import gui.listview.NullSelectionModel
import gui.widget.TimetableView
import tornadofx.*

class WelcomeStep3 : View("Izbor rasporeda") {

    override val root = vbox {
        vbox {
            listview(Repository.timetables) {
                selectionModel = NullSelectionModel()
                cellFormat {
                    graphic = cache {
                        TimetableView(it)
                    }
                }
            }
        }
    }

}
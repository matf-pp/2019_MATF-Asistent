package gui.view.welcome

import data.Repository
import gui.widget.TimetableView
import tornadofx.*

class WelcomeStep3 : View("Izbor rasporeda") {

    override val root = vbox {
        vbox {
            listview(Repository.timetables) {
                cellFormat {
                    graphic = cache {
                        TimetableView(it)
                    }
                }
            }
        }
    }

}
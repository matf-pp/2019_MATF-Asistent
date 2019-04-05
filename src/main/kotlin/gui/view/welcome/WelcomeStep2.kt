package gui.view.welcome

import data.Repository
import tornadofx.View
import tornadofx.listview
import tornadofx.vbox

class WelcomeStep2 : View("Izbor kurseva") {

    override val root = vbox {
        listview(Repository.allAvailableCourses)
    }

}
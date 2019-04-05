package gui.view.welcome

import tornadofx.View
import tornadofx.vbox

class WelcomeStep2 : View("Izbor kurseva") {

    override val root = vbox {
        // TODO čekanje na spisak kurseva iz repozitorijuma
        //  (koji dolazi sa veba, ili lokalno keširanih podataka)
        //val task = fetchCourseListTask()
    }

}
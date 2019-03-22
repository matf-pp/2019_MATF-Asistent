package gui.view.welcome

import tornadofx.View
import tornadofx.label
import tornadofx.vbox

class WelcomeStep1 : View("Izbor godine") {

    override val root = vbox {
        label("Prvi Korak")
    }
}
package gui.view.welcome

import tornadofx.View
import tornadofx.label
import tornadofx.vbox

class WelcomeStep3 : View("Izbor rasporeda") {

    override val root = vbox {
        label("Čekanje na učitavanje rasporeda (koje je beskonačno jer nije implementirano učitavanje)")

        vbox {
            // TODO prikazati generisane rasporede
        }
    }

}
package gui.view.welcome

import data.Repository
import tornadofx.*

class WelcomeStep2 : View("Izbor kurseva") {

    override val root = vbox {
        label("Čekanje na učitavanje rasporeda (koje je beskonačno jer nije implementirano učitavanje)")

        vbox {
            runAsyncWithOverlay {
                println(Repository.schedules)
            }
        }
    }

}
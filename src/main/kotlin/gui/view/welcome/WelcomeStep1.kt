package gui.view.welcome

import data.Repository
import javafx.geometry.Orientation
import tornadofx.*

class WelcomeStep1 : View("Izbor godine") {

    override val root = form {
        fieldset {
            this@fieldset.spacing = 30.0
            labelPosition = Orientation.VERTICAL

            field("Izaberite smer:") {
                combobox(values = Repository.smerovi)
            }

            field {
                checkbox("Odgovara mi da mi pauze budu minimalne")
            }

            field("Kada vam najviše odgovara nastava", Orientation.VERTICAL) {
                togglegroup {
                    radiobutton("Prepodne")
                    radiobutton("U podne")
                    radiobutton("Popodne")
                    radiobutton("Svejedno")
                }
            }

            field("Više volim da...", Orientation.VERTICAL) {
                togglegroup {
                    radiobutton("Imam nastavu ravnomerno raspoređenu tokom cele nedelje")
                    radiobutton("Imam više časova u toku dana, ali zato i slobodne dane")
                }
            }
        }


    }
}
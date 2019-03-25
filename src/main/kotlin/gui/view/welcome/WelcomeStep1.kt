package gui.view.welcome

import data.Repository
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Orientation
import tornadofx.*

class WelcomeStep1 : View("Izbor godine") {

    private val majorProperty = SimpleObjectProperty<Repository.Major>()
    private val minimalPausePrefProperty = SimpleBooleanProperty(true)
    private val timeFramePrefProperty = SimpleObjectProperty<Repository.TimeFramePreference>()
    private val arrangementPrefProperty = SimpleObjectProperty<Repository.ArrangementPreference>()

    // Formular je kompletan tek kad se izabere svaka od opcija
    override val complete = booleanBinding(
            majorProperty, timeFramePrefProperty, arrangementPrefProperty) {
        majorProperty.isNotNull.value &&
        timeFramePrefProperty.isNotNull.value &&
        arrangementPrefProperty.isNotNull.value
    }

    override val root = form {
        fieldset {
            this@fieldset.spacing = 30.0
            labelPosition = Orientation.VERTICAL

            field("Moj smer je:") {
                combobox(majorProperty, Repository.majors)
            }

            field {
                checkbox("Odgovara mi da mi pauze budu minimalne", minimalPausePrefProperty)
            }

            field("Najviše mi odgovara da nastava bude:", Orientation.VERTICAL) {
                // Ažurirati kada (i ako) izađe nova TornadoFX verzija
                // Videti https://github.com/edvin/tornadofx/issues/956
                togglegroup {
                    timeFramePrefProperty.bind(selectedValueProperty())
                    radiobutton("Prepodne", value = Repository.TimeFramePreference.BEFORE_NOON)
                    radiobutton("U podne", value = Repository.TimeFramePreference.NOON)
                    radiobutton("Popodne", value = Repository.TimeFramePreference.AFTERNOON)
                    radiobutton("Svejedno", value = Repository.TimeFramePreference.NONE)
                }
            }

            field("Više volim da:", Orientation.VERTICAL) {
                togglegroup {
                    arrangementPrefProperty.bind(selectedValueProperty())
                    radiobutton("Imam nastavu ravnomerno raspoređenu tokom cele nedelje", value = Repository.ArrangementPreference.EVEN)
                    radiobutton("Imam više časova u toku dana, ali zato i slobodne dane", value = Repository.ArrangementPreference.GROUPED)
                    radiobutton("Svejedno", value = Repository.ArrangementPreference.NONE)
                }
            }
        }
    }
}
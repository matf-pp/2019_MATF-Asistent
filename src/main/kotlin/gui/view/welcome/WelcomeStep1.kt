package gui.view.welcome

import data.Repository
import javafx.geometry.Orientation
import tornadofx.*

class WelcomeStep1 : View("Izbor godine") {

    // Prosleđen ViewModel objekat iz Wizard-a koji koristi ovaj korak
    private val viewModel: WelcomeScreenWizard.ViewModel by inject()

    // Formular je kompletan tek kad se izabere svaka od opcija
    override val complete = booleanBinding(
        viewModel.intermediaryPausesProperty,
        viewModel.majorProperty,
        viewModel.timeFramePrefProperty,
        viewModel.arrangementPrefProperty) {
            this@WelcomeStep1.viewModel.intermediaryPausesProperty.isNotNull.value
            this@WelcomeStep1.viewModel.majorProperty.isNotNull.value &&
            this@WelcomeStep1.viewModel.timeFramePrefProperty.isNotNull.value &&
            this@WelcomeStep1.viewModel.arrangementPrefProperty.isNotNull.value
    }

    override val root = form {
        fieldset {
            this@fieldset.spacing = 30.0
            labelPosition = Orientation.VERTICAL

            field("Moj smer je:") {
                combobox(viewModel.majorProperty, Repository.majors) {
                    promptText = "Izaberite smer"
                }
            }

            field("Najviše mi odgovara da:", Orientation.VERTICAL) {
                togglegroup {
                    viewModel.intermediaryPausesProperty.bind(selectedValueProperty())
                    radiobutton("Imam pauzu u toku dana (kako bih stigao/la na ručak, na primer)", value = Repository.IntermediaryPauses.PREFER)
                    radiobutton("Nemam pauze u toku dana", value = Repository.IntermediaryPauses.AVOID)
                    radiobutton("Svejedno mi je", value = Repository.IntermediaryPauses.NONE)

                }
            }

            field("Najviše mi odgovara da nastava bude:", Orientation.VERTICAL) {
                // Ažurirati kada (i ako) izađe nova TornadoFX verzija
                // Videti https://github.com/edvin/tornadofx/issues/956
                togglegroup {
                    viewModel.timeFramePrefProperty.bind(selectedValueProperty())
                    radiobutton("Prepodne", value = Repository.TimeFramePreference.BEFORE_NOON)
                    radiobutton("U podne", value = Repository.TimeFramePreference.NOON)
                    radiobutton("Popodne", value = Repository.TimeFramePreference.AFTERNOON)
                    radiobutton("Svejedno", value = Repository.TimeFramePreference.NONE)
                }
            }

            field("Više volim da:", Orientation.VERTICAL) {
                togglegroup {
                    viewModel.arrangementPrefProperty.bind(selectedValueProperty())
                    radiobutton("Imam nastavu ravnomerno raspoređenu tokom cele nedelje", value = Repository.ArrangementPreference.EVEN)
                    radiobutton("Imam više časova u toku dana, ali zato i slobodne dane", value = Repository.ArrangementPreference.GROUPED)
                    radiobutton("Svejedno", value = Repository.ArrangementPreference.NONE)
                }
            }
        }
    }
}
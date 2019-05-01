package gui.view.welcome

import data.Repository
import javafx.geometry.Orientation
import tornadofx.*

class WelcomeStep1 : View("Izbor godine") {

    // Prosleđen ViewModel objekat iz Wizard-a koji koristi ovaj korak
    private val wizardViewModel: WelcomeScreenWizard.ViewModel by inject()

    // Formular je kompletan tek kad se izabere svaka od opcija
    override val complete = booleanBinding(
        wizardViewModel.majorProperty,
        wizardViewModel.minorProperty,
        wizardViewModel.intermediaryPausesProperty,
        wizardViewModel.timeFramePrefProperty,
        wizardViewModel.arrangementPrefProperty) {
            wizardViewModel.majorProperty.isNotNull.value &&
            wizardViewModel.minorProperty.isNotNull.value &&
            wizardViewModel.intermediaryPausesProperty.isNotNull.value &&
            wizardViewModel.timeFramePrefProperty.isNotNull.value &&
            wizardViewModel.arrangementPrefProperty.isNotNull.value
    }

    private val minorsAvailable = objectBinding(wizardViewModel.majorProperty) {
        when (wizardViewModel.majorProperty.value) {
            Repository.Major.COMP_SCI -> {
                //Takođe, ako je smer Informatika, modul je obavezno I
                wizardViewModel.minorProperty.value = Repository.Minor.I
                observableList(Repository.Minor.I)
            }

            Repository.Major.MATH -> {
                observableList(
                    Repository.Minor.L,
                    Repository.Minor.M,
                    Repository.Minor.R,
                    Repository.Minor.P,
                    Repository.Minor.S,
                    Repository.Minor.MA
                )
            }

            Repository.Major.ASTRONOMY -> {
                observableList(
                    Repository.Minor.AF,
                    Repository.Minor.AI
                )
            }

            null -> observableList()
        }
    }

    private val minorsPickerDisabled = booleanBinding(wizardViewModel.majorProperty) {
        wizardViewModel.majorProperty.value == Repository.Major.COMP_SCI
    }

    override val root = form {
        fieldset {
            this@fieldset.spacing = 30.0
            labelPosition = Orientation.VERTICAL

            field("Moj smer je:") {
                combobox(wizardViewModel.majorProperty, Repository.majors) {
                    promptText = "Izaberite smer"
                }
            }

            field("Moj modul je:") {
                combobox(wizardViewModel.minorProperty) {
                    itemsProperty().bind(minorsAvailable)
                    disableProperty().bind(minorsPickerDisabled)
                    promptText = "Izaberite modul"
                }
            }

            field("Najviše mi odgovara da:", Orientation.VERTICAL) {
                togglegroup {
                    wizardViewModel.intermediaryPausesProperty.bind(selectedValueProperty())
                    radiobutton("Imam pauzu u toku dana (kako bih stigao/la na ručak, na primer)", value = Repository.IntermediaryPauses.PREFER)
                    radiobutton("Nemam pauze u toku dana", value = Repository.IntermediaryPauses.AVOID)
                    radiobutton("Svejedno mi je", value = Repository.IntermediaryPauses.NONE)

                }
            }

            field("Najviše mi odgovara da nastava bude:", Orientation.VERTICAL) {
                // Ažurirati kada (i ako) izađe nova TornadoFX verzija
                // Videti https://github.com/edvin/tornadofx/issues/956
                togglegroup {
                    wizardViewModel.timeFramePrefProperty.bind(selectedValueProperty())
                    radiobutton("Prepodne", value = Repository.TimeFramePreference.BEFORE_NOON)
                    radiobutton("U podne", value = Repository.TimeFramePreference.NOON)
                    radiobutton("Popodne", value = Repository.TimeFramePreference.AFTERNOON)
                    radiobutton("Svejedno", value = Repository.TimeFramePreference.NONE)
                }
            }

            field("Više volim da:", Orientation.VERTICAL) {
                togglegroup {
                    wizardViewModel.arrangementPrefProperty.bind(selectedValueProperty())
                    radiobutton("Imam nastavu ravnomerno raspoređenu tokom cele nedelje", value = Repository.ArrangementPreference.EVEN)
                    radiobutton("Imam više časova u toku dana, ali zato i slobodne dane", value = Repository.ArrangementPreference.GROUPED)
                    radiobutton("Svejedno", value = Repository.ArrangementPreference.NONE)
                }
            }
        }
    }
}
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
        wizardViewModel.yearOfStudyProperty,
        wizardViewModel.intermediaryPausesProperty,
        wizardViewModel.arrangementPrefProperty) {
            wizardViewModel.majorProperty.isNotNull.value &&
            wizardViewModel.minorProperty.isNotNull.value &&
            wizardViewModel.yearOfStudyProperty.isNotNull.value &&
            wizardViewModel.intermediaryPausesProperty.isNotNull.value &&
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
                    Repository.Minor.N,
                    Repository.Minor.V,
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

            field("Moja godina studija je:") {
                combobox(wizardViewModel.yearOfStudyProperty, enumValues<Repository.YearOfStudy>().toList()) {
                    promptText = "Izaberite godinu studija"
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
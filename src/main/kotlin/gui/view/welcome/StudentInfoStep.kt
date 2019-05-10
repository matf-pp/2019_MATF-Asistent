package gui.view.welcome

import data.Repository
import javafx.geometry.Orientation
import tornadofx.*

class StudentInfoStep : View("Izbor godine") {

    // Prosleđen ViewModel objekat iz Wizard-a koji koristi ovaj korak
    private val wizardViewModel: WelcomeScreenWizard.ViewModel by inject()

    // Formular je kompletan tek kad se izabere svaka od opcija
    override val complete = booleanBinding(
        wizardViewModel.majorProperty,
        wizardViewModel.minorProperty,
        wizardViewModel.yearsOfStudy,
        wizardViewModel.intermediaryPausesProperty) {
            wizardViewModel.majorProperty.isNotNull.value &&
            wizardViewModel.minorProperty.isNotNull.value &&
            wizardViewModel.yearsOfStudy.isNotEmpty() &&
            wizardViewModel.intermediaryPausesProperty.isNotNull.value
    }

    private val minorsAvailable = objectBinding(wizardViewModel.majorProperty) {
        when (wizardViewModel.majorProperty.value) {
            Repository.Major.COMP_SCI -> {
                // Ako je smer Informatika, modul je obavezno I
                wizardViewModel.minorProperty.value = Repository.Minor.I
                observableList(Repository.Minor.I)
            }

            Repository.Major.MATH -> {
                observableList(
                    Repository.Minor.L,
                    Repository.Minor.M,
                    Repository.Minor.R,
                    Repository.Minor.N,
                    Repository.Minor.V
                )
            }

            Repository.Major.ASTRONOMY -> {
                // Ako je smer Astronomija, modul je obavezno AI (jedini podržan modul)
                wizardViewModel.minorProperty.value = Repository.Minor.AI
                observableList(Repository.Minor.AI)
            }

            null -> observableList()
        }
    }

    private val minorsPickerEnabled = booleanBinding(wizardViewModel.majorProperty) {
        wizardViewModel.majorProperty.value != Repository.Major.COMP_SCI &&
        wizardViewModel.majorProperty.value != Repository.Major.ASTRONOMY &&
        wizardViewModel.majorProperty.value != null
    }

    override val root = form {

        fieldset {
            this@fieldset.spacing = 30.0
            labelPosition = Orientation.VERTICAL

            field {
                hbox(10) {
                    vbox(3) {
                        label("Moj smer je:")
                        combobox(wizardViewModel.majorProperty, Repository.majors) {
                            promptText = "Izaberite smer"
                        }
                    }


                    vbox(3) {
                        visibleProperty().bind(minorsPickerEnabled)

                        label("Moj modul je:")
                        combobox(wizardViewModel.minorProperty) {
                            itemsProperty().bind(minorsAvailable)
                            promptText = "Izaberite modul"
                        }
                    }
                }
            }

            field("Imam kurseve sa godina:") {
                hbox(10) {
                    enumValues<Repository.YearOfStudy>().map { year ->
                        checkbox(year.toString()) {
                            selectedProperty().onChange {
                                if (it) {
                                    wizardViewModel.yearsOfStudy.add(year)
                                } else {
                                    wizardViewModel.yearsOfStudy.remove(year)
                                }
                            }
                        }
                    }
                }
            }

            field("Najviše mi odgovara da:", Orientation.VERTICAL) {
                togglegroup {
                    wizardViewModel.intermediaryPausesProperty.bind(selectedValueProperty())
                    radiobutton("Imam pauzu u toku dana (kako bih stigao/la na ručak, na primer)", value = Repository.IntermediaryPauses.PREFER)
                    radiobutton("Nemam pauze u toku dana", value = Repository.IntermediaryPauses.AVOID) { isSelected = true }
                    radiobutton("Svejedno mi je", value = Repository.IntermediaryPauses.NONE)

                }
            }
        }
    }
}
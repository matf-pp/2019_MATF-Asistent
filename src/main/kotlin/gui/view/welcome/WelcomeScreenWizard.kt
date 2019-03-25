package gui.view.welcome

import tornadofx.Wizard

class WelcomeScreenWizard : Wizard("Formiranje rasporeda") {

    init {
        //TODO graphic = ...
        add(WelcomeStep1::class)
        add(WelcomeStep2::class)

        //TODO prevesti elemente Wizard-a na srpski
    }

}
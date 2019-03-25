package gui.view.welcome

import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.Wizard

class WelcomeScreenWizard : Wizard("Formiranje rasporeda") {

    init {
        //TODO graphic = ...
        add(WelcomeStep1::class)
        add(WelcomeStep2::class)

        // Preimenovanje elemenata na srpski
        val stepsLabel = (root.left as VBox).children[0] as Label // Malo prljavo, ali nema lepšeg načina
        stepsLabel.textProperty().unbind()
        stepsLabel.text = "Koraci"

        (root.bottom as ButtonBar).buttons.forEach {
            // Kompilator može da garantuje da ako ova naredba kastovanja prođe, nakon toga ta promenljiva može
            // da se koristi kao da je instanca te klase, bez ponovnog eksplicitnog kastovanja.
            it as Button

            // Svojstvo teksta je bind-ovano za neko drugo Svojstvo, pa ćemo prvo da ga "otkačimo", pre nego što
            // krenemo da ga menjamo.
            it.textProperty().unbind()

            it.text = when (it.text) {
                "< _Back" -> "Predhodno"
                "_Next >" -> "Sledeće"
                "_Cancel" -> "Otkaži"
                "_Finish" -> "Gotovo"
                else -> null
            }
            println(it.text)
        }
    }



}
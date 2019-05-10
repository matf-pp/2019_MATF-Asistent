package gui.view.welcome

import data.Repository
import javafx.beans.binding.BooleanBinding
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import tornadofx.*

class WelcomeScreenWizard : Wizard("Formiranje rasporeda") {

    // Ovaj ViewModel se koristi u koracima, a ovde je samo zbog dependency injection-a.
    val viewModel: ViewModel by inject()

    init {
        add(StudentInfoStep::class)
        add(CoursePickingStep::class)
        add(LecturerChoiceStep::class)
        add(TimetableConfirmDialog::class)

        // Preimenovanje elemenata na srpski
        stepsTextProperty.value = "Koraci"
        backButtonTextProperty.value = "Predhodno"
        nextButtonTextProperty.value = "Sledeće"
        cancelButtonTextProperty.value = "Otkaži"
        finishButtonTextProperty.value = "Potvrdi raspored"

        // Ručno ispravljanje baga (ukloniti kada izađe sledeća verzija TornadoFX-a)
        // (videti https://github.com/edvin/tornadofx/commit/1e8fdc7158a270fe73ada2cac84d5530b97e8823)
        runLater {
            (root.bottom as ButtonBar).buttons
                .filterIsInstance<Button>() // Ovo je potrebno da bi uverili kompilator da su to instance klase Button
                .first { it.text == "Sledeće" }
                .enableWhen(canGoNext.and(hasNext).and(currentPageComplete))
        }
    }

    /**
     * Ova klasa sadrži sve podatke koje je korisnik uneo. Kako je objekat ove klase instanciran u okviru Wizard-a,
     * njegova polja su dostupna svim koracima Wizard-a.
     */
    class ViewModel : tornadofx.ViewModel() {
        val majorProperty = SimpleObjectProperty<Repository.Major>()
        val minorProperty = SimpleObjectProperty<Repository.Minor>()
        val intermediaryPausesProperty = SimpleObjectProperty<Repository.IntermediaryPauses>(Repository.IntermediaryPauses.AVOID)
        val yearsOfStudy = observableList<Repository.YearOfStudy>()
        init {

            // Svaki put kada se promeni modul ili izbor godina, potrebno je da se promeni i spisak kurseva
            minorProperty.onChange { updateCourseList() }
            yearsOfStudy.onChange { updateCourseList() }
        }

        private fun updateCourseList() {
            if (minorProperty.value != null && yearsOfStudy.isNotEmpty()) {
                Repository.updateCourseList(minorProperty.value, yearsOfStudy)
            }
        }
    }

    override val canFinish: BooleanBinding = Repository.bestTimetableProperty.isNotNull
}
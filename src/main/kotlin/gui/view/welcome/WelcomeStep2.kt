package gui.view.welcome

import data.Course
import data.Repository
import gui.tableview.NullSelectionModel
import tornadofx.*
import gui.view.welcome.WelcomeScreenWizard.ViewModel


class WelcomeStep2 : View("Izbor kurseva") {

    private val viewModel: ViewModel by inject()

    override val root = vbox {
        tableview(Repository.allAvailableCourses) {
            columnResizePolicy = SmartResize.POLICY

            // Za objašnjenje, pogledati dokumentaciju za NullSelectionModel
            selectionModel = NullSelectionModel(this)

            column("Izabran", Course::selected).makeEditable()
            column("Godina", Course::year)
            column("Semestar", Course::semester)
            column("Naziv", Course::title)
            column("Broj ESPB", Course::espbPoints)
        }
    }

    // Ovo se izvršava kada se pritisne dugme "Sledeće"
    override fun onSave() {
        Repository.generateTimetables(viewModel)
        super.onSave()
    }
}
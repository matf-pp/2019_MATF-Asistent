package gui.view.welcome

import data.CourseDef
import data.Repository
import gui.tableview.NullSelectionModel
import tornadofx.*
import gui.view.welcome.WelcomeScreenWizard.ViewModel


class WelcomeStep2 : View("Izbor kurseva") {

    private val wizardViewModel: ViewModel by inject()

    override val root = vbox {
        tableview(wizardViewModel.availableCourses) {
            columnResizePolicy = SmartResize.POLICY

            // Za objašnjenje, pogledati dokumentaciju za NullSelectionModel
            selectionModel = NullSelectionModel(this)

            column("Izabran", CourseDef::selected).makeEditable()
            column("Godina", CourseDef::year)
            column("Semestar", CourseDef::semester)
            column("Naziv", CourseDef::title)
            column("Broj ESPB", CourseDef::ects)
        }
    }

    // Ovo se izvršava kada se pritisne dugme "Sledeće"
    override fun onSave() {
        Repository.generateTimetables(wizardViewModel)
        super.onSave()
    }
}
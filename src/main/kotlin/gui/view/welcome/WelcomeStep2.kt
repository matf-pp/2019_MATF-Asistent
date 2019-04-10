package gui.view.welcome

import data.CourseListItem
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

            column("Izabran", CourseListItem::selected).makeEditable()
            column("Godina", CourseListItem::year)
            column("Semestar", CourseListItem::semester)
            column("Naziv", CourseListItem::title)
            column("Broj ESPB", CourseListItem::espbPoints)
        }
    }

    // Ovo se izvršava kada se pritisne dugme "Sledeće"
    override fun onSave() {
        Repository.generateTimetables(viewModel)
        super.onSave()
    }
}
package gui.view.welcome

import data.CourseDef
import data.Repository
import gui.tableview.NullSelectionModel
import tornadofx.*
import gui.view.welcome.WelcomeScreenWizard.ViewModel


class CoursePickingStep : View("Izbor kurseva") {

    private val wizardViewModel: ViewModel by inject()

    override val root = vbox {
        tableview(Repository.courseDefs) {
            columnResizePolicy = SmartResize.POLICY

            // Za objašnjenje, pogledati dokumentaciju za NullSelectionModel
            selectionModel = NullSelectionModel(this)

            column("Izabran", CourseDef::selected).makeEditable()
            column("Naziv", CourseDef::title)
        }
    }
}
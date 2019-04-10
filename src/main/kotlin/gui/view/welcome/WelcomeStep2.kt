package gui.view.welcome

import data.CourseListItem
import data.Repository
import gui.tableview.NullSelectionModel
import tornadofx.*

class WelcomeStep2 : View("Izbor kurseva") {

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
}
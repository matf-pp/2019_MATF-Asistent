package gui.view.welcome

import data.CourseDef
import data.Repository
import gui.listview.NullSelectionModel
import javafx.scene.layout.VBox
import tornadofx.*

class LecturerChoiceStep: View("Izbor predavača") {

    override val root = vbox {
        listview(Repository.courseDefs.filtered(CourseDef::selected)) {
            selectionModel = NullSelectionModel()
            cellFormat { courseDef ->
                graphic = cache {
                    VBox(10.0).apply {
                        label(courseDef.title)
                        courseDef.lecturers.forEach {
                            checkbox(it.name, it.selected.toProperty())
                        }
                    }
                }
            }
        }
    }

}
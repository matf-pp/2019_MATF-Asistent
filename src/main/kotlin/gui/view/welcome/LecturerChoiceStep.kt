package gui.view.welcome

import data.CourseDef
import data.Repository
import gui.listview.NullSelectionModel
import javafx.scene.layout.VBox
import tornadofx.*

class LecturerChoiceStep: View("Izbor predavača") {

    private val viewModel: WelcomeScreenWizard.ViewModel by inject()

    override val root = vbox {
        listview(Repository.selectedCourseDefs) {
            selectionModel = NullSelectionModel()
            cellFormat { courseDef ->
                graphic = VBox(10.0).apply {
                    label(courseDef.title)
                    courseDef.lecturers.forEach {
                        checkbox(it.name, it.selectedProperty)
                    }
                }
            }
        }
    }

    // Ovo se izvršava kada se pritisne dugme "Sledeće"
    override fun onSave() {

        val selectedLecturers = Repository.selectedCourseDefs
            .flatMap { courseDef ->
                courseDef.lecturers.map { Pair(courseDef.title, it) }
            }
            .filter {
                it.second.selected
            }
            .map {
                Pair(it.first, it.second.name)
            }

        val coursesWithSelectedLecturers = Repository.courses.filter { course ->
            selectedLecturers.any { it.first == course.title && it.second == course.lecturer }
        }

        Repository.generateTimetables(
            coursesWithSelectedLecturers,
            viewModel.intermediaryPausesProperty.value,
            viewModel.arrangementPrefProperty.value
        )
        super.onSave()
    }

}
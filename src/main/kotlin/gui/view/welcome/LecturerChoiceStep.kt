package gui.view.welcome

import data.CourseDef
import data.Repository
import gui.listview.NullSelectionModel
import javafx.scene.layout.VBox
import tornadofx.*

class LecturerChoiceStep: View("Izbor predavača") {

    private val viewModel: WelcomeScreenWizard.ViewModel by inject()

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

    // Ovo se izvršava kada se pritisne dugme "Sledeće"
    override fun onSave() {
//TODO
//        val selectedLecturers = Repository.courseDefs
//            .flatMap(CourseDef::lecturers)
//            .filter(CourseDef.Lecturer::selected)
//            .map(CourseDef.Lecturer::)
//            .also(::println)
//
//        val coursesWithSelectedLecturers = Repository.courses.filter { it.title in selectedLecturers }
//
//        println(coursesWithSelectedLecturers)

        Repository.generateTimetables(
            Repository.courses,
            viewModel.intermediaryPausesProperty.value,
            viewModel.arrangementPrefProperty.value
        )
        super.onSave()
    }

}
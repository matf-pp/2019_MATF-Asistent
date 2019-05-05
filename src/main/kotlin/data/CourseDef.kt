package data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.getProperty
import tornadofx.property

/** Ova klasa služi samo da opiše kurs koji se daje na fakultetu. Pojavljuje se samo na mestima
 *  gde se pravi izbor kurseva. Ne bavi se time kada se tačno taj kurs odvija u toku nedelje
 *  (za tu upotrebu, videti [Course]).
 *
 */
data class CourseDef(var title: String, var minor: Repository.Minor, var year: Repository.YearOfStudy) {

    data class Lecturer(var name: String, var selected: Boolean = true)

    var lecturers: ObservableList<Lecturer> = FXCollections.observableArrayList<Lecturer>()

    // Ovo svojstvo služi da se čuva informacija o tome da li je izabran ovaj kurs na listi dostupnih kurseva
    var selected: Boolean by property(true)
    val selectedProperty = getProperty(CourseDef::selected)
}
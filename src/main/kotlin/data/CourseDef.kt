package data

import javafx.beans.property.ObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.getProperty
import tornadofx.property

/** Ova klasa služi samo da opiše kurs koji se daje na fakultetu. Pojavljuje se samo na mestima
 *  gde se pravi izbor kurseva. Ne bavi se time kada se tačno taj kurs odvija u toku nedelje
 *  (za tu upotrebu, videti [Course]).
 *
 */
data class CourseDef(var title: String) {

    class Lecturer(var name: String, selected: Boolean = true) {

        var selected: Boolean by property(selected)
        val selectedProperty: ObjectProperty<Boolean> = getProperty(Lecturer::selected)

        override fun toString(): String {
            return "Lecturer(name='$name', selected=$selected)"
        }
    }

    var lecturers: ObservableList<Lecturer> = FXCollections.observableArrayList<Lecturer>()

    // Ovo svojstvo služi da se čuva informacija o tome da li je izabran ovaj kurs na listi dostupnih kurseva
    var selected: Boolean by property(false)
    val selectedProperty = getProperty(CourseDef::selected)
}
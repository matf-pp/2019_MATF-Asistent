package data

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import scheduler.Day

/**
 * Osnovna klasa za čuvanje podataka o odabranim kursevima.
 * Ovo će biti klasa koju planer sme da menja u toku svog izvršavanja.
 */

@PlanningEntity
data class Course(
    var id: Int = 0, // Potrebno nešto što će biti jedinstveno za svaki objekat, može da bude redni broj?
    var title: String = "",
    var type: Char = ' ', // Ova promenljiva označava da li su u pitanju vežbe 'v' ili predavanja 'p'
    var dayOfWeek: String = "", // Potrebno je da ova promenljiva bude tipa String da bi mogla da se poredi sa imenom iz klase Day
    var classroom: String = "", // Potrebna informacija o zgradi u kojoj se odrzavaja kurs ( 'jag' ili 'trg')
    var start: Int = 0,
    var duration: Int = 3,
    var selected: Boolean = false
)
{
    var assignedDay: Day ?= null
        @PlanningVariable(valueRangeProviderRefs = ["dayRange"], nullable = true)
        get () = field

    fun calculateIntervalBetween(other: Course): Int {
        val end: Int = this.start + this.duration
        val otherEnd: Int = other.start + other.duration

        return when {
            end <= other.start -> other.start - end
            otherEnd <= this.start -> this.start - otherEnd
            else -> -1
        }
    }

    override fun toString(): String {
        return "Course(id=$id, title='$title', type=$type, dayOfWeek='$dayOfWeek', assignedDay='$assignedDay', classroom='$classroom', start=$start, duration=$duration)"
    }
}
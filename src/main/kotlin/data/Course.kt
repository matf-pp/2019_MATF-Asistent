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
    var major: Repository.Major,
    var year: Int,
    var semester: Int,
    var title: String,
    var code: String,
    var type: Char, // Ova promenljiva označava da li su u pitanju vežbe 'v' ili predavanja 'p'
    var dayOfWeek: String, // Potrebno je da ova promenljiva bude tipa String da bi mogla da se poredi sa imenom iz klase Day
    var classroom: String, // Potrebna informacija o zgradi u kojoj se odrzavaja kurs ( 'jag' ili 'trg')
    var start: Int,
    var duration: Int = 3,
    var espbPoints: Int = 6,
    var selected: Boolean = false
)
{
    var assignedDay: Day ?= null
        @PlanningVariable(valueRangeProviderRefs = ["dayRange"])
        get () = field

    fun calculateIntervalBetween(other: Course): Int {
        val end: Int = this.start + duration
        val otherEnd: Int = start + duration

        return when {
            end < other.start -> other.start - end
            otherEnd < this.start -> this.start - otherEnd
            else -> -1
        }
    }
}
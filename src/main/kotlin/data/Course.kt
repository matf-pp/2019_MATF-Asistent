package data

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import scheduler.Day
import java.time.DayOfWeek

/**
 * Osnovna klasa za čuvanje podataka o odabranim kursevima.
 * Ovo će biti klasa koju planer sme da menja u toku svog izvršavanja.
 */

@PlanningEntity
data class Course(
    var title: String = "",
    var type: Type = Type.LECTURE, // Ova promenljiva označava da li su u pitanju vežbe, predavanja ili praktikum
    var dayOfWeek: DayOfWeek= DayOfWeek.MONDAY,
    var classroom: Classroom = Classroom.TRG, // Potrebna informacija o zgradi u kojoj se odrzavaja kurs ( trg, jagic ili n sale)
    var start: Int = 0,
    var duration: Int = 3,
    var lecturer: String = "",
    var id: Int = getNextId() // TODO Potrebno nešto što će biti jedinstveno za svaki objekat, da li može hashCode?
)
{
    enum class Type {
        LECTURE, EXERCISE, PRACTICUM
    }

    enum class Classroom {
        TRG, JAG, N
    }

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

    companion object {
        private var id = 0

        fun getNextId() = id++
    }
}
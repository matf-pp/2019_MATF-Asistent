package data

import javafx.scene.paint.Color
import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import scheduler.Day
import tornadofx.*
import java.lang.Math.abs
import java.time.DayOfWeek
import javax.json.JsonObject

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
    var classroomText: String = "", // Naziv kabineta
    var start: Int = 0,
    var duration: Int = 3,
    var lecturer: String = "",
    var id: Int = getNextId()
) : JsonModel
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

    fun getColor(): Color {
        var seed = title.hashCode()

        val red = abs(seed % 256)
        seed /= 256
        val green = abs(seed % 256)
        seed /= 256
        val blue = abs(seed % 256)

        return Color.rgb(red, green, blue).desaturate().brighter()
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("title", title)
            add("type", type.ordinal)
            add("dayOfWeek", dayOfWeek.value)
            add("classroom", classroom.ordinal)
            add("classroomText", classroomText)
            add("start", start)
            add("duration", duration)
            add("lecturer", lecturer)
            add("id", id)
            add("assignedDay", assignedDay?.dayName?.value)
        }
    }

    override fun updateModel(json: JsonObject) {

        // NullPointerException se propagira i obrađuje na drugom mestu
        with(json) {
            title = string("title")!!
            type = Type.values()[int("type")!!]
            dayOfWeek = DayOfWeek.of(int("dayOfWeek")!!)
            classroom = Classroom.values()[int("classroom")!!]
            classroomText = string("classroomText")!!
            start = int("start")!!
            duration = int("duration")!!
            lecturer = string("lecturer")!!
            id = int("id")!!
            assignedDay = int("assignedDay")?.let { Day(DayOfWeek.of(it)) }
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
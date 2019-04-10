package scheduler

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable

/**
 * Ovo će biti klasa koju će planer moći da menja tj. da dodeljuje satima kurseve
 */

@PlanningEntity
class Hour (val dayName: String)
{
    private var course : Course
        @PlanningVariable(valueRangeProviderRefs = arrayOf("courseRange"))
        get() = this.course
        set(value) {
            this.course = value
        }
}
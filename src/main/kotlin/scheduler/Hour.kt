package scheduler

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable

/**
 * Ovo će biti klasa koju će planer moći da menja tj. da dodeljuje satima kurseve
 */

@PlanningEntity
class Hour (val dayName: String)
{
    var course: Course? = null
        @PlanningVariable(valueRangeProviderRefs = ["courseRange"])
        get() = field
}
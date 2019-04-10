package scheduler

import  org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

/** Ovo će biti klasa koja predstavlja problem koji se rešava, kao i rešenje problema
*/

@PlanningSolution
class Timetable {
    private val courseList: List<Course>
        @ValueRangeProvider(id = "courseRange")
        @ProblemFactCollectionProperty
        get() = this.courseList

    private val hoursList: List<Hour>
        @PlanningEntityCollectionProperty
        get() = this.hoursList

    private var score: HardSoftScore
        @PlanningScore
        get () = this.score
        set (score: HardSoftScore) {
            this.score = score
        }
}
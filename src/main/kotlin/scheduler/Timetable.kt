package scheduler

import data.Course
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import tornadofx.JsonBuilder
import tornadofx.JsonModel
import tornadofx.toJSON
import tornadofx.toModel
import javax.json.JsonObject

/*
* * Ovo će biti klasa koja predstavlja problem koji se rešava, kao i rešenje problema
*/

@PlanningSolution
class Timetable (_courseList: List<Course> = emptyList(), _daysList: List<Day> = emptyList()) : JsonModel {

    @ConstraintConfigurationProvider
    var constraintConfiguration : TimetableConstraintConfiguration ?= null

    init {
        constraintConfiguration = TimetableConstraintConfiguration()
    }

    var courseList: List<Course> = _courseList
        @PlanningEntityCollectionProperty
        get () = field

    val daysList: List<Day> = _daysList
        @ValueRangeProvider(id = "dayRange")
        @ProblemFactCollectionProperty
        get() = field

    var score: HardSoftScore? = null
        @PlanningScore
        get () = field

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("days", courseList.toJSON())
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            courseList = getJsonArray("days").toModel()
        }
    }
}



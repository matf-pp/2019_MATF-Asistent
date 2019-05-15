package scheduler

import data.Course
import data.Repository
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import tornadofx.runAsync
import tornadofx.runLater
import tornadofx.ui
import java.time.DayOfWeek

fun generateTimetablesTask(courses: List<Course>) = runAsync {

    val solverFactory : SolverFactory<Timetable> = SolverFactory.createFromXmlResource("scheduler/solver/timetableSolverConfig.xml")
    val solver : Solver<Timetable> = solverFactory.buildSolver()

    solver.addEventListener {
        if (it.newBestSolution.score?.isFeasible == true) {
            updateBestSolution(it.newBestSolution)
        }
    }

    val dayList : List<Day> = listOf(Day(DayOfWeek.MONDAY),
                                     Day(DayOfWeek.TUESDAY),
                                     Day(DayOfWeek.WEDNESDAY),
                                     Day(DayOfWeek.THURSDAY),
                                     Day(DayOfWeek.FRIDAY))


    val unsolvedTimetable = Timetable(courses, dayList)
    val solvedTimetable : Timetable = solver.solve(unsolvedTimetable)

    if (solvedTimetable.score?.isFeasible == false) {
        // Nije moguće formirati raspored

        // Prazan raspored je indikator greške
        ui {
            Repository.bestTimetable = Timetable()
        }
    } else {
        updateBestSolution(solvedTimetable)
    }
}

fun updateBestSolution(timetable: Timetable) {
    runLater {
        Repository.bestTimetable = timetable
    }
}
package scheduler

import data.Course
import data.Repository
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import tornadofx.runAsync
import tornadofx.runLater
import java.time.DayOfWeek

fun generateTimetablesTask(courses: List<Course>) = runAsync {

    val solverFactory : SolverFactory<Timetable> = SolverFactory.createFromXmlResource("scheduler/solver/timetableSolverConfig.xml")
    val solver : Solver<Timetable> = solverFactory.buildSolver()

    val dayList : MutableList<Day> = mutableListOf( Day(DayOfWeek.MONDAY),
                                                    Day(DayOfWeek.TUESDAY),
                                                    Day(DayOfWeek.WEDNESDAY),
                                                    Day(DayOfWeek.THURSDAY),
                                                    Day(DayOfWeek.FRIDAY))

    val unsolvedTimetable = Timetable(courses, dayList)

    solver.addEventListener {
        if (it.newBestScore?.isSolutionInitialized == true) {
            updateBestSolution(it.newBestSolution)
            println(it.newBestScore)
        }
    }
    val solvedTimetable : Timetable = solver.solve(unsolvedTimetable)

    for(course in solvedTimetable.courseList) {
        println(course.toString())
    }

    println(solvedTimetable.score) // Najbolji pronađen rezultat
}

fun updateBestSolution(timetable: Timetable) {
    runLater {
        Repository.bestTimetable = timetable
    }
}
package scheduler

import data.Course
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import tornadofx.runAsync
import java.time.DayOfWeek

// TODO: odrediti koje parametre treba da prima ova metoda
fun generateTimetablesTask(courses: List<Course>) = runAsync {

    val solverFactory : SolverFactory<Timetable> = SolverFactory.createFromXmlResource("scheduler/solver/timetableSolverConfig.xml")
    val solver : Solver<Timetable> = solverFactory.buildSolver()

    val courseList : MutableList<Course> = mutableListOf(
        Course("Konstrukcija kompilatora",Course.Type.LECTURE, DayOfWeek.MONDAY, Course.Classroom.TRG, 12, 2),
        Course("Veštačka inteligencija",  Course.Type.EXERCISE, DayOfWeek.MONDAY, Course.Classroom.TRG, 14, 3),
        Course("Istraživanje podataka 1",  Course.Type.EXERCISE, DayOfWeek.TUESDAY, Course.Classroom.TRG, 8, 3),
        Course("Istraživanje podataka 1",  Course.Type.EXERCISE, DayOfWeek.TUESDAY, Course.Classroom.TRG, 11, 3),
        Course("Veštačka inteligencija",Course.Type.LECTURE, DayOfWeek.TUESDAY, Course.Classroom.TRG, 11, 2),
        Course("Veštačka inteligencija",Course.Type.LECTURE, DayOfWeek.TUESDAY, Course.Classroom.TRG, 14, 2),
        Course("Veštačka inteligencija",  Course.Type.EXERCISE, DayOfWeek.TUESDAY, Course.Classroom.TRG, 13, 3),
        Course("Programske paradigme",Course.Type.LECTURE, DayOfWeek.WEDNESDAY, Course.Classroom.TRG, 10, 2),
        Course("Programske paradigme",Course.Type.LECTURE, DayOfWeek.WEDNESDAY, Course.Classroom.TRG, 12, 2),
        Course("Statistika",Course.Type.LECTURE, DayOfWeek.WEDNESDAY, Course.Classroom.TRG, 15, 2),
        Course("Statistika",Course.Type.LECTURE, DayOfWeek.WEDNESDAY, Course.Classroom.TRG, 17, 2),
        Course("Programske paradigme",  Course.Type.EXERCISE, DayOfWeek.THURSDAY, Course.Classroom.JAG, 8, 3),
        Course("Konstrukcija kompilatora",  Course.Type.EXERCISE, DayOfWeek.THURSDAY, Course.Classroom.TRG, 8, 3),
        Course("Statistika",  Course.Type.EXERCISE, DayOfWeek.THURSDAY, Course.Classroom.TRG, 12, 3),
        Course("Istraživanje podataka 1",Course.Type.LECTURE, DayOfWeek.THURSDAY, Course.Classroom.TRG, 15, 2),
        Course("Programske paradigme",  Course.Type.EXERCISE, DayOfWeek.FRIDAY, Course.Classroom.TRG, 8, 3),
        Course("Konstrukcija kompilatora",  Course.Type.EXERCISE, DayOfWeek.FRIDAY, Course.Classroom.JAG, 14, 3),
        Course("Istraživanje podataka 1",  Course.Type.EXERCISE, DayOfWeek.FRIDAY, Course.Classroom.TRG, 14, 3)
    )

    val dayList : MutableList<Day> = mutableListOf( Day(DayOfWeek.MONDAY),
                                                    Day(DayOfWeek.TUESDAY),
                                                    Day(DayOfWeek.WEDNESDAY),
                                                    Day(DayOfWeek.THURSDAY),
                                                    Day(DayOfWeek.FRIDAY))

    val unsolvedTimetable = Timetable(courseList, dayList)

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
    //TODO
}
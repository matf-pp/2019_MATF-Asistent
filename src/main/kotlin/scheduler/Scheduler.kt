package scheduler

import data.Course
import gui.view.welcome.WelcomeScreenWizard
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import tornadofx.runAsync

// TODO: ovo bi trebalo da bude neki komputaciono-zahtevni zadatak preko kojeg se generiše raspored, i koji
// potencijalno daje uvid u napredak generisanja kroz updateProgress.
// TODO: odrediti koje parametre treba da prima ova metoda
fun generateTimetablesTask(viewModel: WelcomeScreenWizard.ViewModel) = runAsync {

    val solverFactory : SolverFactory<Timetable> = SolverFactory.createFromXmlResource("scheduler/solver/timetableSolverConfig.xml")
    val solver : Solver<Timetable> = solverFactory.buildSolver()

    val courseList : MutableList<Course> = mutableListOf(
        Course(1,  "Statistika",  Course.Type.EXERCISE, "ponedeljak", Course.Classroom.TRG, 8, 3),
        Course(2,  "Konstrukcija kompilatora",Course.Type.LECTURE, "ponedeljak", Course.Classroom.TRG, 12, 2),
        Course(3,  "Veštačka inteligencija",  Course.Type.EXERCISE, "ponedeljak", Course.Classroom.TRG, 14, 3),
        Course(4,  "Istraživanje podataka 1",  Course.Type.EXERCISE, "utorak", Course.Classroom.TRG, 8, 3),
        Course(5,  "Istraživanje podataka 1",  Course.Type.EXERCISE, "utorak", Course.Classroom.TRG, 11, 3),
        Course(6,  "Veštačka inteligencija",Course.Type.LECTURE, "utorak", Course.Classroom.TRG, 11, 2),
        Course(7,  "Veštačka inteligencija",Course.Type.LECTURE, "utorak", Course.Classroom.TRG, 14, 2),
        Course(8,  "Veštačka inteligencija",  Course.Type.EXERCISE, "utorak", Course.Classroom.TRG, 13, 3),
        Course(9,  "Programske paradigme",Course.Type.LECTURE, "sreda", Course.Classroom.TRG, 10, 2),
        Course(10,  "Programske paradigme",Course.Type.LECTURE, "sreda", Course.Classroom.TRG, 12, 2),
        Course(11,  "Statistika",Course.Type.LECTURE, "sreda", Course.Classroom.TRG, 15, 2),
        Course(12,  "Statistika",Course.Type.LECTURE, "sreda", Course.Classroom.TRG, 17, 2),
        Course(13,  "Programske paradigme",  Course.Type.EXERCISE, "četvrtak", Course.Classroom.JAG, 8, 3),
        Course(14,  "Konstrukcija kompilatora",  Course.Type.EXERCISE, "četvrtak", Course.Classroom.TRG, 8, 3),
        Course(15,  "Statistika",  Course.Type.EXERCISE, "četvrtak", Course.Classroom.TRG, 12, 3),
        Course(16,  "Istraživanje podataka 1",Course.Type.LECTURE, "četvrtak", Course.Classroom.TRG, 15, 2),
        Course(17,  "Programske paradigme",  Course.Type.EXERCISE, "petak", Course.Classroom.TRG, 8, 3),
        Course(18,  "Konstrukcija kompilatora",  Course.Type.EXERCISE, "petak", Course.Classroom.JAG, 14, 3),
        Course(19,  "Istraživanje podataka 1",  Course.Type.EXERCISE, "petak", Course.Classroom.TRG, 14, 3)
    )

    val dayList : MutableList<Day> = mutableListOf()

    for (i in 1..5) {
        var s = ""

        when(i) {
            1 -> s = "ponedeljak"
            2 -> s = "utorak"
            3 -> s = "sreda"
            4 -> s = "četvrtak"
            5 -> s = "petak"
        }
        dayList.add(Day(s))
    }

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
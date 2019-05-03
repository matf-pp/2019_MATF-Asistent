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
        Course(1,  "Statistika", 'v', "ponedeljak", "trg", 8, 3, true),
        Course(2,  "Konstrukcija kompilatora", 'p', "ponedeljak", "trg", 12, 2, true),
        Course(3,  "Veštačka inteligencija", 'v', "ponedeljak", "trg", 14, 3, true),
        Course(4,  "Istraživanje podataka 1", 'v', "utorak", "trg", 8, 3, true),
        Course(5,  "Istraživanje podataka 1", 'v', "utorak", "trg", 11, 3, true),
        Course(6,  "Veštačka inteligencija", 'p', "utorak", "trg", 11, 2, true),
        Course(7,  "Veštačka inteligencija", 'p', "utorak", "trg", 14, 2, true),
        Course(8,  "Veštačka inteligencija", 'v', "utorak", "trg", 13, 3, true),
        Course(9,  "Programske paradigme", 'p', "sreda", "trg", 10, 2, true),
        Course(10,  "Programske paradigme", 'p', "sreda", "trg", 12, 2, true),
        Course(11,  "Statistika", 'p', "sreda", "trg", 15, 2, true),
        Course(12,  "Statistika", 'p', "sreda", "trg", 17, 2, true),
        Course(13,  "Programske paradigme", 'v', "četvrtak", "jag", 8, 3, true),
        Course(14,  "Konstrukcija kompilatora", 'v', "četvrtak", "trg", 8, 3, true),
        Course(15,  "Statistika", 'v', "četvrtak", "trg", 12, 3, true),
        Course(16,  "Istraživanje podataka 1", 'p', "četvrtak", "trg", 15, 2, true),
        Course(17,  "Programske paradigme", 'v', "petak", "trg", 8, 3, true),
        Course(18,  "Konstrukcija kompilatora", 'v', "petak", "jag", 14, 3, true),
        Course(19,  "Istraživanje podataka 1", 'v', "petak", "trg", 14, 3, true)
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
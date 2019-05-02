package scheduler

import data.Course
import data.Repository
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory

/*
* Privremena klasa za testiranje solvera
 */

 fun main() {
        val solverFactory : SolverFactory<Timetable> = SolverFactory.createFromXmlResource("scheduler/solver/timetableSolverConfig.xml")
        val solver : Solver<Timetable> = solverFactory.buildSolver()

        val courseList : MutableList<Course> = mutableListOf(Course(1, Repository.Major.COMP_SCI, 3, 6, "Statistika", "M162", 'v', "ponedeljak", "trg", 8, 3, 6, true),
                                                             Course(2, Repository.Major.COMP_SCI, 3, 6, "Konstrukcija kompilatora", "R241", 'p', "ponedeljak", "trg", 12, 2, 6, true),
                                                             Course(3, Repository.Major.COMP_SCI, 3, 6, "Veštačka inteligencija", "R260", 'v', "ponedeljak", "trg", 14, 3, 6, true),
                                                             Course(4, Repository.Major.COMP_SCI, 3, 6, "Istraživanje podataka 1", "R274", 'v', "utorak", "trg", 8, 3, 6, true),
                                                             Course(5, Repository.Major.COMP_SCI, 3, 6, "Istraživanje podataka 1", "R274", 'v', "utorak", "trg", 11, 3, 6, true),
                                                             Course(6, Repository.Major.COMP_SCI, 3, 6, "Veštačka inteligencija", "R260", 'p', "utorak", "trg", 11, 2, 6, true),
                                                             Course(7, Repository.Major.COMP_SCI, 3, 6, "Veštačka inteligencija", "R260", 'p', "utorak", "trg", 14, 2, 6, true),
                                                             Course(8, Repository.Major.COMP_SCI, 3, 6, "Veštačka inteligencija", "R260", 'v', "utorak", "trg", 13, 3, 6, true),
                                                             Course(9, Repository.Major.COMP_SCI, 3, 6, "Programske paradigme", "R245", 'p', "sreda", "trg", 10, 2, 6, true),
                                                             Course(10, Repository.Major.COMP_SCI, 3, 6, "Programske paradigme", "R245", 'p', "sreda", "trg", 12, 2, 6, true),
                                                             Course(11, Repository.Major.COMP_SCI, 3, 6, "Statistika", "M162", 'p', "sreda", "trg", 15, 2, 6, true),
                                                             Course(12, Repository.Major.COMP_SCI, 3, 6, "Statistika", "M162", 'p', "sreda", "trg", 17, 2, 6, true),
                                                             Course(13, Repository.Major.COMP_SCI, 3, 6, "Programske paradigme", "R245", 'v', "četvrtak", "jag", 8, 3, 6, true),
                                                             Course(14, Repository.Major.COMP_SCI, 3, 6, "Konstrukcija kompilatora", "R241", 'v', "četvrtak", "trg", 8, 3, 6, true),
                                                             Course(15, Repository.Major.COMP_SCI, 3, 6, "Statistika", "M162", 'v', "četvrtak", "trg", 12, 3, 6, true),
                                                             Course(16, Repository.Major.COMP_SCI, 3, 6, "Istraživanje podataka 1", "R274", 'p', "četvrtak", "trg", 15, 2, 6, true),
                                                             Course(17, Repository.Major.COMP_SCI, 3, 6, "Programske paradigme", "R245", 'v', "petak", "trg", 8, 3, 6, true),
                                                             Course(18, Repository.Major.COMP_SCI, 3, 6, "Konstrukcija kompilatora", "R241", 'v', "petak", "jag", 14, 3, 6, true),
                                                             Course(19, Repository.Major.COMP_SCI, 3, 6, "Istraživanje podataka 1", "R274", 'v', "petak", "trg", 14, 3, 6, true))

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
        val solvedTimetable : Timetable = solver.solve(unsolvedTimetable)

       for(course in solvedTimetable.courseList) {
           println(course.toString())
       }

        println(solvedTimetable.score) // Najbolji pronađen rezultat
        println(solver.isTerminateEarly) // Da li je prekinut pre kraja
}

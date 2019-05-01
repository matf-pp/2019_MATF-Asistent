package scraper

import data.CourseDef
import data.Repository
import tornadofx.runAsync
import tornadofx.runLater

/*
TODO ovo bi trebao da bude neki zadatak koji učitava listu svih postojećih kurseva sa veba.

*/
fun fetchCourseListTask() = runAsync {
    Thread.sleep(3000) // Simuliranje neke spore operacije (učitavanje veb strane)

    // Test podaci za scenario: treća godina informatičkog smera
    addCourseDefToRepository(
        CourseDef("Relacione baze podataka", "R270", Repository.Minor.I, 3, 5, 3, 2),
        CourseDef("Prevođenje programskih jezika", "R240", Repository.Minor.I, 3, 5),
        CourseDef("Računarska grafika", "R255", Repository.Minor.I, 3, 5),
        CourseDef("Verovatnoća", "M161", Repository.Minor.I, 3, 5),
        CourseDef("Uvod u numeričku matematiku", "M150", Repository.Minor.I, 3, 5, 2, 2),

        CourseDef("Veštačka inteligencija", "R260", Repository.Minor.I, 3, 6),
        CourseDef("Istraživanje podataka 1", "R274", Repository.Minor.I, 3, 6),
        CourseDef("Programske paradigme", "R245", Repository.Minor.I, 3, 6),
        CourseDef("Izborni predmet R1 (TODO)", "R1", Repository.Minor.I, 3, 6),
        CourseDef("Statistika", "M162", Repository.Minor.I, 3, 6)
    )
}

private fun addCourseDefToRepository(vararg courseDefs: CourseDef) {
    runLater {
        Repository.allAvailableCourses.addAll(courseDefs)
    }
}
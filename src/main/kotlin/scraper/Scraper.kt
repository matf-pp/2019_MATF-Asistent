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

    // TODO implementirati zapravo: sledi samo test za scenario: treća godina informatičkog smera
    runLater {
        Repository.allAvailableCourses.addAll(
            CourseDef("Relacione baze podataka", "R270", Repository.Major.COMP_SCI, 3, 5, 3, 2),
            CourseDef("Prevođenje programskih jezika", "R240", Repository.Major.COMP_SCI, 3, 5),
            CourseDef("Računarska grafika", "R255", Repository.Major.COMP_SCI, 3, 5),
            CourseDef("Verovatnoća", "M161", Repository.Major.COMP_SCI, 3, 5),
            CourseDef("Uvod u numeričku matematiku", "M150", Repository.Major.COMP_SCI, 3, 5, 2, 2),

            CourseDef("Veštačka inteligencija", "R260", Repository.Major.COMP_SCI, 3, 6),
            CourseDef("Istraživanje podataka 1", "R274", Repository.Major.COMP_SCI, 3, 6),
            CourseDef("Programske paradigme", "R245", Repository.Major.COMP_SCI, 3, 6),
            CourseDef("Izborni predmet R1 (TODO)", "R1", Repository.Major.COMP_SCI, 3, 6),
            CourseDef("Statistika", "M162", Repository.Major.COMP_SCI, 3, 6)
        )
    }
}
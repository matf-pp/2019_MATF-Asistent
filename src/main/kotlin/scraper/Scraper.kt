package scraper

import data.Course
import data.Repository
import tornadofx.runAsync
import tornadofx.ui
import java.time.DayOfWeek

/*
TODO ovo bi trebao da bude neki zadatak koji učitava listu postojećih kurseva sa veba, okvirno-govoreći u obliku:
    M-smer:
        I godina:
            Analiza
            Geometrija
        II godina:
    I-smer:
        I godina...

*/
fun fetchCourseListTask(major: Repository.Major) = runAsync {
    Thread.sleep(3000) // Neko čekanje pri učitavanju
    when (major) {
        Repository.Major.COMP_SCI -> {
            ui {
                Repository.allAvailableCourses.addAll(
                    Course(Repository.Major.COMP_SCI, 1, 1, "Programiranje 1", "P101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Diskretne strukture 1", "M101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Uvod u organizaciju i arhitekturu računara 1", "P101", 'x', DayOfWeek.MONDAY, 0)
                )
            }

        }
        Repository.Major.MATH -> {
            ui {
                Repository.allAvailableCourses.addAll(
                    Course(Repository.Major.COMP_SCI, 1, 1, "Analiza", "P101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Linearna algebra", "P101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Programiranje 1", "P101", 'x', DayOfWeek.MONDAY, 0)
                )
            }

        }
        Repository.Major.ASTRONOMY -> {
            ui {
                Repository.allAvailableCourses.addAll(
                    Course(Repository.Major.COMP_SCI, 1, 1, "Opšta astronomija 1", "P101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Diskretne strukture 1", "P101", 'x', DayOfWeek.MONDAY, 0),
                    Course(Repository.Major.COMP_SCI, 1, 1, "Uvod u organizaciju i arhitekturu računara 1", "P101", 'x', DayOfWeek.MONDAY, 0)
                )
            }
        }
    }
}
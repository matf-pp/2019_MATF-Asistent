package scraper

import data.CourseListItem
import data.Repository
import tornadofx.runAsync
import tornadofx.ui

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
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Programiranje 1"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Diskretne strukture 1"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Uvod u organizaciju i arhitekturu računara 1")
                )
            }

        }
        Repository.Major.MATH -> {
            ui {
                Repository.allAvailableCourses.addAll(
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Analiza"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Linearna algebra"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Programiranje 1")
                )
            }

        }
        Repository.Major.ASTRONOMY -> {
            ui {
                Repository.allAvailableCourses.addAll(
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Opšta astronomija 1"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Diskretne strukture 1"),
                    CourseListItem(Repository.Major.COMP_SCI, 1, 1, "Uvod u organizaciju i arhitekturu računara 1")
                )
            }
        }
    }
}
package scraper

import data.Repository
import tornadofx.runAsync

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
    Thread.sleep(3000)
    when (major) {
        Repository.Major.COMP_SCI -> TODO()
        Repository.Major.MATH -> TODO()
        Repository.Major.ASTRONOMY -> TODO()
    }
}
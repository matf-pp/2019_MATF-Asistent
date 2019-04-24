package data

/** Ova klasa služi samo da opiše kurs koji se daje na fakultetu. Pojavljuje se samo na mestima
 *  gde se pravi izbor kurseva. Ne bavi se time kada se tačno taj kurs odvija u toku nedelje
 *  (za tu upotrebu, videti [Course]).
 *
 */
data class CourseDef(
    var title: String,
    var code: String,
    var major: Repository.Major,
    var year: Int,
    var semester: Int,
    var lectureWeeklyHours: Int = 2,
    var exerciseWeeklyHours: Int = 3,
    var ects: Int = 6,
    /* TODO: da li bi ovde mogla da postoji informacija o tome ko su predavači na kursu?
            Ako da: dodati ta polja, ako ne: izbrisati ovaj komentar.
    */

    // Ovo svojstvo služi da se čuva informacija o tome da li je izabran ovaj kurs na listi dostupnih kurseva
    var selected: Boolean = false
)
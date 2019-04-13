package data

import java.time.DayOfWeek

/**
 * Osnovna klasa za čuvanje podataka o odabranim kursevima.
 * Ovo će biti klasa koju planer ne sme da menja u toku svog izvršavanja.
 */

data class Course(
    var major: Repository.Major,
    var year: Int,
    var semester: Int,
    var title: String,
    var code: String,
    var type: Char, // TODO šta je ova promenljiva?
    var dayOfWeek: DayOfWeek,
    var start: Int,
    var duration: Int = 3,
    var espbPoints: Int = 6,
    var selected: Boolean = false
)
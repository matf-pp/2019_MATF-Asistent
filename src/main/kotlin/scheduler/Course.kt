package scheduler

/**
 * Onovna klasa za čuvanje podataka o odabranim kursevima.
 * Ovo će biti klasa koju planer ne sme da menja u toku svog izvršavanja.
 */

class Course (val name: String, val type: Char, val professor: String, val classroom: String, val start: Int, val duration: Int)

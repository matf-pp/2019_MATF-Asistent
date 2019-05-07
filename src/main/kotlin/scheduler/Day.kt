package scheduler

import java.io.Serializable
import java.time.DayOfWeek

/**
 * Ovo će biti klasa koju planer neće moći da menja tj. da dodeljuje satima kurseve
 */

data class Day (var dayName: DayOfWeek = DayOfWeek.MONDAY) : Serializable {
    override fun toString(): String {
       return when (dayName) {
           DayOfWeek.MONDAY -> "ponedeljak"
           DayOfWeek.TUESDAY -> "utorak"
           DayOfWeek.WEDNESDAY -> "sreda"
           DayOfWeek.THURSDAY -> "četvrtak"
           DayOfWeek.FRIDAY -> "petak"

           else -> ""
       }
    }
}
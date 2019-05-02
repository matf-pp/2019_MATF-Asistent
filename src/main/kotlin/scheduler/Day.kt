package scheduler

import java.io.Serializable

/**
 * Ovo će biti klasa koju planer neće moći da menja tj. da dodeljuje satima kurseve
 */

class Day (val dayName: String) : Serializable {
    override fun toString(): String {
        return dayName
    }
}
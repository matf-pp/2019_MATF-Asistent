package scheduler

import java.io.Serializable
import java.time.DayOfWeek

/**
 * Ovo će biti klasa koju planer neće moći da menja
 */

data class Day (var dayName: DayOfWeek = DayOfWeek.MONDAY) : Serializable
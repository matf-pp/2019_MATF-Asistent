package scheduler

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

/**
 * Klasa koja će čuvati težine ograničenja. Postoji tačno jedna instanca ove klase u rešenju.
 */

@ConstraintConfiguration(constraintPackage = "scheduler.solver")
class TimetableConstraintConfiguration {
    @ConstraintWeight("Day conflict") // Dodeljeni dan mora da odgovara danu iz rasporeda
    var dayConflict: HardSoftScore = HardSoftScore.ofHard(15)

    @ConstraintWeight("Course conflict")  // Student ne sme da ima više časova u isto vreme
    var courseConflict: HardSoftScore = HardSoftScore.ofHard(14)

    @ConstraintWeight("At least one") // Za svaki kurs mora postojati bar jedno predavanje i bar jedne vežbe
    var atLeastOne: HardSoftScore = HardSoftScore.ofHard(13)

    @ConstraintWeight("One type")  // Student treba da ima tačno jedan termin predavanja iz nekog kursa
    var oneLecture: HardSoftScore = HardSoftScore.ofHard(12)

    @ConstraintWeight("Room conflict")  // Ako student treba da pređe iz jedne zgrade u drugu potrebno je bar sat vremena razmaka
    var roomConflict: HardSoftScore = HardSoftScore.ofHard(11)


    // Dodati odabrane želje studenta
}
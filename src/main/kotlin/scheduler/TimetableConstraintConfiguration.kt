package scheduler

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

/**
 * Klasa koja će čuvati težine ograničenja. Postoji tačno jedna instanca ove klase u rešenju.
 */

@ConstraintConfiguration
class TimetableConstraintConfiguration {
    @ConstraintWeight("Course conflict")  // Student ne sme da ima više časova u isto vreme
    var courseConflict: HardSoftScore = HardSoftScore.ofHard(10)
    @ConstraintWeight("One lecture")  // Student treba da ima tačno jedan termin predavanja iz nekog kursa
    var oneLecture: HardSoftScore = HardSoftScore.ofHard(8)
    @ConstraintWeight("One exercise")  // Student treba da ima tačno jedan termin vežbi iz nekog kursa
    var oneExercise: HardSoftScore = HardSoftScore.ofHard(8)
    @ConstraintWeight("Room conflict")  // Ako student treba da pređe iz jedne zgrade u drugu potrebno je bar sat vremena razmaka
    var roomConflict: HardSoftScore = HardSoftScore.ofHard(9)
    @ConstraintWeight("Block")  // Predavanja i vežbe se određuju u bloku
    var block: HardSoftScore = HardSoftScore.ofHard(10)

    @ConstraintWeight("Minimize breaks")  // Minimalizovati pauze između časova
    var minBreaks: HardSoftScore = HardSoftScore.ofSoft(10)

    // Dodati odabrane želje studenta
}
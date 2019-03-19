package data

import tornadofx.observableList

/** Objekat koji će služiti da se u njemu čuvaju Kolekcije podataka iz različitih izvora, uključujući
 *  podatke koje generiše korisnik, podatke sa mreže, i podatke iz baze podataka.
 *
 */
object Repository {

    val notifications = observableList<Notification>()

    init {
        // Test podaci
        notifications.addAll((0..10).map { Notification("Obaveštenje $it", "Neki opis") })
    }

}
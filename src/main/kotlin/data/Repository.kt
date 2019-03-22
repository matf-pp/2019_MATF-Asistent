package data

import tornadofx.observableList

/** Objekat koji će služiti da se u njemu čuvaju Kolekcije podataka iz različitih izvora, uključujući
 *  podatke koje generiše korisnik, podatke sa mreže, i podatke iz baze podataka.
 *
 */
object Repository {

    /**
     * Iako ispod grma leži jednostavna String informacija, koristeći nabrojivu klasu garantujemo veću
     * bezbednost u fazi kompilacije, kao i zaobilaženje nekih greški poput slučajne greške u kucanju
     * pri sirovom upoređivanju String-ova. npr. "Informatika == informatika".
     */
    enum class Smerovi {
        INFORMATIKA, MATEMATIKA, ASTRONOMIJA;

        override fun toString() = when (this) {
            INFORMATIKA -> "Informatika"
            MATEMATIKA -> "Matematika"
            ASTRONOMIJA -> "Astronomija"
        }
    }

    val smerovi = observableList(Smerovi.INFORMATIKA, Smerovi.MATEMATIKA, Smerovi.ASTRONOMIJA)

    val notifications = observableList<Notification>()

    init {
        // Test podaci
        notifications.addAll((0..10).map { Notification("Obaveštenje $it", "Neki opis") })
    }

}
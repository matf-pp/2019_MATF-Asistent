# MATF Asistent :robot:

## Rezime 
Ideja projekta je pomoć studentima pri organizaciji vremena. Program učitava raspored sa veb strane fakulteta. Korisnik zatim može da izabere kurseve koje želi da pohađa, nakon čega program automatski generiše odgovarajući raspored.

![Formiran raspored](https://github.com/matf-pp2019/ConstPlusPlus/blob/master/screenshots/formiran.png)

Biće korišćen programski jezik Kotlin, i:
- [TorandoFX](https://tornadofx.io/), biblioteka za grafički interfejs, koja koristi JavaFX u pozadini,
- [OptaPlanner](https://www.optaplanner.org/), biblioteka za programiranje ograničenja, korišćena za formiranje rasporeda,
- [Drools](https://www.drools.org/), sistem za upravljanje pravilima, korišćen za definisanje ograničenja,
- [Jsoup](https://jsoup.org/), biblioteka za parsiranje veb stranica, korišćena za parsiranje rasporeda.

Za kompilaciju je preporučeno razvojno okruženje IntelliJIDEA. Projekat koristi Gradle sistem za kompilaciju. Sve potrebne biblioteke se automatski preuzimaju. Projekat se kompilira i pokreće kao standardni IntelliJIDEA projekat.

Izvršiva distribucija programa je `.jar` datoteka, za čije izvršavanje je potrebna Java virtuelna mašina.

## Članovi tima
- Olivera Popović,  [popovic-olivera](https://github.com/popovic-olivera)
- Aleksandar Stefanović, [aleksandar-stefanovic](https://github.com/aleksandar-stefanovic)

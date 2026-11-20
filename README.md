# Okos Raktárkezelő (Java OOP Beadandó)

Konzolos raktárkezelő alkalmazás Java nyelven, Maven projektstruktúrával, objektumorientált alapelvekre (öröklődés, polimorfizmus, absztrakt osztályok, interfészek, saját kivételkezelés, fájlkezelés és JUnit 5 egységtesztek) építve.

## Csomagstruktúra (`hu.raktar`)

* `domain` – `Product` (absztrakt ősosztály), `PhysicalProduct`, `DigitalProduct`, `Sellable` interfész
* `service` – `InventoryService` (készletkezelés, eladás, ár szerinti rendezés)
* `persistence` – `FileHandler` (állapot mentése és visszatöltése fájlból)
* `exception` – `OutOfStockException` (készlethiányt jelző saját kivétel)
* `ui` – `Main` (konzolos menürendszer)

## Fő funkciók

* Fizikai és digitális termékek felvétele és listázása
* Termékek rendezése ár szerint
* Készletcsökkentés / eladás kivételkezeléssel (`OutOfStockException`)
* Adatok mentése és betöltése fájlból
* Egységtesztek JUnit 5 keretrendszerrel (`src/test`)

## Futtatás és tesztelés

1. Nyisd meg a projektet IntelliJ IDEA-ban, és frissítsd a Maven függőségeket (`pom.xml`).
2. Az alkalmazás indításához futtasd a `hu.raktar.ui.Main` osztály `main` metódusát.
3. A tesztek futtatásához indítsd el az `InventoryServiceTest` osztályt (vagy futtasd a `mvn test` parancsot).
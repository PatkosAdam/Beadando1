# Tervezési dokumentáció – Okos Raktárkezelő

## 1. Problem Statement
A raktárkezelők és kisvállalkozások számára gyakran okoz gondot, hogy egyszerre kezeljenek fizikai (súllyal és raktárkészlettel bíró) és digitális (licenckulccsal és fájlmérettel rendelkező) termékeket egy egységes, átlátható rendszerben. Az alkalmazás célja egy konzolos raktárkezelő biztosítása, amely egységes objektummodellben kezeli a termékeket, ellenőrzi a készletet, kivétellel jelzi a készlethiányt, rendezést támogat, és a futások között fájlból menti/tölti vissza a készletet.

## 2. User Storyk
* **US-01:** Raktárosként szeretnék új fizikai és digitális termékeket felvenni, hogy a teljes készlet nyilvántartható legyen.
* **US-02:** Raktárosként szeretném listázni a raktárban lévő termékeket, hogy lássam az aktuális adatokat és készleteket.
* **US-03:** Raktárosként szeretném eladni a kiválasztott termékből megadott mennyiséget, és hibajelzést kapni, ha nincs elég készlet (`OutOfStockException`).
* **US-04:** Raktárosként szeretném ár szerint rendezni a termékeket, hogy könnyebben áttekintsem az árkategóriákat.
* **US-05:** Raktárosként szeretném a készletet kilépéskor menteni és indításkor visszatölteni, hogy ne vesszenek el az adatok.

## 3. UML osztálydiagram
A szerkeszthető PlantUML forrás a `docs/class-diagram.puml` fájlban található.

```mermaid
classDiagram
    class Sellable {
        <<interface>>
        +sell(quantity: int) void
        +getPrice() double
    }
    class Product {
        <<abstract>>
        -id: String
        -name: String
        -price: double
        -quantity: int
        +getProductDetails()* String
    }
    class PhysicalProduct {
        -weightKg: double
        +sell(quantity: int) void
        +getProductDetails() String
    }
    class DigitalProduct {
        -licenseKey: String
        -fileSizeMb: double
        +sell(quantity: int) void
        +getProductDetails() String
    }
    class OutOfStockException {
        <<exception>>
    }
    class ProductFactory {
        +createPhysicalProduct(...) Product
        +createDigitalProduct(...) Product
    }
    class InventoryService {
        -products: List~Product~
        +addProduct(product: Product) void
        +sortProductsByPrice() void
        +sellProduct(id: String, quantity: int) void
    }
    class FileHandler {
        +loadInventory(service: InventoryService) void
        +saveInventory(service: InventoryService) void
    }
    class Main {
        +main(String[] args) void
    }

    Product <|-- PhysicalProduct
    Product <|-- DigitalProduct
    Sellable <|.. PhysicalProduct
    Sellable <|.. DigitalProduct
    Exception <|-- OutOfStockException
    Main --> InventoryService
    Main --> ProductFactory
    Main --> FileHandler
    InventoryService o-- Product
    ProductFactory ..> PhysicalProduct
    ProductFactory ..> DigitalProduct
    Sellable ..> OutOfStockException
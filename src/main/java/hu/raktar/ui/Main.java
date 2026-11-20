package hu.raktar.ui;

import hu.raktar.domain.DigitalProduct;
import hu.raktar.domain.PhysicalProduct;
import hu.raktar.exception.OutOfStockException;
import hu.raktar.persistence.FileHandler;
import hu.raktar.service.InventoryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryService service = new InventoryService();
        FileHandler fileHandler = new FileHandler();
        Scanner scanner = new Scanner(System.in);

        fileHandler.loadInventory(service);

        boolean running = true;
        while (running) {
            System.out.println("\n--- OKOS RAKTÁRKEZELŐ ---");
            System.out.println("1. Termékek listázása");
            System.out.println("2. Új fizikai termék hozzáadása");
            System.out.println("3. Új digitális termék hozzáadása");
            System.out.println("4. Termékek rendezése ár szerint");
            System.out.println("5. Termék eladása (készlet csökkentés)");
            System.out.println("6. Mentés fájlba és kilépés");
            System.out.print("Válassz egy opciót: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // enter lenyelése

                switch (choice) {
                    case 1:
                        if (service.getProducts().isEmpty()) {
                            System.out.println("A raktár üres.");
                        } else {
                            for (var p : service.getProducts()) {
                                System.out.println(p.getProductDetails());
                            }
                        }
                        break;
                    case 2:
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Név: ");
                        String name = scanner.nextLine();
                        System.out.print("Ár (Ft): ");
                        double price = scanner.nextDouble();
                        System.out.print("Mennyiség (db): ");
                        int qty = scanner.nextInt();
                        System.out.print("Súly (kg): ");
                        double weight = scanner.nextDouble();
                        scanner.nextLine(); // enter lenyelése

                        service.addProduct(new PhysicalProduct(id, name, price, qty, weight));
                        System.out.println("Fizikai termék sikeresen hozzáadva!");
                        break;
                    case 3:
                        System.out.print("ID: ");
                        String dId = scanner.nextLine();
                        System.out.print("Név: ");
                        String dName = scanner.nextLine();
                        System.out.print("Ár (Ft): ");
                        double dPrice = scanner.nextDouble();
                        System.out.print("Mennyiség (db): ");
                        int dQty = scanner.nextInt();
                        scanner.nextLine(); // enter lenyelése
                        System.out.print("Licenckulcs: ");
                        String licenseKey = scanner.nextLine();
                        System.out.print("Fájlméret (MB): ");
                        double fileSizeMb = scanner.nextDouble();
                        scanner.nextLine(); // enter lenyelése

                        service.addProduct(new DigitalProduct(dId, dName, dPrice, dQty, licenseKey, fileSizeMb));
                        System.out.println("Digitális termék sikeresen hozzáadva!");
                        break;
                    case 4:
                        service.sortProductsByPrice();
                        System.out.println("Termékek rendezve ár szerint!");
                        break;
                    case 5:
                        System.out.print("Add meg a termék ID-ját: ");
                        String sellId = scanner.nextLine();
                        System.out.print("Eladandó mennyiség: ");
                        int sellQty = scanner.nextInt();
                        scanner.nextLine(); // enter lenyelése

                        service.sellProduct(sellId, sellQty);
                        System.out.println("Sikeres eladás!");
                        break;
                    case 6:
                        fileHandler.saveInventory(service);
                        running = false;
                        System.out.println("Kilépés...");
                        break;
                    default:
                        System.out.println("Érvénytelen választás!");
                }
            } catch (OutOfStockException | IllegalArgumentException e) {
                System.out.println("HIBA: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Helytelen bemenet!");
                scanner.nextLine(); // hibás bemenet ürítése
            }
        }
        scanner.close();
    }
}
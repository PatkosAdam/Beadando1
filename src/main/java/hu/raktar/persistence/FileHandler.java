package hu.raktar.persistence;

import hu.raktar.domain.DigitalProduct;
import hu.raktar.domain.PhysicalProduct;
import hu.raktar.domain.Product;
import hu.raktar.service.InventoryService;
import hu.raktar.service.ProductFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler {
    private static final String FILE_NAME = "inventory.txt";
    private final ProductFactory productFactory = new ProductFactory();

    // Adatok mentése szöveges fájlba try-with-resources segítségével[cite: 15]
    public void saveInventory(InventoryService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Product p : service.getProducts()) {
                if (p instanceof PhysicalProduct pp) {
                    writer.println("PHYSICAL;" + pp.getId() + ";" + pp.getName() + ";"
                            + pp.getPrice() + ";" + pp.getQuantity() + ";" + pp.getWeightInKg());
                } else if (p instanceof DigitalProduct dp) {
                    writer.println("DIGITAL;" + dp.getId() + ";" + dp.getName() + ";"
                            + dp.getPrice() + ";" + dp.getQuantity() + ";"
                            + dp.getLicenseKey() + ";" + dp.getFileSizeMb());
                }
            }
            System.out.println("Adatok sikeresen elmentve!");
        } catch (IOException e) {
            System.out.println("Hiba történt a mentés során: " + e.getMessage());
        }
    }

    // Adatok betöltése a fájrból
    public void loadInventory(InventoryService service) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(";");
                if ("PHYSICAL".equals(parts[0]) && parts.length >= 6) {
                    String id = parts[1];
                    String name = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    double weight = Double.parseDouble(parts[5]);

                    service.addProduct(productFactory.createPhysicalProduct(id, name, price, quantity, weight));
                } else if ("DIGITAL".equals(parts[0]) && parts.length >= 7) {
                    String id = parts[1];
                    String name = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    String licenseKey = parts[5];
                    double fileSizeMb = Double.parseDouble(parts[6]);

                    service.addProduct(productFactory.createDigitalProduct(id, name, price, quantity, licenseKey, fileSizeMb));
                }
            }
            System.out.println("Adatok sikeresen betöltve!");
        } catch (IOException e) {
            System.out.println("Hiba történt a betöltés során: " + e.getMessage());
        }
    }
}
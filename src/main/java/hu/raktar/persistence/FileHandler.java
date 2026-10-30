package hu.raktar.persistence;

import hu.raktar.domain.PhysicalProduct;
import hu.raktar.domain.Product;
import hu.raktar.service.InventoryService;

import java.io.*;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "inventory.txt";

    // Adatok mentése szöveges fájlba try-with-resources segítségével
    public void saveInventory(InventoryService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Product p : service.getProducts()) {
                if (p instanceof PhysicalProduct) {
                    PhysicalProduct pp = (PhysicalProduct) p;
                    writer.println("PHYSICAL;" + pp.getId() + ";" + pp.getName() + ";" + pp.getPrice() + ";" + pp.getQuantity() + ";" + pp.getWeightInKg());
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
            return; // Ha még nincs fájl, nem csinál semmit
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals("PHYSICAL")) {
                    String id = parts[1];
                    String name = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    double weight = Double.parseDouble(parts[5]);

                    service.addProduct(new PhysicalProduct(id, name, price,quantity, weight));
                }
            }
            System.out.println("Adatok sikeresen betöltve!");
        } catch (IOException e) {
            System.out.println("Hiba történt a betöltés során: " + e.getMessage());
        }
    }
}
package hu.raktar.service;

import hu.raktar.domain.DigitalProduct;
import hu.raktar.domain.PhysicalProduct;
import hu.raktar.domain.Product;

public class ProductFactory {

    public Product createPhysicalProduct(String id, String name, double price, int quantity, double weightKg) {
        return new PhysicalProduct(id, name, price, quantity, weightKg);
    }

    public Product createDigitalProduct(String id, String name, double price, int quantity,
                                        String licenseKey, double fileSizeMb) {
        return new DigitalProduct(id, name, price, quantity, licenseKey, fileSizeMb);
    }
}
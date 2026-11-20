package hu.raktar.service;

import hu.raktar.domain.Product;
import hu.raktar.exception.OutOfStockException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryService {
    // Kollekciók és generikusok követelmény: ArrayList a termékekhez
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("A termék nem lehet null!");
        }
        for (Product existing : products) {
            if (existing.getId().equals(product.getId())) {
                throw new IllegalArgumentException("Már létezik termék ezzel az ID-val: " + product.getId());
            }
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void sortProductsByPrice() {
        Collections.sort(products); // Comparable alapú rendezés
    }

    public void sellProduct(String productId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Az eladott mennyiségnek pozitívnak kell lennie!");
        }
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                if (p.getQuantity() < amount) {
                    throw new OutOfStockException("Nincs elegendő készlet a(z) " + p.getName() + " termékből!");
                }
                p.setQuantity(p.getQuantity() - amount);
                return;
            }
        }
        throw new IllegalArgumentException("Nem található termék ezzel az ID-val: " + productId);
    }
}
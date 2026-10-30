package hu.raktar.domain;

// Öröklődés: a PhysicalProduct kiterjeszti a Product osztályt
public class PhysicalProduct extends Product {
    private double weightInKg;

    public PhysicalProduct(String id, String name, double price, int quantity, double weightInKg) {
        super(id, name, price, quantity); // Az ősosztály konstruktorának hívása
        this.weightInKg = weightInKg;
    }

    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        if (weightInKg < 0) {
            throw new IllegalArgumentException("A súly nem lehet negatív!");
        }
        this.weightInKg = weightInKg;
    }

    // Polimorfizmus: felülírjuk az absztrakt metódust
    @Override
    public String getProductDetails() {
        return "Fizikai termék: " + getName() + " | Ár: " + getPrice() + " Ft | Készlet: " + getQuantity() + " db | Súly: " + weightInKg + " kg";
    }
}
package hu.raktar.domain;

import hu.raktar.exception.OutOfStockException;

public class DigitalProduct extends Product implements Sellable {
    private final String licenseKey;
    private final double fileSizeMb;

    public DigitalProduct(String id, String name, double price, int quantity, String licenseKey, double fileSizeMb) {
        super(id, name, price, quantity);
        if (fileSizeMb <= 0) {
            throw new IllegalArgumentException("A fájlméretnek pozitívnak kell lennie!");
        }
        this.licenseKey = licenseKey;
        this.fileSizeMb = fileSizeMb;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public double getFileSizeMb() {
        return fileSizeMb;
    }

    @Override
    public void sell(int quantity) throws OutOfStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Az eladott mennyiségnek pozitívnak kell lennie!");
        }
        if (getQuantity() < quantity) {
            throw new OutOfStockException("Nincs elegendő digitális licenc készleten: " + getName());
        }
        setQuantity(getQuantity() - quantity);
    }

    @Override
    public String getProductDetails() {
        return String.format("[Digitális] %s (ID: %s) - %.0f Ft | Készlet: %d db | Méret: %.1f MB | Licenc: %s",
                getName(), getId(), getPrice(), getQuantity(), fileSizeMb, licenseKey);
    }
}
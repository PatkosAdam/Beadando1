package hu.raktar.domain;

public abstract class Product implements Comparable<Product> {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setQuantity(quantity);
    }

    public abstract String getProductDetails();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Az ár nem lehet negatív!");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("A mennyiség nem lehet negatív!");
        }
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }
}
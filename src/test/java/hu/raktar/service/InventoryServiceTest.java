package hu.raktar.service;

import hu.raktar.domain.DigitalProduct;
import hu.raktar.domain.PhysicalProduct;
import hu.raktar.domain.Product;
import hu.raktar.exception.OutOfStockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    @Test
    void shouldAddAndSellPhysicalProduct() throws OutOfStockException {
        InventoryService service = new InventoryService();
        PhysicalProduct product = new PhysicalProduct("P1", "Laptop", 250000.0, 10, 2.1);
        service.addProduct(product);

        service.sellProduct("P1", 3);

        assertEquals(7, product.getQuantity());
    }

    @Test
    void shouldThrowOutOfStockExceptionWhenSellingTooMuch() {
        InventoryService service = new InventoryService();
        DigitalProduct digital = new DigitalProduct("D1", "Antivírus", 12000.0, 2, "KEY-999", 120.0);
        service.addProduct(digital);

        assertThrows(OutOfStockException.class, () -> service.sellProduct("D1", 5));
    }

    @Test
    void shouldSortProductsByPriceAscending() {
        InventoryService service = new InventoryService();
        service.addProduct(new PhysicalProduct("P2", "Monitor", 80000.0, 4, 4.5));
        service.addProduct(new PhysicalProduct("P1", "Egér", 8000.0, 15, 0.2));

        service.sortProductsByPrice();

        assertEquals("P1", service.getProducts().get(0).getId());
        assertEquals("P2", service.getProducts().get(1).getId());
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateProductId() {
        InventoryService service = new InventoryService();
        service.addProduct(new PhysicalProduct("ID-100", "Billentyűzet", 15000.0, 5, 0.8));

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new DigitalProduct("ID-100", "E-könyv", 4500.0, 10, "KEY-123", 15.0))
        );
        assertEquals(1, service.getProducts().size());
    }

    @Test
    void shouldThrowExceptionWhenAddingNullProduct() {
        InventoryService service = new InventoryService();

        assertThrows(IllegalArgumentException.class, () -> service.addProduct(null));
    }

    @Test
    void shouldThrowExceptionWhenSellingNonExistentProduct() {
        InventoryService service = new InventoryService();
        service.addProduct(new PhysicalProduct("P1", "Laptop", 250000.0, 10, 2.1));

        assertThrows(IllegalArgumentException.class, () -> service.sellProduct("NEM-LETEZIK", 1));
    }

    @Test
    void shouldThrowExceptionWhenSellingZeroOrNegativeAmount() {
        InventoryService service = new InventoryService();
        service.addProduct(new PhysicalProduct("P1", "Laptop", 250000.0, 10, 2.1));

        assertThrows(IllegalArgumentException.class, () -> service.sellProduct("P1", 0));
        assertThrows(IllegalArgumentException.class, () -> service.sellProduct("P1", -3));
    }

    @Test
    void shouldCreateProductsUsingProductFactory() {
        ProductFactory factory = new ProductFactory();
        Product physical = factory.createPhysicalProduct("P10", "Nyomtató", 45000.0, 3, 6.2);
        Product digital = factory.createDigitalProduct("D10", "IDE Licenc", 30000.0, 50, "LIC-2026", 850.0);

        assertInstanceOf(PhysicalProduct.class, physical);
        assertInstanceOf(DigitalProduct.class, digital);
        assertTrue(physical.getProductDetails().contains("ID: P10"));
        assertTrue(digital.getProductDetails().contains("D10"));
    }
}
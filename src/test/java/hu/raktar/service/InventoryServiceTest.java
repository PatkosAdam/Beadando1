package hu.raktar.service;

import hu.raktar.domain.DigitalProduct;
import hu.raktar.domain.PhysicalProduct;
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
}
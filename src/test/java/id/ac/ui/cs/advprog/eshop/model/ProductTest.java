package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;
    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("test-product-id123");
        this.product.setProductName("test-product-name123");
        this.product.setProductQuantity(123);
    }

    @Test
    void testGetProductId() {
        String expected = "test-product-id123";
        String actual = this.product.getProductId();
        assertEquals(expected, actual);
    }

    @Test
    void testGetProductName() {
        String expected = "test-product-name123";
        String actual = this.product.getProductName();
        assertEquals(expected, actual);
    }

    @Test
    void testGetProductQuantity() {
        int expected = 123;
        int actual = this.product.getProductQuantity();
        assertEquals(expected, actual);
    }
}

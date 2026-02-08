package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("test-product-id123");
        product.setProductName("test-product-name123");
        product.setProductQuantity(123);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();

        String expectedId = product.getProductId();
        String actualId = savedProduct.getProductId();
        assertEquals(expectedId, actualId);

        String expectedName = product.getProductName();
        String actualName = savedProduct.getProductName();
        assertEquals(expectedName, actualName);

        int expectedQuantity = product.getProductQuantity();
        int actualQuantity = savedProduct.getProductQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("test-product-id1");
        product1.setProductName("test-product-name1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("test-product-id2");
        product2.setProductName("test-product-name2");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();

        String expectedId = product1.getProductId();
        String actualId = savedProduct.getProductId();
        assertEquals(expectedId, actualId);

        savedProduct = productIterator.next();

        expectedId = product2.getProductId();
        actualId = savedProduct.getProductId();
        assertEquals(expectedId, actualId);
    }
}

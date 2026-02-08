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
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("test-product-id-edit");
        product.setProductName("test-product-name-edit");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("test-product-id-edit"); // id stays the same
        updatedProduct.setProductName("test-product-name-edited");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.edit(updatedProduct);
        assertNotNull(result);
        assertEquals("test-product-name-edited", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        Product storedProduct = productRepository.findById("test-product-id-edit");
        assertEquals("test-product-name-edited", storedProduct.getProductName());
        assertEquals(20, storedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product product = new Product();
        product.setProductId("test-product-id-not-found");
        product.setProductName("test-product-name-not-found");
        product.setProductQuantity(10);

        Product result = productRepository.edit(product);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("test-product-id-delete");
        product.setProductName("test-product-name-delete");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete("test-product-id-delete");
        Product result = productRepository.findById("test-product-id-delete");
        assertNull(result);
    }

    @Test
    void testDeleteProductNotFound() {
        productRepository.delete("test-product-id-not-found");
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
}

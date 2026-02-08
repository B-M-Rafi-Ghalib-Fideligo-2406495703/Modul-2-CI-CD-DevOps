package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment =  RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d",testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");

        WebElement createProductButton = driver.findElement(By.xpath("//*[text()='Create Product']"));
        createProductButton.click();

        WebElement productNameField = driver.findElement(By.name("productName"));
        productNameField.clear();
        String productName = "Produk 1";
        productNameField.sendKeys(productName);

        WebElement productQuantityField = driver.findElement(By.name("productQuantity"));
        productQuantityField.clear();
        String productQuantity = "100";
        productQuantityField.sendKeys(productQuantity);

        WebElement submitButton = driver.findElement(By.xpath("//*[text()='Submit']"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(baseUrl + "/product/list", currentUrl);

        WebElement productTable = driver.findElement(By.className("table"));
        String tableContent = productTable.getText();
        
        if (!tableContent.contains(productName)) {
             throw new Exception("Product Name not found in the list");
        }
        if (!tableContent.contains(productQuantity)) {
             throw new Exception("Product Quantity not found in the list");
        }

        WebElement nameCell = driver.findElement(By.xpath("//td[contains(text(), '" + productName + "')]"));
        WebElement quantityCell = driver.findElement(By.xpath("//td[contains(text(), '" + productQuantity + "')]"));
        
        assertEquals(productName, nameCell.getText());
        assertEquals(productQuantity, quantityCell.getText());
    }
}

package chaldalpayment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

public class ChaldalPaymentTest {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open the Chaldal homepage
            driver.get("https://chaldal.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 1: Search for a product
            WebElement searchBar = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".landingPage2 .landingBanner .floatingSearchBar .searchArea input")
            ));
            searchBar.clear();
            searchBar.sendKeys("oil");
            searchBar.submit();

            // Wait for product listing page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-container")));

            // Step 2: Select a product from the results and add to cart
            WebElement addToCartButton = null;
            for (int i = 0; i < 3; i++) {  // Retry up to 3 times
                try {
                    addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class, 'product')][1]//button[contains(text(), 'Add')]")
                    ));
                    addToCartButton.click();
                    break;  // Exit the loop if successful
                } catch (StaleElementReferenceException e) {
                    System.out.println("Caught StaleElementReferenceException, retrying...");
                }
            }

            // Step 3: Proceed to the checkout page
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".cart-button a")
            ));
            checkoutButton.click();

            // Wait for the checkout page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".checkout-container")));

            // Step 4: Fill out payment details - For example, select Cash on Delivery
            WebElement paymentMethod = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(), 'Cash on Delivery')]")
            ));
            paymentMethod.click();

            // Step 5: Confirm the order
            WebElement confirmOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".confirm-order-button")
            ));
            confirmOrderButton.click();

            // Wait for the order confirmation page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".order-confirmation")));

            // Step 6: Verify the order confirmation
            WebElement confirmationMessage = driver.findElement(By.cssSelector(".order-confirmation .message"));
            if (confirmationMessage.getText().contains("Your order has been placed")) {
                System.out.println("Payment Test Passed: Order successfully placed!");
            } else {
                System.out.println("Payment Test Failed: Order was not confirmed.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit(); 
        }
    }
}

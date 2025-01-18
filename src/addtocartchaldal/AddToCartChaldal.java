package addtocartchaldal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

public class AddToCartChaldal {

    private static int retryCount = 0;
    private static final int MAX_RETRIES = 3;

    public static void main(String[] args) {
        if (retryCount >= MAX_RETRIES) {
            System.out.println("Max retry attempts reached. Exiting.");
            return;
        }
        retryCount++;

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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased wait time

            // Locate the search bar using the updated CSS selector
            WebElement searchBar = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".landingPage2 .landingBanner .floatingSearchBar .searchArea input")
            ));

            // Send search query and submit
            searchBar.clear();  // Clear any existing text before entering the search term
            searchBar.sendKeys("oil");
            searchBar.submit();

            // Wait for the product listing page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-container")));

            // Locate the first product's "Add to Cart" button again (re-locate element)
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'product')][1]//button[contains(text(), 'Add')]")
            ));
            addToCartButton.click();

            // Verify that the product is added to the cart
            WebElement cartCount = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".cart-counter")
            ));
            String count = cartCount.getText();

            if (!count.isEmpty() && Integer.parseInt(count) > 0) {
                System.out.println("Product successfully added to cart. Cart count: " + count);
            } else {
                System.out.println("Failed to add product to cart.");
            }

        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element reference error. Retrying...");
            // Retry by re-locating elements after a stale element error
            main(args); // Recursive retry with retry limit
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}

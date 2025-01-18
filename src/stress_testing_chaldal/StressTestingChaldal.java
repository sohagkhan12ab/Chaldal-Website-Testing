package stress_testing_chaldal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StressTestingChaldal {

    private static final String CHROME_DRIVER_PATH = "C:\\webdrivers\\chromedriver.exe";

    public static void main(String[] args) {
        StressTestingChaldal test = new StressTestingChaldal();
        test.performStressTesting();
    }

    public void performStressTesting() {
        int numUsers = 5; // Number of users to simulate
        List<WebDriver> drivers = new ArrayList<>();

        // Set the ChromeDriver path
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        // Initialize WebDrivers for each simulated user
        try {
            for (int i = 0; i < numUsers; i++) {
                WebDriver driver = new ChromeDriver();
                drivers.add(driver);
                driver.get("https://chaldal.com/");
            }

            // Simulate user interactions
            for (WebDriver driver : drivers) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    // Search for a product
                    String searchQuery = "Rice";

                    // Locate search bar and perform search
                    WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.search-bar")));
                    searchBar.click();
                    searchBar.sendKeys(searchQuery);
                    searchBar.submit();

                    // Wait for results
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-container")));
                    System.out.println("Search completed successfully for: " + searchQuery);

                } catch (Exception e) {
                    System.err.println("Error during interaction: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing WebDrivers: " + e.getMessage());
        } finally {
            // Close all browsers
            for (WebDriver driver : drivers) {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }
}

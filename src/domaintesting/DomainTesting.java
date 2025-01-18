package domaintesting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DomainTesting {

    public static void main(String[] args) throws InterruptedException {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");

        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Chaldal login page
            driver.get("https://chaldal.com/login");

            // Maximize the browser window
            driver.manage().window().maximize();

            // Wait for 10 seconds (10,000 milliseconds) to observe the opened page
            Thread.sleep(10000);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}

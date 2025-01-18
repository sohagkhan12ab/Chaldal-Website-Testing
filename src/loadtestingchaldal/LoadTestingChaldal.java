package loadtestingchaldal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoadTestingChaldal {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Start Chrome maximized

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        // List of URLs to test
        String[] urls = {
            "https://chaldal.com/",
            "https://chaldal.com/fruits",
            "https://chaldal.com/search/bread"
        };

        // Loop through each URL and measure page load time
        for (String url : urls) {
            try {
                long startTime = System.currentTimeMillis();
                driver.get(url);
                long loadTime = System.currentTimeMillis() - startTime;
                System.out.println("Page load time for " + url + ": " + loadTime + " milliseconds");
            } catch (Exception e) {
                System.out.println("Error loading URL: " + url + " - " + e.getMessage());
            }
        }

        // Close the browser
        driver.quit();
    }
}

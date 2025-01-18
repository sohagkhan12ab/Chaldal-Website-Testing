package chaldallogintest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Chaldallogintest {

    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Chaldal login page
            driver.get("https://chaldal.com/");
            driver.manage().window().maximize();

            // Initialize explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Locate the email input field
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@placeholder='Email Address']")
            ));
            emailField.sendKeys("rimankhan55@gmail.com");

            // Locate the password input field
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@placeholder='Password']")
            ));
            passwordField.sendKeys("Rimankhan55@");

            // Locate and click the login button
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Login')]")
            ));
            loginButton.click();

            // Wait for the dashboard page to load
            boolean isLoggedIn = wait.until(ExpectedConditions.urlContains("dashboard"));

            // Verify login success
            if (isLoggedIn) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) {
            // Print error details
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}

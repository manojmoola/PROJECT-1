package Project1;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationFunctionality{

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        validRegistration(driver);
        existingEmailRegistration(driver);
        blankFieldsRegistration(driver);
        passwordMismatch(driver);
        invalidEmailFormat(driver);

        driver.quit();
    }

    public static void validRegistration(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/register");
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        String uniqueEmail = "test" + System.currentTimeMillis() + "@mail.com";
        driver.findElement(By.id("Email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("Password")).sendKeys("Test@123");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@123");
        driver.findElement(By.id("register-button")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".result")));
        boolean success = driver.getPageSource().contains("Your registration completed");
        System.out.println(success ? "Valid Registration Passed" : "Valid Registration Failed");
        driver.findElement(By.linkText("Log out")).click();
    }

    public static void existingEmailRegistration(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/register");
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys("manojmula878@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Test@123");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@123");
        driver.findElement(By.id("register-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".validation-summary-errors")));
            System.out.println(error.getText().contains("exists") ? "Existing Email Test Passed" : "Existing Email Test Failed");
        } catch (TimeoutException e) {
            System.out.println("Existing Email Test Failed: Error message not displayed");
        }
    }

    public static void blankFieldsRegistration(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/register");
        driver.findElement(By.id("register-button")).click();
        String validation = driver.findElement(By.id("FirstName")).getAttribute("validationMessage");
        System.out.println(validation.length() > 0 ? "Blank Field Validation Shown" : "Blank Field Validation Missing");
    }

    public static void passwordMismatch(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/register");
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys("test" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(By.id("Password")).sendKeys("Test@123");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Wrong@123");
        driver.findElement(By.id("register-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement mismatch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".field-validation-error")));
            System.out.println(mismatch.getText().contains("do not match") ? "Password Mismatch Test Passed" : "Password Mismatch Test Failed");
        } catch (TimeoutException e) {
            System.out.println("Password Mismatch Test Failed: Error not visible");
        }
    }

    public static void invalidEmailFormat(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/register");
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys("name@name");
        driver.findElement(By.id("Password")).sendKeys("Test@123");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@123");
        driver.findElement(By.id("register-button")).click();
        String validation = driver.findElement(By.id("Email")).getAttribute("validationMessage");
        System.out.println(validation.length() > 0 ? "Invalid Email Format Validation Shown" : "Invalid Email Format Validation Missing");
    }
}

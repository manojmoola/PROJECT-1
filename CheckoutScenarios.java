package Project1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CheckoutScenarios {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        checkoutWithRegisteredUser(driver);
        checkoutWithoutLogin(driver);
        
        driver.quit();
    }

    public static void checkoutWithRegisteredUser(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/login");
        driver.findElement(By.id("Email")).sendKeys("yourregisteredemail@example.com");
        driver.findElement(By.id("Password")).sendKeys("yourpassword");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();

        driver.navigate().to("http://demowebshop.tricentis.com/books");
        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
        driver.findElement(By.cssSelector("span.cart-qty")).click();

        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout"));

        boolean reachedCheckout = driver.getCurrentUrl().contains("checkout");
        System.out.println(reachedCheckout ? "Checkout with Registered User Passed" : "Checkout with Registered User Failed");
    }

    public static void checkoutWithoutLogin(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/books");
        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
        driver.findElement(By.cssSelector("span.cart-qty")).click();

        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        boolean loginPrompt = driver.getPageSource().contains("Welcome, Please Sign In!");
        System.out.println(loginPrompt ? "Checkout without Login Passed" : "Checkout without Login Failed");
    }

}

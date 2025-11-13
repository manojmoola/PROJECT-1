package Project1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginFunctionality {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver(); // No need to set path
        driver.manage().window().maximize();

        validLogin(driver);
        invalidPassword(driver);
        emptyFields(driver);
        invalidEmailFormat(driver);
        passwordMasking(driver);

        driver.quit();
    }

    public static void validLogin(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/login");
        driver.findElement(By.id("Email")).sendKeys("mulamanoj25@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Mula@878");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        Thread.sleep(2000);
        boolean loggedIn;
        try {
            loggedIn = driver.findElement(By.linkText("Log out")).isDisplayed();
        } catch (Exception e) {
            loggedIn = false;
        }
        System.out.println(loggedIn ? "Valid Login Passed" : "Valid Login Failed");
        if (loggedIn) driver.findElement(By.linkText("Log out")).click();
    }

    public static void invalidPassword(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/login");
        driver.findElement(By.id("Email")).sendKeys("mulamanoj25@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        Thread.sleep(1000);
        String error = driver.findElement(By.cssSelector(".validation-summary-errors")).getText();
        System.out.println(error.contains("unsuccessful") ? "Invalid Password Test Passed" : "Invalid Password Test Failed");
    }

    public static void emptyFields(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/login");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        String validation = driver.findElement(By.id("Email")).getAttribute("validationMessage");
        System.out.println(validation.length() > 0 ? "Empty Field Validation Shown" : "Empty Field Validation Missing");
    }

    public static void invalidEmailFormat(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/login");
        driver.findElement(By.id("Email")).sendKeys("user.com");
        driver.findElement(By.id("Password")).sendKeys("test123");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        String validation = driver.findElement(By.id("Email")).getAttribute("validationMessage");
        System.out.println(validation.length() > 0 ? "Invalid Email Format Validation Shown" : "Invalid Email Format Validation Missing");
    }

    public static void passwordMasking(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/login");
        String type = driver.findElement(By.id("Password")).getAttribute("type");
        System.out.println(type.equals("password") ? "Password Field Masking Works" : "Password Field Masking Failed");
    }
}

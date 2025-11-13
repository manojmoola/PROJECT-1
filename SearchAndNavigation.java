package Project1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class SearchAndNavigation {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        validSearch(driver);
        invalidSearch(driver);
        navigateViaMenu(driver);

        driver.quit();
    }

    public static void validSearch(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.id("small-searchterms")).sendKeys("computer");
        driver.findElement(By.cssSelector("input[value='Search']")).click();

        boolean result = driver.getPageSource().toLowerCase().contains("computer");
        System.out.println(result ? "Valid Search Passed" : "Valid Search Failed");
    }

    public static void invalidSearch(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.id("small-searchterms")).sendKeys("asdfgh");
        driver.findElement(By.cssSelector("input[value='Search']")).click();

        boolean noResult = driver.getPageSource().contains("No products were found");
        System.out.println(noResult ? "Invalid Search Passed" : "Invalid Search Failed");
    }

    public static void navigateViaMenu(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.linkText("Books")).click();

        boolean booksDisplayed = driver.getPageSource().contains("Books");
        System.out.println(booksDisplayed ? "Navigate via Menu Passed" : "Navigate via Menu Failed");
    }
}

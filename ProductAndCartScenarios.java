package Project1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductAndCartScenarios {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        addSingleProductToCart(driver);
        addMultipleProducts(driver);
        updateProductQuantity(driver);
        removeProductFromCart(driver);
        

        driver.quit();
    }

    public static void addSingleProductToCart(WebDriver driver) {
        driver.get("http://demowebshop.tricentis.com/books");
        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
        driver.findElement(By.cssSelector("span.cart-qty")).click();
        boolean added = driver.getPageSource().contains("Shopping cart");
        System.out.println(added ? "Add Single Product Passed" : "Add Single Product Failed");
    }

    public static void addMultipleProducts(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/computers");
        driver.findElement(By.linkText("Desktops")).click();
        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
        driver.navigate().to("http://demowebshop.tricentis.com/apparel-shoes");
        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
        driver.findElement(By.cssSelector("span.cart-qty")).click();
        boolean multiple = driver.findElements(By.cssSelector(".cart-item-row")).size() >= 2;
        System.out.println(multiple ? "Add Multiple Products Passed" : "Add Multiple Products Failed");
    }

    public static void updateProductQuantity(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/cart");
        if (driver.findElements(By.cssSelector("input.qty-input")).isEmpty()) {
            driver.navigate().to("http://demowebshop.tricentis.com/books");
            driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
            driver.navigate().to("http://demowebshop.tricentis.com/cart");
        }
        WebElement qtyBox = driver.findElement(By.cssSelector("input.qty-input"));
        qtyBox.clear();
        qtyBox.sendKeys("3");
        driver.findElement(By.name("updatecart")).click();
        Thread.sleep(1500);
        String qty = driver.findElement(By.cssSelector("input.qty-input")).getAttribute("value");
        System.out.println(qty.equals("3") ? "Update Quantity Passed" : "Update Quantity Failed");
    }

    public static void removeProductFromCart(WebDriver driver) throws InterruptedException {
        driver.get("http://demowebshop.tricentis.com/cart");
        if (driver.findElements(By.name("removefromcart")).isEmpty()) {
            driver.navigate().to("http://demowebshop.tricentis.com/books");
            driver.findElement(By.cssSelector("input[value='Add to cart']")).click();
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
            driver.navigate().to("http://demowebshop.tricentis.com/cart");
        }
        driver.findElement(By.name("removefromcart")).click();
        driver.findElement(By.name("updatecart")).click();
        Thread.sleep(1500);
        boolean empty = driver.getPageSource().contains("Your Shopping Cart is empty!");
        System.out.println(empty ? "Remove Product Passed" : "Remove Product Failed");
    }

   
}

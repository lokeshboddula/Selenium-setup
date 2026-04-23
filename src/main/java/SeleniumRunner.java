import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumRunner {
    public static void main(String[] args) {
        // 1️⃣ Setup ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // 2️⃣ Open webpage
            // 2️⃣ Open Selenium practice web form
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 3️⃣ Fill text input
            driver.findElement(By.name("my-text")).sendKeys("Lokesh Test");

            // 4️⃣ Fill textarea
            driver.findElement(By.name("my-textarea")).sendKeys("This is a sample shit text.");

            // 🔟 Fill password input
            driver.findElement(By.name("my-password")).sendKeys("Password123!");


            // 5️⃣ Check checkboxes
            driver.findElement(By.id("my-check-1")).click();
            driver.findElement(By.id("my-check-2")).click();

            // 6️⃣ Select a radio button
            driver.findElement(By.id("my-radio-1")).click();

            // 7️⃣ Select option from dropdown
            Select dropdown = new Select(driver.findElement(By.name("my-select")));
            dropdown.selectByVisibleText("Two");

            // 1. Datalist dropdown: Type to search & select a value

            // ============================================================================

            // 1. Locate the input element with datalist
            WebElement input = driver.findElement(By.name("my-datalist"));

            // 2. Get the id of the linked datalist from input's 'list' attribute
            String datalistId = input.getAttribute("list");

            // 3. Find the <datalist> element by id
            WebElement datalist = driver.findElement(By.id(datalistId));

            // 4. Get all <option> elements inside datalist
            List<WebElement> options = datalist.findElements(By.tagName("option"));

            String firstValue = options.get(0).getAttribute("value");

            // set value in input using JS
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", input, firstValue);

            // ============================================================================

            WebElement colorPicker = driver.findElement(By.name("my-colors"));
            colorPicker.sendKeys("#ff0000"); // Red


            WebElement dateInput = driver.findElement(By.name("my-date"));
            dateInput.sendKeys("2026-04-15");




            WebElement fileInput = driver.findElement(By.name("my-file"));
            fileInput.sendKeys("/Users/furlenco/Documents/Untitled.csv"); // Change path accordingly


            // 8️⃣ Fill URL input
//            driver.findElement(By.name("my-url")).sendKeys("https://www.google.com");
//
//            // 9️⃣ Fill email input
//            driver.findElement(By.name("my-email")).sendKeys("lokesh@test.com");


            // 11️⃣ Fill number input
//            driver.findElement(By.name("my-number")).sendKeys("42");

            // 12️⃣ Click submit button
            //driver.findElement(By.cssSelector("button")).click();

            // 13️⃣ Wait for response message and print it
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            System.out.println("Form submission message: " + message.getText());

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // 6️⃣ Close browser
            //driver.quit();
        }
    }
}
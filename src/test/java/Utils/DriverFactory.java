package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    public static WebDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();

        // 👇 IMPORTANT for CI + stability
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        // 👇 Required for GitHub Actions / Linux runners
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        return new ChromeDriver(options);
    }
}
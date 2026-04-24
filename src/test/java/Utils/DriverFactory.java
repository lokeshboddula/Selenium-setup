package Utils;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

@Getter
public class DriverFactory {
    private WebDriver driver;

    public DriverFactory() {
        WebDriverManager.chromedriver().setup();
    }

    public void initializeDriver() {
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

        this.driver = new ChromeDriver(options);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
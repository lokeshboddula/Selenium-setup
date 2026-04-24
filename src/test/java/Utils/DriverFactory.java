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
        // Setup ChromeDriver - skip if running in Docker with system-installed Chrome
        if (System.getenv("CI") == null && !isDockerEnvironment()) {
            WebDriverManager.chromedriver().setup();
        }
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
        } else if (isDockerEnvironment()) {
            // 👇 Docker environment specific options
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.setBinary("/usr/bin/chromium");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        this.driver = new ChromeDriver(options);
    }

    private boolean isDockerEnvironment() {
        // Check if running in Docker
        try {
            return new java.io.File("/.dockerenv").exists() || 
                   System.getenv("DOCKER_CONTAINER") != null ||
                   System.getenv("HOSTNAME") != null && System.getenv("HOSTNAME").matches("[a-f0-9]{12}");
        } catch (Exception e) {
            return false;
        }
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
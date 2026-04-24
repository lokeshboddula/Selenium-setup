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
        // Skip WebDriverManager setup in CI/Docker environments that have Chrome pre-installed
        if (isCI() || isDockerEnvironment()) {
            System.out.println("CI/Docker environment detected - skipping WebDriverManager setup");
        } else {
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
            options.addArguments("--disable-gpu");
        } else if (isDockerEnvironment()) {
            // 👇 Docker environment specific options
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.setBinary("/usr/bin/chromium");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        } else if (isJenkinsEnvironment()) {
            // 👇 Jenkins environment options
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        this.driver = new ChromeDriver(options);
    }

    private boolean isCI() {
        return System.getenv("CI") != null || isJenkinsEnvironment();
    }

    private boolean isJenkinsEnvironment() {
        return System.getenv("JENKINS_HOME") != null || 
               System.getenv("BUILD_NUMBER") != null ||
               System.getenv("JOB_NAME") != null;
    }

    private boolean isDockerEnvironment() {
        // Check if running in Docker
        try {
            return new java.io.File("/.dockerenv").exists() || 
                   System.getenv("DOCKER_CONTAINER") != null;
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
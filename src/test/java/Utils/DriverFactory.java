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
            
            // Try multiple possible Chromium paths (Docker packages vary)
            String chromiumPath = getChromiumPath();
            if (chromiumPath != null) {
                options.setBinary(chromiumPath);
            }
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

    private String getChromiumPath() {
        // Try multiple possible Chromium installation paths
        String[] chromiumPaths = {
            "/usr/bin/chromium",
            "/usr/bin/chromium-browser",
            "/snap/bin/chromium",
            "/Applications/Chromium.app/Contents/MacOS/Chromium" // macOS
        };
        
        for (String path : chromiumPaths) {
            if (new java.io.File(path).exists()) {
                System.out.println("Found Chromium at: " + path);
                return path;
            }
        }
        System.out.println("WARNING: Chromium not found at common paths. Using default.");
        return null; // Let ChromeDriver find it automatically
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
package stepDefinitions;

import Utils.DriverFactory;
import Utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class CucumberHooks {

    private WebDriver driver;
    private DriverFactory driverFactory;
    private ExtentTest extentTest;

    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting Cucumber scenario: {}", scenario.getName());

        // Initialize WebDriver
        driverFactory = new DriverFactory();
        driverFactory.initializeDriver();
        driver = driverFactory.getDriver();

        // Setup ExtentReports
        extentTest = ExtentManager.getInstance().createTest(scenario.getName());
        ExtentManager.setTest(extentTest);

        // Store driver in scenario context for step definitions
        scenario.attach("WebDriver initialized", "text/plain", "driver-setup");
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("Finishing Cucumber scenario: {}", scenario.getName());

        if (scenario.isFailed()) {
            extentTest.fail("Scenario failed: " + scenario.getName());
            log.error("Scenario failed: {}", scenario.getName());
        } else {
            extentTest.pass("Scenario passed: " + scenario.getName());
            log.info("Scenario passed: {}", scenario.getName());
        }

        // Quit WebDriver
        if (driverFactory != null) {
            driverFactory.quitDriver();
        }

        // Flush ExtentReports
        ExtentManager.getInstance().flush();
    }

    public WebDriver getDriver() {
        return driver;
    }
}

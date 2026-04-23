package base;

import Utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Utils.DriverFactory;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver = DriverFactory.createDriver();
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeSuite
    public void startReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("OS", "Linux");
        extent.setSystemInfo("Tester", "AutomationUser");
    }

    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
        ExtentManager.setTest(test);
    }

    @AfterMethod
    public void getResult(ITestResult result) {

        ExtentTest test = ExtentManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        } else {
            test.skip("Test Skipped");
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush(); // 🔴 MUST HAVE
    }
}


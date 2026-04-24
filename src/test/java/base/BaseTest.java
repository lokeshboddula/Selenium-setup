package base;

import Utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Utils.DriverFactory;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    private DriverFactory driverFactory;
    public WebDriver driver;
    private static ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driverFactory = new DriverFactory();
        driverFactory.initializeDriver();
        driver = driverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeSuite
    public void startReport() {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
    }

    @BeforeMethod
    public void startTest(Method method) {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
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
        if (driverFactory != null) {
            driverFactory.quitDriver();
        }
        extent.flush(); // 🔴 MUST HAVE
    }
}

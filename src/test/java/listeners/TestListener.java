package listeners;

import Utils.ExtentManager;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.createTest(result.getName());
        ExtentManager.getTest().info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //ExtentManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());
    }
}
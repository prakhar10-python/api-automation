package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ExtentReportManager;

public class ExtentTestListener implements ITestListener {

    private static final ExtentReports extentReports =
            ExtentReportManager.getReportInstance();

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test = extentReports.createTest(
                result.getMethod().getMethodName()
        );

        extentTest.set(test);

        extentTest.get().info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail("Test Failed");

        if (result.getThrowable() != null) {
            extentTest.get().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        extentReports.flush();
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
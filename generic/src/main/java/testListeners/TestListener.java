package testListeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reportFactory.ReportFactory;
//import reportFactory.ReportFactory.java;
import java.io.IOException;


public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub

    }

    /* Function Decription - This function will Listen to Test Skipping call and will be executed after
     * 						 any testcase is skipped.
     * */

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            ReportFactory.getInstance().newTest(result.getName(), result);
            ReportFactory.getInstance().info("Testcase get skipped");
            ReportFactory.getInstance().skipped(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub

    }

}
package reportFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import utilities.PropertyUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

public class ReportFactory {

    private static ReportFactory instance = null;

    public static Properties reportPropertyMap;
    public static final Logger LOGGER = Logger.getLogger(String.valueOf(ReportFactory.class));
    public static String reportFolder;
    public static String archiveFolder;

    private ReportFactory() {

    }

    public static ReportFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new ReportFactory();
            reportPropertyMap = PropertyUtils.readProperties("/generic/src/main/resources/extent.properties");
        }
        return instance;
    }

    private static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<ExtentReports>();

    private static ThreadLocal<ExtentHtmlReporter> extentHtmlReporter = new ThreadLocal<ExtentHtmlReporter>();

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();


    public final void generateReport(String deviceType) throws Exception {
        try {
            String reportPath = reportFolder + "/" + deviceType + "_report.html";
            extentHtmlReporter.set(new ExtentHtmlReporter(reportPath));
            extentHtmlReporter.get().config().setChartVisibilityOnOpen(true);
            extentHtmlReporter.get().config().setDocumentTitle(reportPropertyMap.getProperty("htmlReportTitle"));
            extentHtmlReporter.get().config().setReportName(reportPropertyMap.getProperty("htmlReportTitle"));
            extentHtmlReporter.get().config().setTestViewChartLocation(ChartLocation.TOP);
            extentHtmlReporter.get().config().setTheme(Theme.STANDARD);
            extentHtmlReporter.get().config().setTimeStampFormat(reportPropertyMap.getProperty("TimeStampFormat"));
            extentReport.set(new ExtentReports());
            extentReport.get().attachReporter(extentHtmlReporter.get());
            LOGGER.info("HTML report created : " + reportPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public final void info(String msg) {
        extentTest.get().log(Status.INFO, msg);
        LOGGER.info(msg);
    }

    public final void error(String msg) {
        extentTest.get().log(Status.ERROR, msg);
        LOGGER.error(msg);

    }

    public final void pass(ITestResult result) {
        extentTest.get().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
        LOGGER.info(result.getName() + " PASSED ");
    }

    public final void fail(ITestResult result) {
        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
        extentTest.get().fail(result.getThrowable());
        LOGGER.error(result.getName() + " FAILED ");
        StringWriter sw = new StringWriter();
        result.getThrowable().printStackTrace(new PrintWriter(sw));
        LOGGER.error(sw.toString());
        sw = null;
    }

    public final void skipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
        extentTest.get().skip(result.getThrowable());
        LOGGER.error(result.getName() + " SKIPPED ");
    }

    public final void printReport(String browser) {
        try {
            extentReport.get().flush();
            LOGGER.info("HTML report saved for browser : " + browser + " at " + reportFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void newTest(String method, ITestResult result) throws IOException {
        extentTest.set(extentReport.get().createTest(method));
        LOGGER.info("New testcase :" + method);
        ReportFactory.getInstance().info("Testcase Description : " + result.getMethod().getDescription());
    }

}


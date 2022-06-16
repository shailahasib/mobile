package devicefactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import static devicefactory.AppiumServerSuite.getServiceUrl;
import static devicefactory.AppiumServerSuite.service;
import static utilities.ConfigData.*;


public class AndroidBase extends DriverThreadManager {

    @BeforeMethod
    @Parameters({"executionMode"})
    public void androidCapabilities(String executionMode) throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        if (executionMode.equalsIgnoreCase("real")) {
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
            cap.setCapability(MobileCapabilityType.APP, getFilePath(getApkFileName()));
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, getAndroidAutomationName());
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, getEmulatorDeviceName());
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, getAndroidAutomationName());

        } else if (executionMode.equalsIgnoreCase("emulator")) {
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, getEmulatorDeviceName());
            cap.setCapability(MobileCapabilityType.APP, getFilePath(getApkFileName()));
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, getAndroidAutomationName());
        }
        setDriver(new AndroidDriver(new URL(getServiceUrl().toString()), cap));
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".jpg";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src,destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    @AfterMethod
    public void teardown() {
        getDriver().quit();
        service.stop();
    }



}

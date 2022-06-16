package devicefactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import static devicefactory.AppiumServerSuite.getServiceUrl;
import static devicefactory.AppiumServerSuite.service;
import static utilities.ConfigData.*;
import java.net.MalformedURLException;
import java.net.URL;


public class IOSBase extends DriverThreadManager {

    @BeforeMethod
    @Parameters({"executionMode"})
    public void iOSCapabilities(String executionMode) throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        if (executionMode.equalsIgnoreCase("simulator")) {
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, getSimulatorName());
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getiOSPlatformVersion());
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

        } else if  (executionMode.equalsIgnoreCase("real")) {
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, getIOSDeviceName());
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getiOSPlatformVersion());
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            cap.setCapability("xcodeOrgId", "");
            cap.setCapability("udid", "");
            cap.setCapability("xcodeSigningId", "iPhone Developer");
            cap.setCapability("updateWDABundleId", "");
        }
        cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 50000);
        cap.setCapability("commandTimeouts", "12000");
        cap.setCapability(MobileCapabilityType.APP, getFilePath(getAppFileName()));
        setDriver(new IOSDriver(new URL(getServiceUrl().toString()), cap));

    }

    @AfterMethod
    public void teardown() {
        getDriver().quit();
        service.stop();
    }

}

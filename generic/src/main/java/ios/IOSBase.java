package ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class IOSBase {

    public static IOSDriver driver;

    public static IOSDriver iOSCap(String device) throws MalformedURLException {

        File appDir = new File("src");
        File appFile = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        cap.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 50000);
        cap.setCapability("commandTimeouts", "12000");
        cap.setCapability(MobileCapabilityType.APP, "");
        if (device.equalsIgnoreCase("real")) {
            cap.setCapability("xcodeOrgId", "");
            cap.setCapability("udid","");
            cap.setCapability("xcodeSigningId", "iPhone Developer");
            cap.setCapability("updateWDABundleId", "");
        }
        System.out.println("h");
        driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

        return driver;
    }
}

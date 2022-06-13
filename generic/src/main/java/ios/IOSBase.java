package ios;


import java.net.URL;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;

import io.appium.java_client.AppiumDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;


public class IOSBase {

    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public void setDriver(AppiumDriver driver) {
        this.driver.set(driver);
    }

    public AppiumDriver getDriver() {
        return this.driver.get();
    }


    @BeforeMethod(alwaysRun=true)
    @Parameters(value={"deviceIndex"})
    public void setUp(String deviceIndex) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/com/browserstack/run_parallel_test/parallel.conf.json"));
        JSONArray envs = (JSONArray) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(Integer.parseInt(deviceIndex));
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(capabilities.getCapability(pair.getKey().toString()) == null){
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if(username == null) {
            username = (String) config.get("username");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if(accessKey == null) {
            accessKey = (String) config.get("access_key");
        }

        String app = System.getenv("BROWSERSTACK_APP_ID");
        if(app != null && !app.isEmpty()) {
            capabilities.setCapability("app", app);
        }

        setDriver(new IOSDriver<IOSElement>(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities));
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        // Invoke driver.quit() to indicate that the test is completed.
        // Otherwise, it will appear as timed out on BrowserStack.
        getDriver().quit();
    }

    public void localiOSCapabilities(String device) throws MalformedURLException {

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

        setDriver(new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap));

    }
}

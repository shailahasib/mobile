package android;

import java.net.URL;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utilities.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class AndroidBase {

    public AndroidDriver<AndroidElement> androidDriver(Map<String,Object> deviceInfoMap) throws IOException {
        //For android real device - DeviceName should be "Android"
        File appDir = new File("src");
        File appFile = new File(appDir, (String)deviceInfoMap.get("application"));//"ApiDemos-debug.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfoMap.get("deviceName"));
        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
//        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME,deviceInfoMap.get("platformName"));
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,deviceInfoMap.get("platformVersion"));
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,deviceInfoMap.get("automationName"));
        cap.setCapability(MobileCapabilityType.AUTO_WEBVIEW,deviceInfoMap.get("autoWebView"));
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,deviceInfoMap.get("appActivity"));
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,deviceInfoMap.get("appPackage"));
        cap.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT,deviceInfoMap.get("avdLaunchTimeout"));
        cap.setCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT,deviceInfoMap.get("adbExecTimeout"));
        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

        return driver;
    }

//    public static AppiumDriverLocalService service;
//
//    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
//
//    public void setDriver(AppiumDriver driver) {
//        this.driver.set(driver);
//    }
//
//    public AppiumDriver getDriver() {
//        return this.driver.get();
//    }


//    public AppiumDriverLocalService startServer(String portNumber) {
//        boolean flag = checkIfServerIsRunning(Integer.parseInt(portNumber));
//        if (!flag) {
//            AppiumServiceBuilder builder = new AppiumServiceBuilder();
//            builder.withIPAddress("127.0.0.1").usingPort(Integer.parseInt(portNumber));
//            service = AppiumDriverLocalService.buildService(builder);
//            service.start();
//        }
//        return service;
//    }
//
//    public static boolean checkIfServerIsRunning(int port) {
//        boolean isServerRunning = false;
//        ServerSocket serverSocket;
//        try {
//            serverSocket = new ServerSocket(port);
//            serverSocket.close();
//        } catch (IOException e) {
//            //If control comes here, then it means that the port is in use
//            isServerRunning = true;
//        } finally {
//            serverSocket = null;
//        }
//        return isServerRunning;
//    }
//
//    @Parameters({"useCloudEnv", "deviceName", "platformVersion", "portNumber"})
//    public void launchTest(@Optional("false")boolean realDevice, boolean useCloudEnv, String deviceName,
//                           String platformVersion, String portNumber) throws Exception {
//        if (useCloudEnv == true) {
//            setUpCloud(deviceName);
//        } else if (realDevice == true) {
//            startServer("");
//            localAndroidCapabilities(useCloudEnv, deviceName, platformVersion, portNumber);
//        } else
//            //emulatorCapabilities(deviceName,portNumber);
//            getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//
//    @Parameters({"useCloudEnv", "deviceName", "platformVersion", "portNumber"})
//    public void localAndroidCapabilities(boolean useCloudEnv, String deviceName, String platformVersion, String portNumber) throws IOException {
//        //For android real device - DeviceName should be "Android"
//        Properties androidProp = PropertyUtils.readProperties("/src/resources/android.properties");
//        File appDir = new File("src");
//        File appFile = new File(appDir, (String) androidProp.get("application"));//"ApiDemos-debug.apk");
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
//        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
//        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
//        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
//        System.out.println("h");
//        setDriver(new AndroidDriver(new URL("http://127.0.0.1:" + portNumber + "/wd/hub"), cap));
//    }
//
//    public void emulatorCapabilities(String deviceName, String portNumber) throws IOException {
//        Properties androidProp = PropertyUtils.readProperties("/src/resources/android.properties");
//        File appDir = new File("/Users/shailahasib/IdeaProjects/mobile-automation/generic/src");
//        File appFile = new File(appDir, "ApiDemos-debug.apk");
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
//        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
//        cap.setCapability("automationName", "UIAutomator2");
//        setDriver(new AndroidDriver(new URL("http://127.0.0.1:" + portNumber + "/wd/hub"), cap));
//    }
//
//    @BeforeMethod
//    @Parameters(value = {"deviceIndex"})
//    public void setUpCloud(String deviceIndex) throws Exception {
//        JSONParser parser = new JSONParser();
//        JSONObject config = (JSONObject) parser.parse(new FileReader("/Users/shailahasib/IdeaProjects/mobile-automation/generic/src/main/resources/browserstack/android/parallel.conf.json"));
//        JSONArray envs = (JSONArray) config.get("environments");
//
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//
//        Map<String, String> envCapabilities = (Map<String, String>) envs.get(Integer.parseInt(deviceIndex));
//        Iterator it = envCapabilities.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
//        }
//
//        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
//        it = commonCapabilities.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            if (capabilities.getCapability(pair.getKey().toString()) == null) {
//                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
//            }
//        }
//
//        String username = System.getenv("BROWSERSTACK_USERNAME");
//        if (username == null) {
//            username = (String) config.get("username");
//        }
//
//        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
//        if (accessKey == null) {
//            accessKey = (String) config.get("access_key");
//        }
//
//        String app = System.getenv("BROWSERSTACK_APP_ID");
//        if (app != null && !app.isEmpty()) {
//            capabilities.setCapability("app", app);
//        }
//
//        setDriver(new AndroidDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities));
//    }
//
//
//    @AfterMethod
//    public void teardown() {
//        getDriver().quit();
//    }
//
//
//    public void stopServer() {
//        service.stop();
//    }


    //
//    public void cloudEnv(String deviceName, String platformVersion, String name) throws MalformedURLException {
//        DesiredCapabilities caps = new DesiredCapabilities();
//
//        // Set your access credentials
//        caps.setCapability("browserstack.user", BROWSERSTACK_USERNAME);
//        caps.setCapability("browserstack.key", BROWSERSTACK_ACCESS_KEY);
//        // Set URL of the application under test
//        caps.setCapability("app", "bs://0f985af066522718ed743296c213a02f34b6268e");
//        // Specify device and os_version for testing
//        caps.setCapability("device", deviceName);
//        caps.setCapability("os_version", platformVersion);
//        // Set other BrowserStack capabilities
//        caps.setCapability("project", "First Java Project");
//        caps.setCapability("build", "browserstack-build-1");
//        caps.setCapability("name", name);
//        // Set these capabilities in your TestNG config file first.conf.json
//
//            caps.setCapability("browserstack.debug", true);  // for enabling visual logs
//                caps.setCapability("browserstack.networkLogs", true);  // to enable network logs to be logged
//
//
//
//        // Initialise the remote Webdriver using BrowserStack remote URL
//        // and desired capabilities defined above
//        setDriver(new AndroidDriver(new URL("http://hub.browserstack.com/wd/hub"), caps));


}

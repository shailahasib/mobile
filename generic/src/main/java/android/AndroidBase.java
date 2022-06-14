package android;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import devicefactory.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utilities.PropertyUtils;
import static devicefactory.DeviceFactory.service;


public class AndroidBase extends DriverManager {


    String BROWSERSTACK_USERNAME;
    String BROWSERSTACK_ACCESS_KEY;
//    public static AndroidDriver<AndroidElement> driver;


    @BeforeTest
    public AppiumDriverLocalService startServer() {
        boolean flag = checkIfServerIsRunning(4723);
        if (!flag) {
            service = AppiumDriverLocalService.buildDefaultService();
            service.start();
        }
        return service;
    }

    public static boolean checkIfServerIsRunning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

//    @Parameters({"useBrowser"})
//    @BeforeMethod
//    public void launchDriver(@Optional("false") boolean useBrowser) throws IOException {
//        androidDriver(useBrowser);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//
//
//    public static AndroidDriver<AndroidElement> androidDriver(boolean useBrowser) throws IOException {
//        //For android real device - DeviceName should be "Android"
//        Properties androidProp= PropertyUtils.readProperties("/src/resources/android.properties");
//        File appDir = new File("src");
//        File appFile = new File(appDir, (String)androidProp.get("application"));//"ApiDemos-debug.apk");
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability(MobileCapabilityType.DEVICE_NAME, androidProp.get("DEVICE_NAME"));
//        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
//        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
//        if(useBrowser==true){
//            cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
//        }
//        System.out.println("h");
//        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
//
//        return driver;
//    }
//
//    @AfterTest
//    public void stopServer() {
//        service.stop();
//    }





    @Parameters({"realDevice", "useCloudEnv", "deviceName", "platformVersion", "portNumber", "deviceIndex"})
    public void launchTest(@Optional("false") boolean realDevice, @Optional("false") boolean useCloudEnv, String deviceName,
                           String platformVersion, @Optional("4723") String portNumber, String deviceIndex) throws Exception {

        if (realDevice == true) {
            localAndroidCapabilities(deviceName, portNumber);
        } else
            emulatorCapabilities(deviceName);
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    public void localAndroidCapabilities(String deviceName, String portNumber) throws IOException {
        //For android real device - DeviceName should be "Android"
        Properties androidProp = PropertyUtils.readProperties("/src/resources/android.properties");
        File appDir = new File("src");
        File appFile = new File(appDir, (String) androidProp.get("application"));//"ApiDemos-debug.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        System.out.println("h");
        setDriver(new AndroidDriver(new URL("http://127.0.0.1:" + portNumber + "/wd/hub"), cap));
    }

    @BeforeMethod
    @Parameters({"deviceName"})
    public void emulatorCapabilities(String deviceName) throws IOException {
        Properties androidProp = PropertyUtils.readProperties("/src/resources/android.properties");
        File appDir = new File("/Users/shailahasib/IdeaProjects/mobile-automation/tr-otm-android/src/main/resources");
        File appFile = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
        cap.setCapability("automationName", "UIAutomator2");
        setDriver(new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap));
    }


    @AfterMethod
    public void teardown() {
        getDriver().quit();
        service.stop();
    }


    public void stopServer() {
        service.stop();
    }


    public void cloudEnv(String deviceName, String platformVersion, String name) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        // Set your access credentials
        caps.setCapability("browserstack.user", BROWSERSTACK_USERNAME);
        caps.setCapability("browserstack.key", BROWSERSTACK_ACCESS_KEY);
        // Set URL of the application under test
        caps.setCapability("app", "bs://0f985af066522718ed743296c213a02f34b6268e");
        // Specify device and os_version for testing
        caps.setCapability("device", deviceName);
        caps.setCapability("os_version", platformVersion);
        // Set other BrowserStack capabilities
        caps.setCapability("project", "First Java Project");
        caps.setCapability("build", "browserstack-build-1");
        caps.setCapability("name", name);
        // Set these capabilities in your TestNG config file first.conf.json

        caps.setCapability("browserstack.debug", true);  // for enabling visual logs
        caps.setCapability("browserstack.networkLogs", true);  // to enable network logs to be logged


        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        setDriver(new AndroidDriver(new URL("http://hub.browserstack.com/wd/hub"), caps));


    }


}

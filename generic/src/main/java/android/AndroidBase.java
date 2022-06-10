package android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static utilities.PropertyUtils.readProp;

public class AndroidBase {

    public static AndroidDriver<AndroidElement> driver;
    public static AppiumDriverLocalService service;



    @BeforeTest
    public AppiumDriverLocalService startServer() {
        boolean flag = checkIfServerIsRunnning(4723);
        if (!flag) {
            service = AppiumDriverLocalService.buildDefaultService();
            service.start();
        }
        return service;
    }

    public static boolean checkIfServerIsRunnning(int port) {

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

    @Parameters({"useBrowser"})
    @BeforeMethod
    public void launchDriver(@Optional("false") boolean useBrowser) throws IOException {
        androidDriver(useBrowser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    public static AndroidDriver<AndroidElement> androidDriver(boolean useBrowser) throws IOException {
        //For android real device - DeviceName should be "Android"
        File appDir = new File("src");
        File appFile = new File(appDir, (String) readProp().get("application"));//"ApiDemos-debug.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, readProp().get("DEVICE_NAME"));
        cap.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        if(useBrowser==true){
            cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        }
        System.out.println("h");
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

        return driver;
    }

    @AfterTest
    public void stopServer() {
        service.stop();
    }




}

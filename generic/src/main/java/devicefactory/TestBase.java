package devicefactory;

import android.AndroidBase;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.ServerSocket;

public class TestBase {

    public static AppiumDriverLocalService service;

    @Parameters({"deviceType"})
    @BeforeTest
    public void launchDriver(@Optional("false") String deviceType) throws Exception {
        DeviceFactory.getInstance().launchDevice(deviceType);
    }

    @BeforeSuite
    public AppiumDriverLocalService startServer() {
        boolean flag = checkIfServerIsRunnning(4723);
        try {
            if (!flag) {
                service = AppiumDriverLocalService.buildDefaultService();
                service.start();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
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


    @AfterSuite
    public void stopServer() {
        service.stop();
    }

}

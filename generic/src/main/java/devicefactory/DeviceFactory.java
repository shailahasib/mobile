package devicefactory;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.net.ServerSocket;

public class DeviceFactory {
    public static AppiumDriverLocalService service;
    private static DeviceFactory instance = null;

    public static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);
    private DeviceFactory() {
    }

    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }


    @BeforeSuite
    public AppiumDriverLocalService startServer() {
        boolean flag = checkIfServerIsRunning(4723);
        if (!flag) {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress("127.0.0.1").usingPort(4723);
            service = AppiumDriverLocalService.buildService(builder);
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

}

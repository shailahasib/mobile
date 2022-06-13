package devicefactory;


import android.AndroidBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.log4j.Logger;
import utilities.DeviceProperties;

import java.util.Map;

public class DeviceFactory {

    private static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);
    private static DeviceFactory instance = null;

    //public static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);
    private DeviceFactory() {
    }

    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }

    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();

    public final void launchDevice(String deviceType) throws Exception {

        Map<String ,Object> deviceInfo= DeviceProperties.getDeviceProp(deviceType);
        switch (deviceType) {
            case "android_app_emulator":
                driver.set(new AndroidBase().androidDriver(deviceInfo));
        }

        LOGGER.info("Launched Browser - "+deviceType);
    }

//    private static DeviceFactory instance = null;
//    public static AppiumDriverLocalService service;

//    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
//
//    public void setDriver(AppiumDriver driver) {
//        this.driver.set(driver);
//    }
//
//    public AppiumDriver getDriver() {
//        return this.driver.get();
//    }
//
//    //public static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);
//    private DeviceFactory() {
//    }
//
//    public static DeviceFactory getInstance() {
//        if (instance == null) {
//            instance = new DeviceFactory();
//        }
//        return instance;
//    }

}

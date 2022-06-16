package devicefactory;

import io.appium.java_client.AppiumDriver;

public class DriverThreadManager {

    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public void setDriver(AppiumDriver driver) {
        this.driver.set(driver);
    }

    public synchronized AppiumDriver getDriver() {
        return this.driver.get();
    }

}

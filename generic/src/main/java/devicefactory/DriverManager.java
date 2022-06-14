package devicefactory;

import io.appium.java_client.AppiumDriver;

public class DriverManager {

    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public void setDriver(AppiumDriver driver) {
        this.driver.set(driver);
    }

    public AppiumDriver getDriver() {
        return this.driver.get();
    }

}

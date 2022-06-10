package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Reusable {

    static AppiumDriver driver;


    public Reusable(AppiumDriver driver){
        this.driver=driver;
    }

    public static void getScreenshot(String s) throws IOException
    {
        File scrFile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"/"+s+".png"));
    }

    public void click(MobileElement element){
        element.click();
    }


}

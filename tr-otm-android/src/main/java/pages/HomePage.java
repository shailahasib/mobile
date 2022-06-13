package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


import java.io.IOException;

public class HomePage extends Preferences {
   AppiumDriver driver;

    //Reusable u =new Reusable(driver);
    public HomePage(AppiumDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Preference']")
    public WebElement preferences;

    public void clickPref(){
        //u.click(preferences);
        preferences.click();
    }

}


import android.AndroidBase;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

public class HomePageTest extends AndroidBase {

    public HomePage homePage() throws IOException {
        HomePage hp = new HomePage(getDriver());
        return hp;
    }

    @Test
    public void clickPreference() throws IOException {
        homePage().clickPref();
    }

}


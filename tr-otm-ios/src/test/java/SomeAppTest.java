import devicefactory.IOSBase;
import org.testng.annotations.Test;

import java.io.IOException;

public class SomeAppTest extends IOSBase {

    public SomeApp someApp() throws IOException {
        SomeApp sa =new SomeApp(getDriver());
        return sa;
    }

    @Test
    public void clickAlertViews() {
        try {
            someApp().alertViews.click();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

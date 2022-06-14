package android.browserstackconfig;

import devicefactory.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class BrowserStackSetup extends DriverManager {

    String BROWSERSTACK_USERNAME;
    String BROWSERSTACK_ACCESS_KEY;

    @Parameters(value = {"deviceIndex"})
    public void setUpCloud(String deviceIndex) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("/Users/shailahasib/IdeaProjects/mobile-automation/generic/src/main/resources/browserstack/android/parallel.conf.json"));
        JSONArray envs = (JSONArray) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(Integer.parseInt(deviceIndex));
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }


        BROWSERSTACK_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
        if (BROWSERSTACK_USERNAME == null) {
            BROWSERSTACK_USERNAME = (String) config.get("username");
        }

        BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (BROWSERSTACK_ACCESS_KEY == null) {
            BROWSERSTACK_ACCESS_KEY = (String) config.get("access_key");
        }

        String app = System.getenv("BROWSERSTACK_APP_ID");
        if (app != null && !app.isEmpty()) {
            capabilities.setCapability("app", app);
        }

        setDriver(new AndroidDriver(new URL("http://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@" + config.get("server") + "/wd/hub"), capabilities));
    }


}

package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    static FileInputStream file;
    static Properties prop;

    public static Properties readProp() throws IOException {
        file = new FileInputStream(System.getProperty("user.dir")+"generic/src/main/recourses/global.properties");
        prop =new Properties();
        prop.load(file);
        return prop;
    }
}

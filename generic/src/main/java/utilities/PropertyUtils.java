package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    static FileInputStream file;
    static Properties prop;
    private static String curDir=System.getProperty("user.dir");

    public static Properties readProperties(String filePath) throws IOException {
        Properties prop = null;
        File file=new File(curDir+filePath);

        if(file.exists()){
            FileInputStream fs=new FileInputStream(file);
            prop.load(fs);
        }
        return prop;
    }

    public static Properties readProp() throws IOException {
        file = new FileInputStream(System.getProperty("user.dir")+"generic/src/main/recourses/global.properties");
        prop =new Properties();
        prop.load(file);
        return prop;
    }
}

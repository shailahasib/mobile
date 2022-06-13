package utilities;

import org.yaml.snakeyaml.Yaml;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class DeviceProperties {
    private static String curDir = System.getProperty("user.dir");
    public static Map<String, Object> deviceProp;
    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(curDir+"/generic/src/main/recourses/deviceProp.yaml"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml yaml = new Yaml();
        deviceProp = yaml.load(inputStream);
    }

    public static Map<String, Object> getDeviceProp(String deviceType){
        return (Map<String, Object>)deviceProp.get(deviceType);
    }



}

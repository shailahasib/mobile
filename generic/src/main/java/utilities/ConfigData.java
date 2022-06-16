package utilities;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

public class ConfigData {

    public static String getPlatformName() {
        return getValueFromConfigFile("");
    }

    public static String getEmulatorDeviceName() {
        return getValueFromConfigFile("deviceName");
    }

    public static String getURL() {
        return getValueFromConfigFile("");
    }

    public static String getiOSPlatformVersion() {
        return getValueFromConfigFile("iOSVersion");
    }

    public static String getAndroidAutomationName() {
        return getValueFromConfigFile("androidAutomationName");
    }

    public static String getIOSDeviceName() {
        return getValueFromConfigFile("iOSDeviceName");
    }

    public static String getSimulatorName() {
        return getValueFromConfigFile("simulatorDeviceName");
    }

    public static String getXcodeOrgId() {
        return getValueFromConfigFile("");
    }

    public static String getUdid() {
        return getValueFromConfigFile("");
    }

    public static String getXcodeSignInId() {
        return getValueFromConfigFile("");
    }

    public static String getBundleId() {
        return getValueFromConfigFile("");
    }

    public static String getApkFileName() {
        return getValueFromConfigFile("apkFileName");
    }

    public static String getAppFileName() {
        return getValueFromConfigFile("appFileName");
    }

    private static String getValueFromConfigFile(String value) {
        Properties properties = new Properties();
        String propertiesFileName = "config.properties";
        try {
            properties.load(ConfigData.class.getClassLoader().getResourceAsStream(propertiesFileName));
            return properties.getProperty(value);
        } catch (Exception ex) {
            //todo insert logging here
            return null;
        }
    }

    public static String getFilePath(String fileName) {
        ClassLoader classLoader = ConfigData.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return file.getAbsolutePath();
    }


}

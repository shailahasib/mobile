package devicefactory;



public class DeviceFactory {
    private static DeviceFactory instance = null;

    //public static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);
    private DeviceFactory() {
    }

    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }

}

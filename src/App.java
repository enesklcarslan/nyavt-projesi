public class App {
    public static void main(String[] args) {
        // Create a new SmartDeviceFactory
        ISmartDeviceFactory factory = new SmartDeviceFactory();
        // Create a new SmartDevice
        SmartDevice device = factory.createSmartDevice();
        // Start the SmartDevice
        device.start();
    }
}
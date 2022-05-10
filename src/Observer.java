public class Observer implements IObserver {
    public SmartDevice device;
    Observer(SmartDevice device){
        this.device = device;
    }
    public void update(int detected_heat){
        if (detected_heat > 50) {
            System.out.println("The Smart Device's initial temperature is too high...");
            System.out.println("Turning cooler on...");
            device.actuator.turn_on_cooler();
        } else {
            System.out.println("The Smart Device's initial temperature is too low...");
            System.out.println("Turning cooler off...");
            device.actuator.turn_off_cooler();
        }
    }
}
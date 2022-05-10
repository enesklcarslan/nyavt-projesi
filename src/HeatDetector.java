import java.util.Random;

public class HeatDetector{
    private int detected_heat;
    private Observer observer;
    public HeatDetector(SmartDevice device){
        int randint = new Random().nextInt(65);
        set_detected_heat(randint);
        observer = new Observer(device);
        observer.update(detected_heat);
    }
    private void set_detected_heat(int detected_heat){
        this.detected_heat = detected_heat;
    }
    public int get_detected_heat(){
        return detected_heat;
    }
}

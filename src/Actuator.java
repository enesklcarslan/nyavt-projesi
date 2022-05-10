public class Actuator {
    public Actuator(){
        this.is_on = false;
    }
    public void turn_on_cooler(){
        if(is_on){
            System.out.println("The cooler is already on.");
            return;
        }
        System.out.println("Turning cooler on...");
        is_on = true;
    }
    public void turn_off_cooler(){
        if(!is_on){
            System.out.println("The cooler is already off.");
            return;
        }
        System.out.println("Turning cooler off...");
        is_on = false;
    }
    private boolean is_on;
}

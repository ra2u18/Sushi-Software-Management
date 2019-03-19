package comp1206.sushi.common;

import java.util.Random;

public class Drone extends Model{

    private Number speed;
    private Number progress;

    private Number capacity;
    private Number battery;

    private String status;

    private Postcode source;
    private Postcode destination;

    public Drone(Number speed){
        this.setSpeed(speed);
        this.setCapacity(1);
        this.setBattery((new Random().nextInt(101)));
    }

    public Number getSpeed(){ return speed; }
    public Number getProgress(){ return progress; }
    public Number getCapacity(){ return capacity; }
    public Number getBattery(){ return battery; }

    public String getStatus(){ return status; }
    public Postcode getSource(){ return source; }
    public Postcode getDestination(){ return destination; }

    public void setProgress(Number progress){ this.progress = progress; }
    public void setSpeed(Number speed){ this.speed = speed; }
    public void setCapacity(Number capacity){ this.capacity = capacity; }
    public void setBattery(Number battery){ this.battery = battery; }
    public void setStatus(String status) {
        notifyUpdate("status", this.status, status);
        this.status = status;
    }
    public void setSource(Postcode source){ this.source = source; }
    public void setDestination(Postcode destination ){ this.destination = destination; }

    @Override
    public String getName() { return "Drone (" + getSpeed() + ") speed"; }
}

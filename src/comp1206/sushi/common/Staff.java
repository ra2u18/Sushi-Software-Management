package comp1206.sushi.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Staff extends Model{

    private String name;
    private String status;
    private Number fatigue;

    /**
     * Taking a random value from StaffStatus and assigning it to a person.
     * */
    private static final List<StaffStatus> VALUES =
            Collections.unmodifiableList(Arrays.asList(StaffStatus.values()));
    private static final int SIZE = VALUES.size();

    public Staff(String name){
        this.setName(name);
        this.setFatigue((new Random()).nextInt(101));
        this.setStatus( (VALUES.get((new Random()).nextInt(SIZE))).toString() );
    }

    public String getName(){
        return name;
    }

    public void setName(String name){ this.name = name; }

    public Number getFatigue(){ return fatigue; }

    public void setFatigue(Number fatigue) { this.fatigue = fatigue; }

    public String getStatus(){ return status; }

    public void setStatus(String status){
        notifyUpdate("status", this.status, status);
        this.status = status;
    }

}

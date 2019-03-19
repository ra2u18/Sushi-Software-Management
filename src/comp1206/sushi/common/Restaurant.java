package comp1206.sushi.common;

public class Restaurant {

    private String name;
    private Postcode location;

    public Restaurant(String name, Postcode location){
        this.setName(name);
        this.setLocation(location);
    }

    public String getName(){ return name; }

    public Postcode getLocation(){ return location; }

    public void setName(String name){ this.name = name; }

    public void setLocation(Postcode location){ this.location = location; }

}

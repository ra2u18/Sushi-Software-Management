package comp1206.sushi.common;

public class Supplier extends  Model{

    private String name;
    private Postcode postcode;
    private Number distance;

    public Supplier(String name, Postcode postcode){
        this.setName(name);
        this.setPostcode(postcode);
    }

    @Override
    public String getName(){ return name; }

    public Postcode getPostcode(){ return postcode; }

    public Number getDistance(){ return postcode.getDistance(); }

    public void setName(String name){ this.name = name; }

    public void setPostcode(Postcode postcode){ this.postcode = postcode; }

}

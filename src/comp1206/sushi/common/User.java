package comp1206.sushi.common;

public class User {

    private String name;
    private String password;
    private String address;
    private Postcode postcode;

    public User(String name, String password, String address, Postcode postcode){
        this.name = name;
        this.password = password;
        this.address = address;
        this.postcode = postcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getDistance() {
        return postcode.getDistance();
    }

    public String getPassword(){ return this.password; }

    public Postcode getPostcode() {
        return this.postcode;
    }

    public String getAddress(){ return this.address; }

    public void setPostcode(Postcode postcode) {
        this.postcode = postcode;
    }

}

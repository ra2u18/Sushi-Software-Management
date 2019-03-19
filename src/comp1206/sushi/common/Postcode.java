package comp1206.sushi.common;

import comp1206.sushi.server.EstablishConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Postcode extends Model{

    private String name;
    private Map<String, Double> latLong;
    private Number distance;

    public Postcode(String code){
        this.name = code.replaceAll("\\s+", "");;
        calculateLatLong();
        this.distance = Integer.valueOf(0);
    }

    public Postcode(String code, Restaurant restaurant){
        code.replaceAll("\\s+", "");
        this.name = code;
        calculateLatLong();
        calculateDistance(restaurant);
    }

    @Override
    public String getName(){ return name; }

    public Number getDistance(){ return distance; }

    public Map<String, Double> getLatLong(){ return latLong; }

    protected void calculateDistance(Restaurant restaurant){
        /*FUNCTION THAT NEEDS IMPLEMENTED*/
        Postcode destination = restaurant.getLocation();

        /**Radius of the earth.**/
        final int R = 6371;

        double lat1 = this.getLatLong().get("lat");
        double lat2 = destination.getLatLong().get("lat");

        double lon1 = this.getLatLong().get("lon");
        double lon2 = destination.getLatLong().get("lon");

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // into meters

        System.out.println(distance);

        distance = Math.pow(distance, 2);

        this.distance = new Double(distance).intValue();
    }

    protected void calculateLatLong(){

        /**Get latitude and longitude. **/
        EstablishConnection connection = new EstablishConnection(name);
        this.latLong = new HashMap<>();

        try{

            Double[] httpRequest = connection.httpRequest();
            System.out.println(httpRequest[0]);
            latLong.put("lat", httpRequest[0]);
            latLong.put("lon", httpRequest[1]);

        }catch (Exception ex){
            System.out.println("Calculate Lat Long error!");
        }

        this.distance = Integer.valueOf(0);
    }

}

package comp1206.sushi.server;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EstablishConnection {

    String postcodeValue = "";
    Double[] latLong = new Double[2];

    public EstablishConnection(String postcodeValue){
        System.out.println(postcodeValue);
        this.postcodeValue = postcodeValue;
    }

    public Double[] httpRequest() throws Exception {

        /**Get rid of any auxiliary spaces, tabs or new lines. **/
        URL url = new URL("http://api.postcodes.io/postcodes/"+postcodeValue);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "application/json");

        String inputLine;
        StringBuffer response = new StringBuffer();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while( (inputLine = in.readLine()) != null )
            response.append(inputLine);
        in.close();

        JSONObject latitude = new JSONObject(response.toString());
        JSONObject longitude = new JSONObject(response.toString());

        Double lat = latitude.getJSONObject("result").getDouble("latitude");
        Double lon = longitude.getJSONObject("result").getDouble("longitude");

        /** Place latitude on position 0 **/
        latLong[0] = lat;

        /** Place longitude on position 1 **/
        latLong[1] = lon;

        return latLong;
    }

}

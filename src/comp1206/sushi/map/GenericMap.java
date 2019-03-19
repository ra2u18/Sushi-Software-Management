package comp1206.sushi.map;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import comp1206.sushi.common.Drone;
import comp1206.sushi.server.EstablishConnection;
import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GenericMap {

    private static Coordinate source;
    private static Coordinate destination;

    private static Marker sourceMarker;
    private static Marker destinationMarker;
    private static Marker droneMarker;

    private static MapLabel sourceLabel;

    private static VBox vBox;

    private Drone mapDrone = null;

    private CoordinateLine srcDestLine;

    private Double sourceLat;
    private Double sourceLon;
    private Double destLat;
    private Double destLon;

    private boolean isMarkerRotating = false;

    private MapView mapView;

    /**Api key for bing maps.**/
    private String bingApiKey = "AoBBtzpCOL3UjuxBhKAfuzaphl7EpkKqMsUoOTtsLNe2V_oNy9cxGzWNl6jVyU-5";

    public GenericMap(){ }


    private Pane createBottomPane() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);

        // label for showing the map's center
        Label labelCenter = new Label();
        hbox.getChildren().add(labelCenter);
        // add an observer for the map's center property to adjust the corresponding label
        mapView.centerProperty().addListener((observable, oldValue, newValue) -> {
            labelCenter.setText(newValue == null ? "" : ("center: " + newValue.toString()));
        });

        // label for showing the map's zoom
        Label labelZoom = new Label();
        hbox.getChildren().add(labelZoom);
        // add an observer to adjust the label
        mapView.zoomProperty().addListener((observable, oldValue, newValue) -> {
            labelZoom.setText(null == newValue ? "" : ("zoom: " + newValue.toString()));
        });

        Button droneMovement = new Button("See drone");
        droneMovement.setOnAction(e->{



        });

        return hbox;
    }

    public void initMapAndControls(Projection projection) {

        vBox = new VBox();
        vBox.setMinSize(600, 400);

        /**Create new map.**/
        mapView = new MapView();

        /**Add the map to the vertical box and create a bottom pane with additional info.**/
        vBox.getChildren().add(mapView);
        vBox.getChildren().add(createBottomPane());

        mapView.setPrefSize(600, 400);
        mapView.setAnimationDuration(500);

        mapView.initializedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        /**Assign bing api key ( Maps JAVASCRIPT API )**/
        mapView.setBingMapsApiKey(bingApiKey);

        /**Set the map type to BINGMAPS_ROAD.**/
        mapView.setMapType(MapType.BINGMAPS_ROAD);
        mapView.initialize(projection);

    }

    public void afterMapIsInitialized() {

        findLatLong();

        mapView.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
            event.consume();
            mapView.setExtent(event.getExtent());
        });

        mapView.setCenter(source);
        mapView.setZoom(14);

        /**Create markers for source and destination at the right position.**/
        sourceMarker = new Marker( getClass().getResource("/comp1206/sushi/images/sourceMarker.png"), -31, -59 ).setPosition(source).setVisible(false);
        destinationMarker = new Marker( getClass().getResource("/comp1206/sushi/images/destination.png"), -31, -10 ).setPosition(destination).setVisible(false);

        droneMarker = new Marker( getClass().getResource("/comp1206/sushi/images/droneMarker.png")).setPosition(source).setVisible(false);

        /**Add label to the source**/
//        sourceLabel = new MapLabel("Source").setPosition(source).setVisible(true);
//        sourceMarker.attachLabel(sourceLabel);

        /**Add markers on the map and set their visibility to TRUE.**/
        mapView.addMarker(sourceMarker);
        mapView.addMarker(destinationMarker);
        mapView.addMarker(droneMarker);
        //mapView.addLabel(sourceLabel);

        //droneMarker.setVisible(true);
        sourceMarker.setVisible(true);
        destinationMarker.setVisible(true);

        /**Create coordinate line. **/
        srcDestLine = new CoordinateLine(source, destination)
                .setVisible(true)
                .setColor(Color.FUCHSIA).setWidth(5);
        mapView.addCoordinateLine(srcDestLine);
    }

    public void setDroneToMap(Drone drone){ this.mapDrone = drone; }
    public Drone getMapDrone(){ return this.mapDrone; }
    public VBox requestMap(){
        return vBox;
    }

    private void findLatLong(){

        sourceLat = mapDrone.getSource().getLatLong().get("lat");
        sourceLon = mapDrone.getSource().getLatLong().get("lon");

        destLat = mapDrone.getDestination().getLatLong().get("lat");
        destLon = mapDrone.getSource().getLatLong().get("lon");

        System.out.println(mapDrone.getSource().getLatLong().get("lat") + " " +  mapDrone.getSource().getLatLong().get("lon"));

        source = new Coordinate( sourceLat, sourceLon );
        destination = new Coordinate(destLat, destLon);


    }

    private double bearingBetweenLocations(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {

        double PI = 3.14159;
        double lat1 = latitude1 * PI / 180;
        double long1 = longitude1 * PI / 180;
        double lat2 = latitude2 * PI / 180;
        double long2 = longitude2 * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }

}

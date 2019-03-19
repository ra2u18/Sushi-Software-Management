package comp1206.sushi.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import comp1206.sushi.common.*;
import comp1206.sushi.map.GenericMap;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;


/**
 * Provides the Sushi Server user interface
 *
 */
public class ServerWindow extends Stage implements UpdateListener {

    private static final long serialVersionUID = -4661566573959270000L;
    private static ServerInterface server;
    private static Stage primaryStage;

    private static final PopOver popOver;
    private static final PopOver errorMessagePopOver;
    private static final PopOver mapPopOver;
    private static final PopOver popOverDish;
    private static final PopOver popOverIngredient;

    static{

        popOver = new PopOver();
        errorMessagePopOver = new PopOver();
        errorMessagePopOver.setStyle("-fx-background-color: #FEF3EF;");

        mapPopOver = new PopOver();
        popOverDish = new PopOver();
        popOverIngredient = new PopOver();

    }

    private static Map<Postcode,String> supplierPostcodeMap = new HashMap<>();
    private static Map<Supplier,String> ingredientSupplierMap = new HashMap<>();


    private static List currentList = null;

    private static GenericMap genericMap = null;


    /**THIS NEEDS TO BE CHANGED TO OVERVIEWWW*/
    private static String currentButton="btnIngredients";

    /**THIS NEEDS TO BE CHANGED TO OVERVIEW!!!!!!!!!!!!!!*/
    private static ButtonNames enumButton = ButtonNames.INGREDIENTS;

    private static Controller mainController;
    private Double x_root, y_root;

    /**
     * Create a new server window
     * @param server instance of server to interact with
     */
    public ServerWindow(ServerInterface server, Stage primaryStage) {

        this.primaryStage = primaryStage;
        ServerWindow.server = server;
        primaryStage.setTitle(server.getRestaurantName() + " Server");
        server.addUpdateListener(this);

        setupScene();
        primaryStage.show();

        //Start timed updates
        startTimer();
    }

    /**
     * Start the timer which updates the user interface based on the given interval to update all panels
     */
    public void startTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int timeInterval = 1;

        scheduler.scheduleAtFixedRate(() -> Platform.runLater(()->refreshAll()), 0, timeInterval, TimeUnit.SECONDS);
    }

    /**
     * Refresh all parts of the server application based on receiving new data, calling the server afresh
     */
    public void refreshAll() {
        mainController.update();
    }

    @Override
    /**
     * Respond to the model being updated by refreshing all data displays
     */
    public void updated(UpdateEvent updateEvent) {
        refreshAll();
    }

    private void setupScene() {

        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        try{
            root = fxmlLoader.load();
            primaryStage.setScene(new Scene(root));
            mainController = fxmlLoader.getController();
        }catch(IOException ex){
            ex.getMessage();
        }

        primaryStage.initStyle(StageStyle.UNDECORATED );
        root.setOnMousePressed( event -> {

            x_root = event.getSceneX();
            y_root = event.getSceneY();

        });
        root.setOnMouseDragged( event -> {

            primaryStage.setX( event.getScreenX() - x_root );
            primaryStage.setY( event.getScreenY() - y_root );

        });
    }

    static public ServerInterface getServer(){
        return server;
    }

    public static void setCurrentButton(String currentButton){ ServerWindow.currentButton = currentButton; }
    public static String getCurrentButton(){ return currentButton; }
    public static void setEnumButton(String currentButton) throws Exception{

        switch (ServerWindow.getCurrentButton()){
            case "btnIngredients": {
                enumButton = ButtonNames.INGREDIENTS;
                break;
            }
            case "btnOrders": {
                enumButton = ButtonNames.ORDERS;
                break;
            }
            case "btnDrones": {
                enumButton = ButtonNames.DRONES;
                break;
            }
            case "btnPostcodes": {
                enumButton = ButtonNames.POSTCODES;
                break;
            }
            case "btnSuppliers": {
                enumButton = ButtonNames.SUPPLIERS;
                break;
            }
            case "btnStaff": {
                enumButton = ButtonNames.STAFF;
                break;
            }
            case "btnUsers": {
                enumButton = ButtonNames.USERS;
                break;
            }
            case "btnDishes": {
                enumButton = ButtonNames.DISH;
                break;
            }
            default: throw new Exception("Controller/getList()");
        }

    }
    public static ButtonNames getEnumButton(){ return enumButton; }
    public static void setCurrentList(ListNames listName) throws Exception {

        switch (listName) {

            case DISH: {
                currentList = server.getDishes();
                break;
            }
            case DRONES: {
                currentList = server.getDrones();
                break;
            }
            case INGREDIENTS: {
                currentList = server.getIngredients();
                break;
            }
            case ORDERS: {
                currentList = server.getOrders();
                break;
            }
            case STAFF: {
                currentList = server.getStaff();
                break;
            }
            case SUPPLIERS: {
                currentList = server.getSuppliers();
                break;
            }
            case USERS: {
                currentList = server.getUsers();
                break;
            }
            case POSTCODES: {
                currentList = server.getPostcodes();
                break;
            }
            default:
                throw new Exception(" list not reffered error with key value" + listName);
        }

    }

    public static void delete(Object o) throws ServerInterface.UnableToDeleteException {

        System.out.println(o);

        if( enumButton.equals(ButtonNames.POSTCODES) )
            if ( supplierPostcodeMap.containsKey(o) )
                throw new ServerInterface.UnableToDeleteException("" +
                        "You are not able to delete " + o.toString() + " because it has dependencies somewhere else!\n" +
                        "Supplier " + supplierPostcodeMap.get(o) + " is registered in the system with that Postcode! ");
        if( enumButton.equals(ButtonNames.SUPPLIERS) ) {
                System.out.println("INTRAT");
                if (ingredientSupplierMap.containsKey(o))
                    throw new ServerInterface.UnableToDeleteException("" +
                            "You are not able to delete " + o.toString() + " because it has dependencies somewhere else!\n" +
                            "Ingredient " + ingredientSupplierMap.get(o) + " is registered in the system with that Supplier!");

            }

        int index = ServerWindow.currentList.indexOf(o);
        ServerWindow.currentList.remove(index);

    }
    public static Stage getPrimaryStage() { return ServerWindow.primaryStage; }

    public static PopOver getPopup(){ return ServerWindow.popOver; }
    public static PopOver getErrorPopOver(){ return ServerWindow.errorMessagePopOver; }
    public static PopOver getMapPopOver(){ return ServerWindow.mapPopOver; }
    public static PopOver getPopOverDish(){ return ServerWindow.popOverDish; }
    public static PopOver getPopOverIngredient(){ return ServerWindow.popOverIngredient; }

    public static Map<Postcode,String> getSupplierPostcodeMap(){ return supplierPostcodeMap; }
    public static Map<Supplier, String> getIngredientSupplierMap(){ return ingredientSupplierMap; }
    public static Controller getController(){ return mainController; }

    public static void setGenericMap( GenericMap map ){ ServerWindow.genericMap = map; }
    public static GenericMap getGenericMap(){ return ServerWindow.genericMap; }

}

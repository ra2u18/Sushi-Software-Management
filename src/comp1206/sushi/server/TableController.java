package comp1206.sushi.server;

import com.sothawo.mapjfx.Projection;
import comp1206.sushi.common.*;
import comp1206.sushi.map.GenericMap;
import comp1206.sushi.uipages.DishEditPage;
import comp1206.sushi.uipages.IngredientEditPage;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView<Object> defaultTableView;
    private TableManager tableManager;

    /** CONSTANT VARIABLES **/
    private final double OFFSETX = 26.0;
    private final double OFFSETY = 26.0;

    /** Property object for last selected row. Gets the last selected row in the table
     *  and applies the positions X&Y to the error pop up window. **/
    private ObjectProperty<TableRow<Object>> lastSelectedRow = new SimpleObjectProperty<>();

    /** Pop up window that displays the message of UnableToDeleteException. **/
    private PopOver popOverError = ServerWindow.getErrorPopOver();

    /** Pop over window that displays items to be changed for a specific dish. **/
    private PopOver popOverDish = ServerWindow.getPopOverDish();

    /**Pop up window that displays the map**/
    private PopOver popOverMap = ServerWindow.getMapPopOver();

    /**Pop up window that displays items to be changed for a specific ingredient.**/
    private PopOver popOverIngredient = ServerWindow.getPopOverIngredient();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        defaultTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /** Create a row factory that returns the last selected row of a particular object
         *  using a change listener.  **/
        defaultTableView.setRowFactory(tableView -> {
            TableRow<Object> row = new TableRow<>();

            row.selectedProperty().addListener((obs, wasSelected, isSelected)->{
                if(isSelected)
                    lastSelectedRow.set(row);
            });


            row.setOnMouseClicked(event -> {

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && ServerWindow.getController().getBtnEdit().isSelected())
                {
                    switch (ServerWindow.getEnumButton()){
                        case DRONES:{
                            Drone drone = (Drone)row.getItem();
                            printDroneMap(drone);

                            break;
                        }
                        case DISH:{
                            Dish dish = (Dish)row.getItem();
                            applyDishChanges(dish);

                            break;
                        }
                        case INGREDIENTS:{
                            Ingredient ingredient = (Ingredient)row.getItem();
                            applyIngredientChanges(ingredient);

                            break;
                        }
                    }
                }
            });

            return row;
        });

        /**Can not update UI elements from a non-main thread.**/
        /**Table factory to find what what Object are we clicking on.**/


    }

    private void printDroneMap(Drone drone){
        GenericMap droneMap = new GenericMap();
        droneMap.setDroneToMap(drone);
        droneMap.initMapAndControls(Projection.WEB_MERCATOR);

        popOverMap.setContentNode(droneMap.requestMap());

        popOverMap.show(ServerWindow.getPrimaryStage());
        setPopOverXY(popOverMap);

    }

    private void applyDishChanges(Dish dish){

        /**bun**/
        //System.out.println(dish + " " + popOverDish);

        popOverDish.setTitle("Customize your dish!");
        popOverDish.setContentNode( new DishEditPage(dish).returnPage() );
        popOverDish.show(ServerWindow.getPrimaryStage());

        setPopOverXY(popOverDish);

    }

    private void applyIngredientChanges(Ingredient ingredient){

        popOverIngredient.setTitle("Customize your ingredient!");
        popOverIngredient.setContentNode(new IngredientEditPage(ingredient).returnPage());
        popOverIngredient.show(ServerWindow.getPrimaryStage());

        setPopOverXY(popOverIngredient);

    }

    public void update(ObservableList<Model> observableList){
        if(tableManager != null)
            tableManager.setTable(defaultTableView, observableList);
    }

    /**Set visibility of the table to my preference.**/
    public void visibility(boolean type){
        defaultTableView.setVisible(type);
    }

    public void displayTable(List<String> columnNames){ tableManager = new TableManager(defaultTableView, columnNames); }
    public void delete() {
        try{
            ServerWindow.delete( defaultTableView.getSelectionModel().getSelectedItem() );
            defaultTableView.getItems().remove( defaultTableView.getSelectionModel().getSelectedItem() );
        }catch (ServerInterface.UnableToDeleteException ex)
        {
            /** Create a label with the error message of the Exception and setting it to
             * the content of the pop-up. **/
            popOverError.setContentNode(new Label( ex.getMessage() ));
            /** Displaying the pop-up on the primary stage UI. **/
            popOverError.show( ServerWindow.getPrimaryStage() );
            setPopOverXY(popOverError);

        }
    }

    private void setPopOverXY( PopOver popOver ){
        TableRow<Object> row = lastSelectedRow.get();

        /**Set the right positions for the pop-up, i.e where the error is occurring.**/
        popOver.setX( row.localToScreen( row.getBoundsInLocal() ).getCenterX() + OFFSETX );
        popOver.setY(  row.localToScreen( row.getBoundsInLocal() ).getMinY() - OFFSETY );
    }
}

package comp1206.sushi.server;

import com.sothawo.mapjfx.Projection;
import comp1206.sushi.common.ButtonNames;
import comp1206.sushi.common.ListNames;
import comp1206.sushi.map.GenericMap;
import comp1206.sushi.uielements.IngredientBlock;
import comp1206.sushi.uipages.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class Controller  implements Initializable {

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneSwap;
    @FXML
    private TableController tableController;
    @FXML
    private Button btnModify;
    @FXML
    private VBox functionalityContainer;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private ToggleButton btnEdit;
    @FXML
    private ScrollPane scrollPane;

    private static final Integer poolSize = 6;
    private ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);

    private Button currButton = null;

    /**
     * Declaration of pages
     * */
    private IngredientPage ingredientPage = new IngredientPage();
    private SupplierPage supplierPage = new SupplierPage();
    private StaffPage staffPage = new StaffPage();
    private DronePage dronePage = new DronePage();
    private UserPage userPage = new UserPage();
    private PostcodePage postcodePage = new PostcodePage();
    private DishPage dishPage = new DishPage();

    public Controller(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.addEventFilter(ScrollEvent.SCROLL, scrollEvent -> {

            if(scrollEvent.getDeltaX() != 0)
                scrollEvent.consume();

        });

    }

    @FXML
    private void setupButton(ActionEvent event) throws Exception{
        currButton = (Button) event.getSource();

        /**When the buttons with #setupButton are pressed the table should appear.**/
        visibilityAnchorPaneSwap(true);

        ServerWindow.setCurrentButton(currButton.getId());
        /**
         * Look into this!! CHANGE EVERYTHING FROM MANIPULATING STRING -> ENUM
         * */
        ServerWindow.setEnumButton(currButton.getId());

        try{
            ServerWindow.setCurrentList( getList() );
        }
        catch(Exception ex){
            ex.getMessage();
        }

        tableController.displayTable(getColumns());
        /**
         * After clicking another button it clears any children of the vbox we don't need.
         * LOOK INTO IT!
         * */
        clearOnSwap();

        setDisable(true);
        btnEdit.setDisable(true);

        update();

    }

    @FXML
    private void overviewAction(){
        visibilityAnchorPaneSwap(false);
    }

    private void visibilityAnchorPaneSwap(boolean type) { this.anchorPaneSwap.setVisible(type); }

    @FXML
    private void setupModify(){
        setDisable(false);
        setPage();
    }

    @FXML
    private void closeApp() throws InterruptedException{

        System.out.println("Stop called: Let background threads complete! ");
        threadPool.shutdown();
        if( threadPool.awaitTermination(2, TimeUnit.SECONDS) ){
            System.out.println("Background Threads exited!");
        }else{
            System.out.println("Background thread did not exit, trying to force termination!");
            threadPool.shutdownNow();
        }

        System.out.println("Calling Platform.exit()");
        Platform.exit();

        System.out.println("Calling System.exit(0) <- Interrupts the JVM");
        System.exit(0);


    }

    /**Set add&delete button to be disable at each change of button in case the user is just inspecting.**/
    private void setDisable(boolean type){
        btnDelete.setDisable(type);
        btnAdd.setDisable(type);
    }

    private void setPage(){

        switch (ServerWindow.getEnumButton()){
            case INGREDIENTS:{
                ingredientPage.addEditFeature(false);
                functionalityContainer.getChildren().add(ingredientPage.returnPage());
                break;
            }
            case SUPPLIERS:{
                functionalityContainer.getChildren().add(supplierPage.returnPage());
                break;
            }
            case STAFF:{
                functionalityContainer.getChildren().add(staffPage.returnPage());
                break;
            }
            case DRONES:{
                dronePage.addEditFeature(false);
                functionalityContainer.getChildren().add(dronePage.returnPage());
                break;
            }
            case USERS:{
                functionalityContainer.getChildren().add(userPage.returnPage());
                break;
            }
            case POSTCODES:{
                functionalityContainer.getChildren().add(postcodePage.returnPage());
                break;
            }
            case DISH:{
                dishPage.addEditFeature(false);
                functionalityContainer.getChildren().add(dishPage.returnPage());
                break;
            }
        }
    }
    @FXML
    public void addFunctionalityPane(ActionEvent event){

        switch (ServerWindow.getEnumButton()){
            case INGREDIENTS:{
                if( ingredientPage.validate() )
                    ServerWindow.getServer().addIngredient(ingredientPage.getName(), ingredientPage.getUnit(), ingredientPage.getSupplier(), ingredientPage.getRestockThreshold(), ingredientPage.getRestockAmount());
                else System.out.println("Please input appropriate data into the field!");
                break;
            }
            case SUPPLIERS:{
                if( supplierPage.validate() ) {
                    ServerWindow.getServer().addSupplier(supplierPage.getName(), supplierPage.getPostcode());
                    ServerWindow.getSupplierPostcodeMap().put(supplierPage.getPostcode(),supplierPage.getName() );
                }
                else System.out.println("Please input appropriate data into the field!");
                break;
            }
            case STAFF:{
                if( staffPage.validate() )
                    ServerWindow.getServer().addStaff(staffPage.getName());
                else System.out.println("Please input appropriate data into the field!");
                break;
            }
            case DRONES:{
                if ( dronePage.validate() ) {
                    ServerWindow.getServer().addDrone(dronePage.getSpeed());

                }
                else System.out.println("Please input appropriate data into the field!");

                break;
            }
            case USERS:{
                if( userPage.validate() )
                    ServerWindow.getServer().addUser(userPage.getName(), userPage.getPassword(), userPage.getAddress(), userPage.getPostcode());
                else System.out.println("Please input appropriate data into the field!");
                break;
            }
            case POSTCODES:{
                if( postcodePage.validate() )
                    ServerWindow.getServer().addPostcode(postcodePage.getCode());
                else System.out.println("Please input appropriate data into the field!");
                break;
            }
            case DISH:{
                if( dishPage.validate()){
                    ServerWindow.getServer().addDish(dishPage.getName(), dishPage.getDescription(), dishPage.getPrice(), dishPage.getRestockThreshold(), dishPage.getRestockAmount());
                    dishPage.addEditFeature(false);

                    /**
                     * Add recipe in the table
                     * */

                    List<IngredientBlock> ingredients = dishPage.getRecipePage().getRecipe();
                    for( IngredientBlock ingr : ingredients )
                    {
                        Number currentValue = ingr.getValue();
                        if( currentValue.intValue() != 0 )
                            ServerWindow.getServer().addIngredientToDish(
                                    ServerWindow.getServer().getDishes().get(ServerWindow.getServer().getDishes().size() - 1) , ingr.getComponent(), currentValue.intValue() );

                    }

                }
                break;
            }
        }
    }
    @FXML
    public void deleteRecord(ActionEvent event){
        tableController.delete();
    }
    @FXML
    public void editRecord(){
        List editItems;
       // editItems = tableController.edit();
    }
    public void update() {

        tableController.update(getUpdates());

    }
    /**
     * Thread that clears the functionalityContainer after swapping windows.
     * */
    private void clearOnSwap(){

        ServerWindow.getPopup().hide();

        for(Iterator<Node> iterator = functionalityContainer.getChildren().iterator(); iterator.hasNext(); ){
            Node currentNode = (Node)iterator.next();
            if (currentNode instanceof HBox)
                System.out.println(currentNode.getId());
            else iterator.remove();
        }
    }
    /**
     * Thread that handles the updates in the table
     * */
    private ObservableList getUpdates(){

        class UpdateTask extends Task<ObservableList<?>>{

            Supplier<List> f;
            public  UpdateTask(Supplier<List> f){ this.f = f; }

            @Override
            protected ObservableList<?> call(){
                ObservableList<?> result = FXCollections.observableArrayList(f.get());
                return result;
            }
        }
        UpdateTask updateTask = null;

        switch (ServerWindow.getEnumButton() ){

            case INGREDIENTS:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getIngredients);
                break;
            }
            case ORDERS:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getOrders);
                break;
            }
            case DRONES:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getDrones);
                break;
            }
            case POSTCODES:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getPostcodes);
                break;
            }
            case SUPPLIERS:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getSuppliers);
                break;
            }
            case STAFF:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getStaff);
                break;
            }
            case USERS:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getUsers);
                break;
            }
            case DISH:{
                updateTask = new UpdateTask(ServerWindow.getServer()::getDishes);
                break;
            }

        }
        threadPool.submit(updateTask);

        try{
            return updateTask.get();
        }catch (Exception ex){
            if( ex instanceof InterruptedException || ex instanceof  ExecutionException )
                ex.getMessage();
        }
        return null;
    }
    /**
     * Thread that handles the column names;
     * */
    private List<String> getColumns() {

        class GetColumnNames extends Task<List<String>> {

            String tableSignature;
            String[] tableSignParsed;

            public GetColumnNames(String tableSignature){ this.tableSignature = tableSignature; }

            protected List<String> call() {
                List<String> words = new ArrayList<>();

                tableSignParsed = tableSignature.split("~");

                for (String name : tableSignParsed)
                    words.add(name);

                return words;
            }
        }

        GetColumnNames gcn = null;

        switch (ServerWindow.getEnumButton()){

            case INGREDIENTS:{
                gcn = new GetColumnNames("Name~Unit~Supplier~Restock Threshold~Restock Amount");
                break;
            }
            case ORDERS:{
                gcn = new GetColumnNames("Name~Status~Distance");
                break;
            }
            case DRONES:{
                gcn = new GetColumnNames("Name~Capacity~Battery~Source~Destination");
                break;
            }
            case DISH:{
                gcn = new GetColumnNames("Name~Description~Price~Restock Threshold~Restock Amount~Recipe");
                break;
            }
            case STAFF:{
                gcn = new GetColumnNames("Name~Status~Fatigue");
                break;
            }
            case USERS:{
                gcn = new GetColumnNames("Name~Password~Address~Postcode");
                break;
            }
            case POSTCODES:{
                gcn = new GetColumnNames("Name~Distance");
                break;
            }
            case SUPPLIERS:{
                gcn = new GetColumnNames("Name~Postcode~Distance");
                break;
            }

        }
        threadPool.submit(gcn);
        try{ return gcn.get();}
        catch (Exception ex){
            if( ex instanceof InterruptedException || ex instanceof ExecutionException ){
                System.out.println("TABLESIGNATURE: The thread is interrupted or won't execute!");
            }
        }

        return null;
    }
    private ListNames getList() throws  Exception{

        switch (ServerWindow.getEnumButton()){
            case INGREDIENTS:return ListNames.INGREDIENTS;
            case ORDERS:return ListNames.ORDERS;
            case DRONES:return ListNames.DRONES;
            case POSTCODES:return ListNames.POSTCODES;
            case SUPPLIERS: return ListNames.SUPPLIERS;
            case STAFF: return ListNames.STAFF;
            case USERS: return ListNames.USERS;
            case DISH: return ListNames.DISH;
            default: throw new Exception("Controller/getList()");
        }

    }

    public ToggleButton getBtnEdit(){ return btnEdit; }
    public TableController getTableController(){ return tableController; }

}

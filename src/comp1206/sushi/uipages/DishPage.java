package comp1206.sushi.uipages;

import comp1206.sushi.server.ServerWindow;
import comp1206.sushi.uielements.NumberBlock;
import comp1206.sushi.uielements.RecipeBlock;
import comp1206.sushi.uielements.TextfieldBlock;
import javafx.scene.control.Button;
import org.controlsfx.control.PopOver;

public class DishPage extends GenericPage {

    TextfieldBlock name;
    TextfieldBlock description;
    NumberBlock price;
    NumberBlock restockThreshold;
    NumberBlock restockAmount;
    Button customize;
    RecipePage recipePage;

    public DishPage(){
        name = new TextfieldBlock("Name");
        description = new TextfieldBlock("Description");
        price = new NumberBlock("Price");
        restockAmount = new NumberBlock("Restock Amount");
        restockThreshold = new NumberBlock("Restock Threshold");
        customize = new Button("Customize your dish");

        PopOver popOver = ServerWindow.getPopup();

        customize.setId("customize");
        customize.setLayoutY( customize.getLayoutY() + 30 );
        customize.getStylesheets().add( getClass().getResource("/comp1206/sushi/styles/topPane.css").toExternalForm() );

        customize.setOnAction(event -> {
            popOver.setTitle("Available Ingredients!");

            /** Add a list of ingredients block */

            recipePage = new RecipePage();
            popOver.setContentNode(recipePage.returnPage());
            popOver.show(ServerWindow.getPrimaryStage());
        });

        createPage();
    }

    public void setList(){

        blockList.add(name); validateList.add(name);
        blockList.add(description); validateList.add(description);
        blockList.add(price); validateList.add(price);
        blockList.add(restockThreshold); validateList.add(restockThreshold);
        blockList.add(restockAmount); validateList.add(restockAmount);
        blockListControl.add(customize);

    }

    public String getName(){ return name.getValue(); }
    public String getDescription(){ return description.getValue(); }
    public Number getPrice(){ return price.getValue(); }
    public Number getRestockThreshold(){ return restockThreshold.getValue(); }
    public Number getRestockAmount(){ return restockAmount.getValue(); }
    public Button getRecipeButton(){ return customize; }
    public RecipePage getRecipePage(){ return this.recipePage; }

}

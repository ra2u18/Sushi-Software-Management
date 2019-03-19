package comp1206.sushi.uipages;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import comp1206.sushi.server.ServerWindow;
import comp1206.sushi.uielements.NumberBlock;
import javafx.scene.control.Button;

public class IngredientEditPage extends GenericPage {

    Ingredient ingredient;

    NumberBlock restockThreshold = new NumberBlock("Restock Threshold");
    NumberBlock restockAmount = new NumberBlock("Restock Amount");
    Button applyChanges = new Button("Apply changes");

    public IngredientEditPage(Ingredient ingredient){
        this.ingredient = ingredient;

        createPage();
        applyChanges.getStylesheets().add(getClass().getResource("/comp1206/sushi/styles/dishEditPane.css").toExternalForm());

        applyChanges.setOnAction(event -> {

            String ingredientName = ingredient.getName();
            String ingredientUnit = ingredient.getUnit();
            Supplier supplier = ingredient.getSupplier();

            ServerWindow.getServer().addIngredient(ingredientName, ingredientUnit, supplier, restockAmount.getValue(), restockThreshold.getValue() );
            try{
                ServerWindow.getServer().removeIngredient(ingredient);
            }catch (ServerInterface.UnableToDeleteException ex){
                ex.getMessage();
            }

        });

    }


    @Override
    public void setList() {

        blockList.add(restockThreshold); validateList.add(restockThreshold);
        blockList.add(restockAmount); validateList.add(restockAmount);

        blockListControl.add(applyChanges);

    }

}

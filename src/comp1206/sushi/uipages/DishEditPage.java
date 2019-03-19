package comp1206.sushi.uipages;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import comp1206.sushi.server.ServerWindow;
import comp1206.sushi.uielements.IngredientBlock;
import comp1206.sushi.uielements.NumberBlock;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishEditPage extends GenericPage {

    List<IngredientBlock> availableRecipes = new ArrayList<>();
    IngredientBlock currentRecipe;
    NumberBlock restockThreshold = new NumberBlock("Restock Threshold");
    NumberBlock restockAmount = new NumberBlock("Restock Amount");
    Button applyChanges = new Button("Apply changes");
    Map<Ingredient, Number> dishRecipe = new HashMap<>();
    Dish dish;

    public DishEditPage(Dish dish){
        createPage();
        this.dish = dish;
        /**Look into this**/
        applyChanges.getStylesheets().add(getClass().getResource("/comp1206/sushi/styles/dishEditPane.css").toExternalForm());

        /**Look into this, use addRecipeToDish instead of creating a new DISH**/

        applyChanges.setOnAction(event -> {

            for( IngredientBlock ingr: availableRecipes ){
                Number currentValue = ingr.getValue();
                dishRecipe.put(ingr.getComponent(), currentValue.intValue());
            }

            String dishName = dish.getName();
            String dishDescription = dish.getDescription();
            Number price = dish.getPrice();

            /**Create a new dish with the info from the dish we selected.**/
            ServerWindow.getServer().addDish( dishName, dishDescription, price, restockThreshold.getValue(), restockAmount.getValue());
            /**Pass the dishRecipe to the last dish added.*/
            ServerWindow.getServer().setRecipe(ServerWindow.getServer().getDishes().get(ServerWindow.getServer().getDishes().size() - 1), dishRecipe);

            /**LOOK INTO THIS**/
            try{
            ServerWindow.getServer().removeDish(dish);
            }catch (ServerInterface.UnableToDeleteException ex){
                ex.getMessage();
            }

        });
    }

    @Override
    public void setList() {

        for( Ingredient ingr : ServerWindow.getServer().getIngredients() )
        {
            currentRecipe = new IngredientBlock(ingr); validateList.add(currentRecipe);
            blockList.add(currentRecipe);
            availableRecipes.add(currentRecipe);
        }

        blockList.add(restockThreshold); validateList.add(restockThreshold);
        blockList.add(restockAmount); validateList.add(restockAmount);

        blockListControl.add(applyChanges);


    }

}

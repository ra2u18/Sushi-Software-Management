package comp1206.sushi.uipages;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerWindow;
import comp1206.sushi.uielements.IngredientBlock;

import java.util.ArrayList;
import java.util.List;

public class RecipePage extends GenericPage {

    List<IngredientBlock> recipe = new ArrayList<>();
    IngredientBlock currentRecipe;

    public RecipePage(){
        createPage();
    }

    @Override
    public void setList() {

        for(Ingredient ingr :  ServerWindow.getServer().getIngredients() ){
            currentRecipe = new IngredientBlock(ingr);
            blockList.add(currentRecipe);
            recipe.add(currentRecipe);
        }

    }

    public List<IngredientBlock> getRecipe(){ return recipe; }
}

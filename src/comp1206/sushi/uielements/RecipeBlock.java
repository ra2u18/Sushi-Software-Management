package comp1206.sushi.uielements;

import javafx.scene.control.Button;

public class RecipeBlock extends UIBlock<Button, String> {

    Button recipe;


    public RecipeBlock(String labelName){
        super(labelName);

        recipe = new Button("Create recipe");
        /**Look into how to add a stylesheet to a button*/
        //recipe.getStylesheets().add(getClass().getResource("/comp1206/src/styles/functionalityPane.css").toExternalForm());
        addControls(recipe);
    }

    public boolean validate(){ return true; }

    @Override
    public String getValue() { return null; }

    @Override
    public Button getComponent() {
        return recipe;
    }

}
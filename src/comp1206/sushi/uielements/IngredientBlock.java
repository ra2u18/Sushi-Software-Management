package comp1206.sushi.uielements;

import comp1206.sushi.common.Ingredient;
import javafx.scene.control.Slider;

public class IngredientBlock extends UIBlock<Ingredient, Number> {

    private Ingredient ingredient;
    private Slider quantitySlider;
    private Number quantity = 0;

    public IngredientBlock(Ingredient ingredient){
        super(ingredient.getName());

        this.ingredient = ingredient;
        quantitySlider = new Slider();

        quantitySlider.setMax((Integer)ingredient.getRestockThreshold());
        quantitySlider.setMin(0);
        quantitySlider.setShowTickLabels(true);
        quantitySlider.setSnapToTicks(true);
        quantitySlider.setMajorTickUnit(1);
        quantitySlider.setMinorTickCount(0);
        //quantitySlider.
        quantitySlider.valueProperty().addListener( (obs, oldValue, newValue) -> { quantity = newValue; } );

        addControls(quantitySlider);
    }


    @Override
    public Number getValue() {
        Double temp = Double.parseDouble(quantity.toString());
        return temp.intValue();
    }

    @Override
    public Ingredient getComponent() {
        return ingredient;
    }

    /**TEMPORARY**/
    public boolean validate(){ return true; }

}

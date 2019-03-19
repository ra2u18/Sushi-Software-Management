package comp1206.sushi.common;

import java.util.HashMap;
import java.util.Map;

public class Dish extends Model {

    private String name;
    private String description;
    private Number price;
    private Number restockThreshold;
    private Number restockAmount;
    private Map<Ingredient, Number> recipe;

    public Dish(String name, String description, Number price, Number restockThreshold, Number restockAmount)
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
        this.recipe = new HashMap<>();
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return name;
    }

    public Number getPrice(){
        return price;
    }

    public Map<Ingredient, Number> getRecipe(){
        return recipe;
    }

    public Number getRestockAmount(){
        return restockAmount;
    }

    public Number getRestockThreshold(){
        return restockThreshold;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    public void setPrice(Number price){
        this.price = price;
    }

    public void setRecipe(Map<Ingredient, Number> recipe ){
        this.recipe = recipe;
    }

    public void setRestockThreshold(Number restockThreshold){
        this.restockThreshold = restockThreshold;
    }

    public void setRestockAmount(Number restockAmount){
        this.restockAmount = restockAmount;
    }

}

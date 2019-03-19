package comp1206.sushi.uipages;


import comp1206.sushi.common.Supplier;
import comp1206.sushi.uielements.NumberBlock;
import comp1206.sushi.uielements.SupplierBlock;
import comp1206.sushi.uielements.TextfieldBlock;
import comp1206.sushi.uielements.UIBlock;

public class IngredientPage extends GenericPage {

    TextfieldBlock name;
    TextfieldBlock unit;
    SupplierBlock supplier;
    NumberBlock restockThreshold;
    NumberBlock restockAmount;

    public IngredientPage(){

        name = new TextfieldBlock("Name");
        unit = new TextfieldBlock("Unit");
        supplier = new SupplierBlock("Available Suppliers");
        restockThreshold = new NumberBlock("Restock Threshold");
        restockAmount = new NumberBlock("Restock Amount");
        createPage();
    }

    public void setList(){
        blockList.add(name); validateList.add(name);
        blockList.add(unit); validateList.add(unit);
        blockList.add(supplier);
        blockList.add(restockThreshold); validateList.add(restockThreshold);
        blockList.add(restockAmount); validateList.add(restockAmount);
    }

    public String getName(){ return name.getValue(); }
    public String getUnit(){ return unit.getValue(); }
    public Supplier getSupplier(){ return supplier.getValue(); }
    public Number getRestockThreshold(){ return restockThreshold.getValue(); }
    public Number getRestockAmount(){ return restockAmount.getValue(); }



}

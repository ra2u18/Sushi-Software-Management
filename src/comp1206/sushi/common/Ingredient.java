package comp1206.sushi.common;


public class Ingredient extends Model {

    private String name;
    private String unit;
    private Supplier supplier;
    private Number restockThreshold;
    private Number restockAmount;

    public Ingredient( String name, String unit, Supplier supplier, Number restockAmount, Number restockThreshold ){

        this.setName(name);
        this.setRestockAmount(restockAmount);
        this.setRestockThreshold(restockThreshold);
        this.setSupplier(supplier);
        this.setUnit(unit);

    }

    @Override
    public String getName() { return name; }
    public Number getRestockAmount(){ return restockAmount; }
    public Number getRestockThreshold(){ return restockThreshold; }
    public Supplier getSupplier() { return supplier; }
    public String getUnit(){ return unit; }

    public void setName(String name){ this.name = name; }
    public void setRestockThreshold(Number restockThreshold){ this.restockThreshold = restockThreshold; }
    public void setRestockAmount(Number restockAmount){ this.restockAmount = restockAmount; };
    public void setSupplier(Supplier supplier){ this.supplier = supplier; }
    public void setUnit(String unit){ this.unit = unit; }
}

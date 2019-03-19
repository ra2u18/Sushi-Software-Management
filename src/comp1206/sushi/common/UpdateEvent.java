package comp1206.sushi.common;

public class UpdateEvent {

    public Model model;
    public String property;
    public Object oldValue;
    public Object newValue;

    public UpdateEvent(){}

    /**
     * Create a new update event belonging to a particular model
     * @param model the model being updated
     * */
    public UpdateEvent(Model model){
        this.model = model;
    }

    /**
     * Create a new update event belonging to a particular property value on a model
     * @param model the model being updated
     * @param property property which has changed
     * @param oldValue the previous value
     * @param newValue the newly updated value
     * */
    public UpdateEvent(Model model, String property, Object oldValue, Object newValue){
        this.model = model;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

}

package comp1206.sushi.uielements;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.ArrayList;
import java.util.List;

public abstract class UIBlock<E, T> {

    Label label;
    private final double fontSize = 15;
    private Font labelFont;
    List<Control> controlList = new ArrayList<>();

    public UIBlock(String labelName){
        label = new Label(labelName);
        addControls(label);
    }

    public abstract T getValue();
    public abstract E getComponent();

    public void addControls(Control control){ controlList.add(control); }
    public List<Control> getControlList(){ return controlList; }

    public Label getLabel(){ return label; }

    public boolean validate(){ return false; }
}

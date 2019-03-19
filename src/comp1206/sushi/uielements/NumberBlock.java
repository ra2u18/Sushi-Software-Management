package comp1206.sushi.uielements;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NumberBlock extends UIBlock<TextField, Number> {

    TextField textField;

    public NumberBlock(String labelName){
        super(labelName);
        textField = new TextField();
        textField.setMaxWidth(250);
        addControls(textField);
    }

    public Number getValue(){
        return Integer.parseInt(textField.getText());
    }

    public TextField getComponent(){
        return textField;
    }

    public boolean validate(){

        if( Integer.parseInt(textField.getText()) < 0 )
            return false;


        char[] chars = textField.getText().toCharArray();
        for( char c : chars )
            if( Character.isDigit(c) )
                return false;
        return true;
    }

}

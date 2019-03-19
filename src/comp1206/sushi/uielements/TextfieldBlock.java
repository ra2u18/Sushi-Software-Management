package comp1206.sushi.uielements;

import javafx.scene.control.TextField;

public class TextfieldBlock extends UIBlock<TextField, String> {

    TextField textField;

    public TextfieldBlock(String labelName){
        super(labelName);
        textField = new TextField();
        textField.setMaxWidth(250);
        addControls(textField);
    }

    public String getValue(){
        return textField.getText();
    }

    public TextField getComponent(){
        return textField;
    }

    public boolean validate(){
        char[] chars = textField.getText().toCharArray();
        for( char c : chars )
            if( !Character.isDigit(c) )
                return false;
        return true;
    }

}

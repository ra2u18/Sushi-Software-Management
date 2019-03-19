package comp1206.sushi.uielements;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.server.ServerWindow;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class PostcodeBlock extends UIBlock<ComboBox<Postcode>, Postcode>{

    ComboBox<Postcode> comboBox;
    List<Postcode> listPostcodes;

    public PostcodeBlock(String labelName){
        super(labelName);

        comboBox = new ComboBox<>();
        setList();
        comboBox.setMaxWidth(250);
        addControls(comboBox);
    }

    public boolean validate(){ return true; }

    private void setList(){

        if(ServerWindow.getServer().getPostcodes().isEmpty()){
            listPostcodes = new ArrayList<>();
        }else listPostcodes = ServerWindow.getServer().getPostcodes();

        comboBox.getItems().addAll(FXCollections.observableArrayList(listPostcodes));
    }

    public Postcode getValue(){ return comboBox.getSelectionModel().getSelectedItem(); }

    public ComboBox<Postcode> getComponent(){ return comboBox; }

}

package comp1206.sushi.uielements;

import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerWindow;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class SupplierBlock extends UIBlock<ComboBox<Supplier>, Supplier>  {

    ComboBox<Supplier> comboBox;
    List<Supplier> listSuppliers;

    public SupplierBlock(String labelName){
        super(labelName);

        comboBox = new ComboBox<>();
        setList();
        comboBox.setMaxWidth(250);
        addControls(comboBox);
    }

    public boolean validate(){ return true; }

    private void setList(){
        if(ServerWindow.getServer().getSuppliers().isEmpty())
            listSuppliers = new ArrayList<>();
        else listSuppliers = ServerWindow.getServer().getSuppliers();

        comboBox.getItems().addAll(FXCollections.observableArrayList(listSuppliers));
    }

    public Supplier getValue(){
        return comboBox.getSelectionModel().getSelectedItem();
    }

    public ComboBox<Supplier> getComponent(){
        return comboBox;
    }

}

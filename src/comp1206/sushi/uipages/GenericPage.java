package comp1206.sushi.uipages;

import comp1206.sushi.server.ServerWindow;
import comp1206.sushi.uielements.UIBlock;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.List;


public abstract class GenericPage{

    VBox functionalVBox = new VBox();
    List<UIBlock> blockList = new ArrayList<>();
    List<Control> blockListControl = new ArrayList<>();

    List<UIBlock> validateList = new ArrayList<>();



    public GenericPage(){

    }

    public VBox createPage(){
        setList();

        functionalVBox.setPadding( new Insets(15, 15, 15, 15));
        functionalVBox.setSpacing(10);

        for (UIBlock uiBlock : blockList)
            functionalVBox.getChildren().addAll(uiBlock.getControlList());

        if ( !blockListControl.isEmpty() )
                functionalVBox.getChildren().addAll(blockListControl);

        return functionalVBox;
    }
    public VBox returnPage(){return functionalVBox;}
    public abstract void setList();


    public boolean validate(){
        for(UIBlock ui : validateList)
            if(ui.validate())
                return false;
        return true;
    }

    public void addEditFeature(boolean status){
       ServerWindow.getController().getBtnEdit().setDisable(status);
    }

}

package comp1206.sushi.uipages;

import comp1206.sushi.uielements.NumberBlock;
import comp1206.sushi.uielements.TextfieldBlock;
import comp1206.sushi.uielements.UIBlock;

public class StaffPage extends GenericPage {

    TextfieldBlock name;
    TextfieldBlock status;
    NumberBlock fatigue;

    public StaffPage(){

        name = new TextfieldBlock("Name");
        /**
         * FATIGUE IS TEMPORARY
         * */

        fatigue = new NumberBlock("Fatigue");

        /**
         * STATUS IS TEMPORARY
         * */
        status = new TextfieldBlock("Status");

        createPage();
    }

    @Override
    public void setList() {
        blockList.add(name); validateList.add(name);
//        blockList.add(fatigue);
//        blockList.add(status);
    }

    public String getName(){ return name.getValue(); }
    public Number getFatigue(){ return fatigue.getValue(); }
    public String getStatus(){ return status.getValue(); }

}

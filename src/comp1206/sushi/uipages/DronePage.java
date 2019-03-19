package comp1206.sushi.uipages;

import comp1206.sushi.uielements.NumberBlock;

public class DronePage extends GenericPage{

    private NumberBlock speed;

    public DronePage(){

        speed = new NumberBlock("Speed");
        createPage();
    }

    @Override
    public void setList() {
        blockList.add(speed); validateList.add(speed);
    }

    public Number getSpeed(){ return speed.getValue(); }

}

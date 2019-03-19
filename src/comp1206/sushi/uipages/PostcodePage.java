package comp1206.sushi.uipages;

import comp1206.sushi.uielements.TextfieldBlock;

public class PostcodePage extends GenericPage{

    TextfieldBlock code;

    public PostcodePage(){
        code = new TextfieldBlock("Code");
        createPage();
    }

    @Override
    public void setList() {
        blockList.add(code); validateList.add(code);
    }

    public String getCode(){ return code.getValue(); }
}

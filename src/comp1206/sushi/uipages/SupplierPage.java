package comp1206.sushi.uipages;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.uielements.PostcodeBlock;
import comp1206.sushi.uielements.TextfieldBlock;

public class SupplierPage extends GenericPage{

    private TextfieldBlock name;
    private PostcodeBlock postcodeBlock;

    public SupplierPage(){
        name = new TextfieldBlock("Name");
        postcodeBlock = new PostcodeBlock("Postcodes in the system");

        createPage();
    }

    public void setList(){
        blockList.add(name); validateList.add(name);
        blockList.add(postcodeBlock);
    }

    public String getName(){ return name.getValue(); }
    public Postcode getPostcode(){ return postcodeBlock.getValue(); }

}

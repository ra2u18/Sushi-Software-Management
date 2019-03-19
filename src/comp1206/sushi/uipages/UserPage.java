package comp1206.sushi.uipages;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.uielements.PostcodeBlock;
import comp1206.sushi.uielements.TextfieldBlock;

public class UserPage extends GenericPage {

    TextfieldBlock name;
    /**
     * Look into this and build a SecureRandom generator ( RNG )
     * */
    TextfieldBlock password;
    TextfieldBlock address;
    PostcodeBlock postcodeBlock;

    public UserPage(){
        name = new TextfieldBlock("Name");
        password = new TextfieldBlock(" Password ");
        address = new TextfieldBlock("Address");
        postcodeBlock = new PostcodeBlock("Postcodes available in the system");
        createPage();
    }

    @Override
    public void setList() {
        blockList.add(name); validateList.add(name);
        blockList.add(password); validateList.add(password);
        blockList.add(address); validateList.add(address);
        blockList.add(postcodeBlock);
    }

    public String getName(){ return name.getValue(); }
    public String getPassword(){ return password.getValue(); }
    public String getAddress(){ return address.getValue(); }
    public Postcode getPostcode(){ return postcodeBlock.getValue(); }

}

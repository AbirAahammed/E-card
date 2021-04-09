package redbull.ecard.local.DataLayerTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static junit.framework.TestCase.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.DataLayer.Contact;
import redbull.ecard.util.testData.testID;

@RunWith(AndroidJUnit4.class)
public class ContactBehaviourCheck {
    private Contact testContact;
    @Before
    public void setup(){
        testContact = new Contact(testID.cell,testID.cell,testID.email);
    }
    @Test
    public void behaviourTest(){
        //get methods behaviour
        assertEquals(testContact.getCellPhone(),testID.cell);
        assertEquals(testContact.getHomePhone(),testID.cell);
        assertEquals(testContact.getEmailAddress(),testID.email);
        try {
            Contact testContact = new Contact();
        }catch(Exception e){
            fail();
        }
    }
}

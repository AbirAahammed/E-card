package redbull.ecard.local.DataLayerTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static junit.framework.TestCase.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.DataLayer.Name;
import redbull.ecard.util.testData.testID;

@RunWith(AndroidJUnit4.class)
public class NameBehaviourCheck {
    private Name testName;
    @Before
    public void setup(){
        testName = new Name(testID.name,testID.l_name,testID.name);
    }
    @Test
    public void behaviourTest(){
        assertEquals(testName.getLastName(),testID.l_name);
        assertEquals(testName.getFirstName(),testID.name);
        assertEquals(testName.getMiddleName(),testID.name);

        try{
            Name testName = new Name();
        }catch(Exception e){
            fail();
        }
    }

}

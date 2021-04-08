package redbull.ecard.local.DataLayerTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static junit.framework.TestCase.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.util.testData.testID;

@RunWith(AndroidJUnit4.class)
public class AddressBehaviourCheck {
    private Address testAddress;
    @Before
    public void setup(){
        testAddress = new Address(testID.road,testID.house,testID.postalCode,testID.city,testID.province,testID.country);
    }
    @Test
    public void behaviourTest(){
        //get methods behaviour
        assertEquals(testAddress.getRoadNumber(),testID.road);
        assertEquals(testAddress.getHouseNumber(),testID.house);
        assertEquals(testAddress.getPostalCode(),testID.postalCode);
        assertEquals(testAddress.getCity(),testID.city);
        assertEquals(testAddress.getProvince(),testID.province);
        assertEquals(testAddress.getCountry(),testID.country);
        try { //must have a default constructor
            Address testAddress= new Address();
        }catch(Exception e){
            fail();
        }

        testAddress.setHouseNumber("23");
        assertEquals(testAddress.getHouseNumber(),"23");
        assertTrue(testAddress.isValid());
        testAddress.setHouseNumber("");
        assertFalse(testAddress.isValid());
    }


}

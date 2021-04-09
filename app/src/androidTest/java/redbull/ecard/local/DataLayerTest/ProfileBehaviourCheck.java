package redbull.ecard.local.DataLayerTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import static junit.framework.TestCase.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.util.testData.testID;

@RunWith(AndroidJUnit4.class)
public class ProfileBehaviourCheck {
    private Profile testProfile;
    private Address testA = new Address(testID.road, testID.house, testID.postalCode, testID.city, testID.province, testID.country);
    private Contact testC = new Contact(testID.cell, testID.cell, testID.email);
    private Name testN = new Name(testID.name, testID.l_name, "");
    @Before
    public void setup(){
        testProfile = new Profile(testN,
                testC,
                testA,
                testID.description,testID.service);
    }
    @Test
    public void behaviourTest(){
        assertEquals(testProfile.getAddress(),testA);
        assertEquals(testProfile.getContact(),testC);
        assertEquals(testProfile.getName(),testN);
        assertEquals(testProfile.getDescription(),testID.description);
        assertEquals(testProfile.getService(),testID.service);
        try{
            Profile testProfile = new Profile();
        }catch (Exception e){
            fail();
        }
    }
}

package redbull.ecard.LogicLayer;

import com.google.firebase.auth.FirebaseAuth;

import junit.framework.TestCase;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.DataLayer.ServiceTypes;
import redbull.ecard.DataLayer.Services;

public class ProfileLogicTest extends TestCase {

    public void testCreateProfile() {
        ProfileLogic profileLogic = ProfileLogic.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<ServiceTypes> serviceTypeList = new ArrayList<>();
        serviceTypeList.add(ServiceTypes.LAWYER);
        serviceTypeList.add(ServiceTypes.PLUMBER);
//        String uid = "UrVRoKrfe2R2JvVs4jbdTS7UeUB2";
        String description = "This is a description";
        Profile profile = new Profile(new Name("", "Sakib", "Ahammed"),
            new Contact("123 456 7891", "987 654 3210", "redbull.com"),
            new Address("12", "21", "Q3E 2R4", "Winnipeg", "MB", "CANADA"),
            description,
            new Services(serviceTypeList)
        );
        profileLogic.createProfile(profile);
    }
}
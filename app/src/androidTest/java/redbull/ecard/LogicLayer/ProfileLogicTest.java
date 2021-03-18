package redbull.ecard.LogicLayer;

import com.google.firebase.auth.FirebaseAuth;

import junit.framework.TestCase;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;

public class ProfileLogicTest extends TestCase {

    public void testCreateProfile() {
        ProfileLogic profileLogic = ProfileLogic.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String uid = "UrVRoKrfe2R2JvVs4jbdTS7UeUB2";
        Profile profile = new Profile(
                                        new Name("Abir", "Sakib", "Ahammed"),
                                        uid,
                                        new Contact("123 456 7891", "987 654 3210", "redbull.com"),
                                        new Address("12", "21", "Q3E 2R4", "Winnipeg", "MB", "CANADA"));
        profileLogic.createProfile(profile);
    }
}
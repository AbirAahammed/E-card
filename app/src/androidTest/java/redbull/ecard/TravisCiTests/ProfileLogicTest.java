package redbull.ecard.TravisCiTests;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import static junit.framework.TestCase.*;


import org.junit.Before;
import org.junit.Test;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.util.testData.testID;
import redbull.ecard.util.testContent;

import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProfileLogicTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private FirebaseAuth auth;
    private String uid;
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }
    @Test
    public void testCreateProfile() {
        auth= FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(testID.email, testID.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    assertNotNull("failed with login",user);
                }
                else {
                    System.out.println("Test failed because failed with login");
                    System.out.flush();
                    fail();
                }
            }}
        );
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }

        ProfileLogic profileLogic = ProfileLogic.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Make a ServiceTypes list for Services. (Unused because Services object was replaced)
//        ArrayList<ServiceTypes> serviceTypeList = new ArrayList<>();
//        serviceTypeList.add(ServiceTypes.LAWYER);
//        serviceTypeList.add(ServiceTypes.PLUMBER);

        Profile profile = new Profile(
                new Name(testID.name, testID.l_name, ""),
                new Contact(testID.cell, testID.cell, testID.email),
                new Address(testID.road, testID.house, testID.postalCode, testID.city, testID.province, testID.country),
                testID.description,
                testID.service);
//                new Services(serviceTypeList));


        profileLogic.createProfile(profile);
        profileLogic.getProfile(uid).addOnProfileGetListener(new OnProfileGetListener() {
            @Override
            public void onSuccess(@NonNull Profile profile) {
                System.out.println("Test passed");
                assertEquals(profile.getAddress().getCity(),testID.city);
                System.out.flush();
                return;

            }

            @Override
            public void onProfileNotFound() {
                fail("failed to retrieve profile");

            }

            @Override
            public void onFailure() {
                fail("failed to create profile");
            }
        });
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        clean();
    }

    private void clean() {
        testContent clean= new testContent();
        clean.removeFromDB(uid);
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        FirebaseAuth.getInstance().signOut();
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
package redbull.ecard.TravisCiTests;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import static junit.framework.TestCase.*;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.DataLayer.ServiceTypes;
import redbull.ecard.DataLayer.Services;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.DataLayer.testData.testID;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        // Make a ServiceTypes list for Services.
        ArrayList<ServiceTypes> serviceTypeList = new ArrayList<>();
        serviceTypeList.add(ServiceTypes.LAWYER);
        serviceTypeList.add(ServiceTypes.PLUMBER);

        Profile profile = new Profile(
                new Name(testID.name, testID.l_name, ""),
                new Contact(testID.cell, testID.cell, testID.email),
                new Address(testID.road, testID.house, testID.postalCode, testID.city, testID.province, testID.country),
                testID.description,
                new Services(serviceTypeList));


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
        System.out.println(uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("profiles").child(uid).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                System.out.println("clean up done");
            }
        });
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        FirebaseAuth.getInstance().signOut();
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
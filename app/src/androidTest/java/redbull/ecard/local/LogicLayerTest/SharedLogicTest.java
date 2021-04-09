package redbull.ecard.local.LogicLayerTest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnConnectionsGetListener;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.LogicLayer.ShareLogic;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testData.testID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class SharedLogicTest {
    private FirebaseAuth auth;
    private String uid;
    @Before
    public void setUpStreams() {


    }
    @Test
    public void shareTest(){
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
                ShareLogic instance =ShareLogic.getInstance(profile);
                instance.createConnection("7BFJRQw4K6dVBoWADcji1yOQXQu2");
                try {
                    Thread.sleep(2000L);
                }catch (Exception e){

                }
//                instance.createConnection("IhnuB3O0gUZgkpf2FQ33hcOem022");
//                try {
//                    Thread.sleep(2000L);
//                }catch (Exception e){
//
//                }
                instance.getConnections().addOnConnectionsGetListener(new OnConnectionsGetListener() {
                    @Override
                    public void onAllReadSuccess(@NonNull ArrayList<Profile> profiles) {
                        // The array of connections
                        for(int i=0;i<profiles.size();i++) {
                            Log.i("ShareTest", profiles.get(i).getID());
                        }
                        assertEquals(profiles.size(),1);

                    }

                    @Override
                    public void onSuccess(@NonNull Profile profile) {
                        // Empty
                    }

                    @Override
                    public void onFailure() {
                        Log.d("test", "Failed to fetch cards");

                       fail();
                    }
                });


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

    private void clean(){
        testContent clean= new testContent();
        //clean.removeFromDB(uid);
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        FirebaseAuth.getInstance().signOut();
    }
}

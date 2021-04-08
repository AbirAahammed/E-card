package redbull.ecard.local.LogicLayerTest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;

import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnConnectionsGetListener;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.LogicLayer.ShareLogic;
import redbull.ecard.util.testData.testID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class SharedLogicTest {
    private FirebaseAuth auth;
    private String curr_email,password,uid;
    @Before
    public void setUpStreams() {
        curr_email=testID.email;
        password= testID.password;
        testID.email="Demo@gmail.com";
        testID.password="123456";
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
        profileLogic.getProfile(uid).addOnProfileGetListener(new OnProfileGetListener() {
            @Override
            public void onSuccess(@NonNull Profile profile) {
                ShareLogic instance =ShareLogic.getInstance(profile);
                instance.getConnections().addOnConnectionsGetListener(new OnConnectionsGetListener() {

                    @Override
                    public void onAllReadSuccess(@NonNull ArrayList<Profile> profiles) {
                        // The array of connections
                        assertEquals(profiles.size(),2);
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

    }
    @After
    public void clean(){
        testID.email=curr_email;
        testID.password=password;
        FirebaseAuth.getInstance().signOut();
    }
}

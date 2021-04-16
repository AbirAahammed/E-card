package redbull.ecard.local.LogicLayerTest;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.LogicLayer.RunnableCallBack;
import redbull.ecard.LogicLayer.ShareLogic;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testData.testID;
import redbull.ecard.util.testWithHWAcceration;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CardDataBaseConnector {
    private FirebaseAuth auth;
    @Test
    public void testFetch(){
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
                    TestCase.fail();
                }
            }}
        );
        testWithHWAcceration.waitTime();
        ArrayList<RunnableCallBack> callBackSuccesses = new ArrayList<RunnableCallBack>();
        callBackSuccesses.add( () -> scanFetchCallbackSuccess());

        ArrayList<RunnableCallBack> callBackFailures = new ArrayList<RunnableCallBack>();
        callBackFailures.add( () -> scanfetchCallbackfailure());
       new CardDatabaseConnector(callBackSuccesses, callBackFailures).fetchScannerProfileInformation ("XO5bxfLQsaW6TBaONAdD0awLmyg1");;
        testWithHWAcceration.waitTime();



    }
    private void scanFetchCallbackSuccess(){
        Profile scannedProfile = CardDatabaseConnector.getScannerProfile();
        assertEquals(scannedProfile.getName().getFirstName(),"John");
    }
    private void scanfetchCallbackfailure(){
        fail();
    }
}

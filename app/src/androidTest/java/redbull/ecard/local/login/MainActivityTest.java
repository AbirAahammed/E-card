package redbull.ecard.local.login;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.SmallTest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import redbull.ecard.DataLayer.testData.testID;
import redbull.ecard.MainActivity;
import redbull.ecard.R;
import redbull.ecard.UILayer.login.LoginActivity;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testWithHWAcceration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

@SmallTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Before
    public void setup(){
        FirebaseAuth auth= FirebaseAuth.getInstance();
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
    }
    @Test
    public void fragmentTest(){
        testWithHWAcceration.waitTime();
        testContent test = new testContent();
        test.loginActivityTest_success();
        Espresso.closeSoftKeyboard();
        try{
            onView(withId(R.id.done)).perform(scrollTo());
        }catch (Exception e){
            System.out.println("No need for scroll");
        }
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation_notifications)).perform(click());
        onView(withId(R.id.navigation_dashboard)).perform(click());
    }
    @Test
    public void homeTest(){

    }
    @After
    public void clean(){
        testContent clean = new testContent();
        String uid= FirebaseAuth.getInstance().getUid();
        clean.removeFromDB(uid);
        FirebaseAuth.getInstance().signOut();
    }
}

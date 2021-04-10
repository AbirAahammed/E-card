package redbull.ecard.local.UITest;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.SmallTest;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import redbull.ecard.R;
import redbull.ecard.UILayer.loginActivity.LoginActivity;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testWithHWAcceration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
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
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Test
    public void fragmentTest(){ //this will include view Profile, view added Card and camera usages
        testWithHWAcceration.waitTime();
        testContent test = new testContent();
        test.loginActivityTest_success();

        try{
            closeSoftKeyboard();
            onView(withId(R.id.done)).perform(scrollTo());
            onView(withId(R.id.done)).perform(click());
        }catch (Exception e){
            System.out.println("No need for scroll");
        }
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.navigation_notifications)).perform(click());
        pressBack();
        onView(withId(R.id.navigation_dashboard)).perform(click());
    }

    @After
    public void clean(){
        testContent clean = new testContent();
        String uid= FirebaseAuth.getInstance().getUid();
        clean.removeFromDB(uid);
        FirebaseAuth.getInstance().signOut();
    }
}

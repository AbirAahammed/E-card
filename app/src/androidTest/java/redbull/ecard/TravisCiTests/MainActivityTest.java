package redbull.ecard.TravisCiTests;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;


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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;

@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> intentsTestRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void fragmentTest(){
        testWithHWAcceration.waitTime();
        testContent test = new testContent();
        test.loginActivityTest_success();

        try{
            ViewActions.closeSoftKeyboard();
            onView(withId(R.id.done)).perform(scrollTo());
            onView(withId(R.id.done)).perform(click());
        }catch (Exception e){
            System.out.println("No need for scroll");
        }
        testWithHWAcceration.waitTime();
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

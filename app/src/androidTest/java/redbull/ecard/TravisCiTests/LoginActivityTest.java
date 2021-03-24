package redbull.ecard.TravisCiTests;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static junit.framework.TestCase.*;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.DataLayer.testData.testID;
import redbull.ecard.MainActivity;
import redbull.ecard.R;
import redbull.ecard.UILayer.login.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    private LoginActivity login;
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);
//    @Rule
//    public ActivityScenarioRule<LoginActivity> activityTestRule =
//            new ActivityScenarioRule<>(LoginActivity.class);
    @Before
    public void setup(){
        login= intentsTestRule.getActivity();
//        intentsTestRule.launchActivity()
    }
    @Test
    public void loginActivityTest_success() {
        // successfully login

        onView(withId(R.id.username)).perform(typeText(testID.email),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(testID.password),closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        assertNotNull(FirebaseAuth.getInstance().getCurrentUser());
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void loginActivityTest_fail() {
        // failed to login
        onView(withId(R.id.username)).perform(typeText(testID.email),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(testID.password+"sdsadwa"),closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());

        onView(withText("Authentication failed."))
                .inRoot(withDecorView(not(intentsTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}

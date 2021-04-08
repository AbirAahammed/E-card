package redbull.ecard.TravisCiTests;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.R;
import redbull.ecard.UILayer.loginActivity.LoginActivity;
import redbull.ecard.util.testContent;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    private LoginActivity Signup;
    @Rule
    public ActivityTestRule intentsTestRule =
            new ActivityTestRule(LoginActivity.class);
    @Test
    public void SignUp_success() {

        onView(withId(R.id.username)).perform(closeSoftKeyboard());
        try {
            onView(withId(R.id.button_register)).perform(scrollTo());
            Thread.sleep(2000L);
        }
        catch(Exception e) {};
        onView(withId(R.id.button_register)).check(matches(isDisplayed()));
        testContent follow= new testContent();
        follow.signUp_test_success();

    }

}

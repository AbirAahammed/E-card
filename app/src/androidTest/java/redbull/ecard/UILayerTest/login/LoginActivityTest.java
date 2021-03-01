package redbull.ecard.UILayerTest.login;

import android.content.Intent;
import android.util.Log;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import redbull.ecard.MainActivity;
import redbull.ecard.testData.testID;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.R;
import redbull.ecard.UILayer.login.LoginActivity;


@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

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
        intended(hasComponent(MainActivity.class.getName()));
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

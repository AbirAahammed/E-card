package redbull.ecard.util;

import android.view.View;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;

import org.hamcrest.Matcher;

import redbull.ecard.DataLayer.testData.testID;
import redbull.ecard.MainActivity;
import redbull.ecard.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import redbull.ecard.util.ViewShownIdlingResource;
public class testWithHWAcceration extends testContent {
    @Override
    public void signUp_test_success() {

        waitViewShown(withResourceName("email"));
        onView(withResourceName("email")).perform(typeText(testID.signup_email),closeSoftKeyboard());
        onView(withText("NEXT")).perform(click());
        onView(withResourceName("name")).perform(typeText(testID.signup_name),closeSoftKeyboard());
        onView(withResourceName("password")).perform(typeText(testID.password),closeSoftKeyboard());
        onView(withText("SAVE")).perform(click());
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Override
    public void signUp_test_fail() {
        waitViewShown(withResourceName("email"));
        onView(withResourceName("email")).perform(typeText(testID.email),closeSoftKeyboard());
        onView(withText("NEXT")).perform(click());
        onView(withResourceName("password")).perform(typeText(testID.password),closeSoftKeyboard());
        onView(withText("SIGN IN")).perform(click());
        try {
            Thread.sleep(2000L);
        }
        catch(Exception e) {}
    }

    private void waitViewShown(Matcher<View> matcher) {
        IdlingResource idlingResource = new ViewShownIdlingResource(matcher);///
        try {
            IdlingRegistry.getInstance().register(idlingResource);
            onView(matcher).check(matches(isDisplayed()));
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}

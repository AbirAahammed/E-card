package redbull.ecard.util;

import redbull.ecard.DataLayer.testData.testID;
import redbull.ecard.MainActivity;
import redbull.ecard.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class testContent {
    public  void signUp_test_success(){};
    public  void signUp_test_fail(){};
    public void loginActivityTest_success(){

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
}

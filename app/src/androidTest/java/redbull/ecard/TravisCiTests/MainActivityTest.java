package redbull.ecard.TravisCiTests;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;


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

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static redbull.ecard.util.testWithHWAcceration.waitTime;

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

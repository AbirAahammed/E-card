package redbull.ecard.local.UITest;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.R;
import redbull.ecard.UILayer.loginActivity.LoginActivity;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testData.testID;
import redbull.ecard.util.testWithHWAcceration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ModifyProfile {
    private String curr_email,password,curr_des,uid;
    @Rule
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Before
    public void setup(){
        curr_email=testID.email;
        password= testID.password;
        curr_des="";
        testID.email="Demo2@gmail.com";
        testID.password="123456";
    }
    @Test
    public void modify() { //this will include view Profile, view added Card and camera usages


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
        onView(withId(R.id.navigation_dashboard)).perform(click());
        onView(withId(R.id.descriptionInput)).perform(replaceText("yes"),closeSoftKeyboard());
        onView(withId(R.id.save_changes)).perform(click());
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProfileLogic profileLogic = ProfileLogic.getInstance();
        profileLogic.getProfile(uid).addOnProfileGetListener(new OnProfileGetListener() {
            @Override
            public void onSuccess(@NonNull Profile profile) {
                assertEquals(profile.getDescription(),"yes");
            }
            @Override
            public void onProfileNotFound() {
                fail("failed to retrieve profile");

            }
            @Override
            public void onFailure() {
                fail("failed to create profile");
            }
        });
    }
    @After
    public void clean(){
        onView(withId(R.id.descriptionInput)).perform(replaceText(curr_des),closeSoftKeyboard());
        onView(withId(R.id.save_changes)).perform(click());
        testID.email=curr_email;
        testID.password=password;
        testContent clean= new testContent();
        clean.removeFromDB(uid);
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        FirebaseAuth.getInstance().signOut();
    }
}

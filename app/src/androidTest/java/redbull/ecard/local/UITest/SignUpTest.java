package redbull.ecard.local.UITest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import redbull.ecard.MainActivity;
import redbull.ecard.R;
import redbull.ecard.UILayer.loginActivity.LoginActivity;
import redbull.ecard.util.testData.testID;
import redbull.ecard.util.testContent;
import redbull.ecard.util.testWithHWAcceration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    private LoginActivity Signup;
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);
    @Before
    public void setup(){
        Signup= intentsTestRule.getActivity();
    }
    @Test
    public void SignUp_success() {
        try {
            onView(withId(R.id.username)).perform(closeSoftKeyboard());
            try {

                onView(withId(R.id.button_register)).perform(scrollTo());
                Thread.sleep(2000L);
            } catch (Exception e) {
            }
            ;
            onView(withId(R.id.button_register)).perform(click());
            testContent follow = new testWithHWAcceration();
            follow.signUp_test_success();
            intended(hasComponent(MainActivity.class.getName()));
        }catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        finally {
            remove();
            FirebaseAuth.getInstance().signOut();
        }

    }
    @Test
    public void SignUp_hasAccount() {
        try {
            onView(withId(R.id.username)).perform(closeSoftKeyboard());
            try {

                onView(withId(R.id.button_register)).perform(scrollTo());
                Thread.sleep(2000L);
            } catch (Exception e) {
            }
            onView(withId(R.id.button_register)).perform(click());
            testWithHWAcceration follow = new testWithHWAcceration();
            follow.signUp_test_fail();
            intended(hasComponent(MainActivity.class.getName()));
        }catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        finally {
            remove();
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void remove(){
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();;
        if(user == null){
            return ;
        }
        if(user.getEmail().equals("tlw@gmail.com")){
            return ;
        }
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(testID.signup_email, testID.password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("SIGN up test", "User account deleted.");
                                        }
                                    }
                                });

                    }
                });

    }

}

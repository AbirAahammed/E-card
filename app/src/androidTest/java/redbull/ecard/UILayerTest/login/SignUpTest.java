package redbull.ecard.UILayerTest.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import redbull.ecard.MainActivity;
import redbull.ecard.R;
import redbull.ecard.UILayer.login.LoginActivity;
import redbull.ecard.testData.testID;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SignUpTest {
    private final int email_id=2131230902;
    private final int name=2131231038;
    private final int password= 2131231065;
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);
    @Test
    public void SignUp_success() {

        onView(withId(R.id.button_register)).perform(click());
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        onView(withId(email_id)).perform(typeText(testID.signup_email),closeSoftKeyboard());
        onView(withText("NEXT")).perform(click());
        onView(withId(name)).perform(typeText(testID.signup_name),closeSoftKeyboard());
        onView(withId(password)).perform(typeText(testID.password),closeSoftKeyboard());
        onView(withText("SAVE")).perform(click());
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        intended(hasComponent(MainActivity.class.getName()));
    }
    @Test
    public void SignUp_hasAccount() {
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(email_id)).perform(typeText(testID.email),closeSoftKeyboard());
        onView(withText("NEXT")).perform(click());
        onView(withId(password)).perform(typeText(testID.password),closeSoftKeyboard());
        onView(withText("SIGN IN")).perform(click());
        try {
            Thread.sleep(2000L);
        }catch (Exception e){

        }
        intended(hasComponent(MainActivity.class.getName()));
    }
    @After
    public void remove(){
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

package redbull.ecard.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }
    public void removeFromDB(String uid){
        System.out.println(uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("profiles").child(uid).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                System.out.println("clean up done");
            }
        });
    }
}



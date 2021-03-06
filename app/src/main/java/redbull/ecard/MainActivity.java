package redbull.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.LogicLayer.RunnableCallBack;
import redbull.ecard.UILayer.loginActivity.LoginActivity;
import redbull.ecard.UILayer.signup.SignUpActivity;
import redbull.ecard.LogicLayer.Listeners.*;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG = "MainActivity";

    // An extra call back to execute
    // This is called if the database is loading slow, to fill in details of a profile
    public static RunnableCallBack excessCallBacks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        ProfileLogic.getInstance().getProfile(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnProfileGetListener(new OnProfileGetListener() {
            @Override
            public void onSuccess(@NonNull Profile profile) {
                Log.i(TAG, profile.toString());
            }

            @Override
            public void onProfileNotFound() {
                Log.i(TAG, "profile not found");
                startSignUpActivity();
            }

            @Override
            public void onFailure() {
                Log.i(TAG, "Failed");
                startSignUpActivity();

            }
        });
//        uncomment this if you want to force invoke this
//        startActivity(new Intent(this, SignUpActivity.class));

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    /**
     * Used to land in login page
     * @param
     * @return
     * */
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    // TODO Implement a conditional in the oncreate of this class, try to get the entry from realtime
    //  db if you see there is none, then call this method, this will open up the window and let the \
    //  user sign up
    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onDestroy() {
        FirebaseAuth.getInstance().signOut();
        super.onDestroy();
    }
    /**
     * Used to logout from the app
     * @param item that will call this method
     * @return
     * */
    public void logOut(MenuItem item) {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // Remove the currently signed in profile from our cache
                            CardDatabaseConnector.setCurrentProfile(null);

                            startLoginActivity();
                        } else {
                            Log.e(TAG, "onComplete : ", task.getException());
                        }
                    }
                });

//        replacement of AuthUI
//        FirebaseAuth.getInstance().signOut();

    }


}
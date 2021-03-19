package redbull.ecard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.UILayer.cards.CardGenerator;
import redbull.ecard.UILayer.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG = "MainActivity";

    // Store a reference to the layout of the cards section, for dynamic adding
    private LinearLayout cardParent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.card_body);
        cardParent = (LinearLayout)findViewById(R.id.cards_layout);

        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }

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
    @Override
    public void onPause() {
        FirebaseAuth.getInstance().signOut();
        super.onPause();
    }
    @Override
    public void onResume() {
        //TODO: re-login
        super.onResume();
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
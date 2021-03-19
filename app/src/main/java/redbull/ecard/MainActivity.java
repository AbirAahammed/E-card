package redbull.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("CREATION", "A");
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }
       // Log.d("CREATION", "Ab");
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

        //(String serviceName, Name name, Contact contact, Address address, String description, int templateNum)
        Card testCard = new Card
                ("", new Name("tim", "doh ", "jim"),
                        new Contact("123", "123", "123"),
                        new Address(), "We like stuff", 0);

        // Append the test card
        //CardGenerator.InsertToView(testCard, (ScrollView)findViewById(R.id.scrollView2), this);
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
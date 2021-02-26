package redbull.ecard.UILayer.cards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.FirebaseDatabase;

import redbull.ecard.PersistenceLayer.ProfilePersistence;
import redbull.ecard.R;

public class CardsFragment extends Fragment {

    private CardsViewModel cardsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD:app/src/main/java/redbull/ecard/UILayer/home/HomeFragment.java

//        Delete the following after testing
        ProfilePersistence pp = new ProfilePersistence(FirebaseDatabase.getInstance());
        pp.read(123456767L);
        Log.d("ABIRDEVTAG", "Testing DB call");


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
=======
        cardsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);
>>>>>>> Committing recent changes:app/src/main/java/redbull/ecard/UILayer/cards/CardsFragment.java
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        cardsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package redbull.ecard.UILayer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.LogicLayer.ShareLogic;
import redbull.ecard.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // remove the following code segment before submitting
        ProfileLogic profileLogic = ProfileLogic.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Profile profile = new Profile(
                new Name("Abir", "Sakib", "Ahammed"),
                new Contact("123 456 7891", "987 654 3210", "redbull.com"),
                new Address("12", "21", "Q3E 2R4", "Winnipeg", "MB", "CANADA"));
        profileLogic.createProfile(profile);
        profileLogic.getProfile(uid).addOnProfileGetListener(new OnProfileGetListener() {
            @Override
            public void onSuccess(@NonNull Profile profile) {
                ShareLogic shareLogic = ShareLogic.getInstance(profile);
                shareLogic.getConnections();

            }

            @Override
            public void onFailure() {

            }
        });

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
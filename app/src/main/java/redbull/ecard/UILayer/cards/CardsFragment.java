package redbull.ecard.UILayer.cards;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.R;

public class CardsFragment extends Fragment {
    static Card[] cardsCache = null; // Cache the cards from the fragment to prevent further database accesses

    private CardsViewModel cardsViewModel;

    // This is called every time the 'cards' section is opened
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment_cards.xml file
        View root = inflater.inflate(R.layout.fragment_cards, container, false);

        // Append all cards to the Cards section from the database
        if (cardsCache == null) {

            // Grab the cards
            cardsCache = (new CardDatabaseConnector ()).GrabCardInstances();

            // Insert them into the view
            CardGenerator.InsertToView(cardsCache, root, inflater, getContext());
        }
        else
        {
            // Use the cache instead of grabbing from the database again
            CardGenerator.InsertToView(cardsCache, root, inflater, getContext());
        }

        cardsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);

//        final TextView textView = root.findViewById(R.id.text_home); // text_home doesn't exist. Never used.
        cardsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });

        return root;
    }
}
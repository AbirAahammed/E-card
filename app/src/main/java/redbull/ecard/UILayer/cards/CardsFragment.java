package redbull.ecard.UILayer.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import redbull.ecard.DataLayer.Card;
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

        addScrollListener((ScrollView)root.findViewById(R.id.cards_scroll));

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

    // Add a listener to the scroll view
    // This will store the offset of the Y of the scroll view in fragment cards
    // The purpose is so that I can track the offset, so when cards are enlarged, the scroll view can be adjusted
    void addScrollListener(ScrollView scroll)
    {
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN)
                {

                    return true;
                }

                return false;
            }
        });
    }
}
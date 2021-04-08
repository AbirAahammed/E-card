package redbull.ecard.UILayer.cards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.R;
import redbull.ecard.LogicLayer.RunnableCallBack;
import redbull.ecard.UILayer.Profile.ProfileFragment;

public class CardsFragment extends Fragment {
    private CardsViewModel cardsViewModel;
    View rootView;
    LayoutInflater rootInflater;

    // This is called every time the 'cards' section is opened
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment_cards.xml file
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        rootInflater = inflater;

        // Append all cards to the Cards section from the database
        if (!CardDatabaseConnector.profileIsCached()) {

            // This portion is quite complicated
            // Inserts profileFetchCallBackSuccess and profileFetchCallBackFailure
            // As the function to call when the data is being returned
            // The order of these in the list matters!
            ArrayList<RunnableCallBack> callBackSuccesses = new ArrayList<RunnableCallBack>();
            callBackSuccesses.add( () -> profileFetchCallBackSuccess());
            callBackSuccesses.add( () -> ProfileFragment.profileDisplaySetup());

            ArrayList<RunnableCallBack> callBackFailures = new ArrayList<RunnableCallBack>();
            callBackFailures.add( () -> profileFetchCallBackFailure());


            // Play loading animation while we wait for a callback
            rootView.findViewById(R.id.loadingRotation).startAnimation(
                    AnimationUtils.loadAnimation(getActivity(), R.anim.rotation)
            );

            new CardDatabaseConnector (callBackSuccesses, callBackFailures).fetchProfileInformation ();
        }
        else
        {
            // Skip the database accesses and just grab the cached profile
            setupUIForCardsList(CardDatabaseConnector.getCachedUserProfile());
        }

        return rootView;
    }

    // This method is called when database retrieval is successful
    private void profileFetchCallBackSuccess()
    {
        Log.d ("connection", "Getting 1.5");
        // Now we will fetch the users cards list, as we now have their profile saved in the database connector

        // Again, a bit complicated, but we are setting our OnSuccess and OnFailure call backs here
        ArrayList<RunnableCallBack> listSuccesses = new ArrayList<RunnableCallBack>();
        listSuccesses.add( () -> cardListFetchCallBackSuccess());

        ArrayList<RunnableCallBack> listFailures = new ArrayList<RunnableCallBack>();
        listFailures.add( () -> cardListFetchCallBackFailure());

        // Fetch the connections from the database and pass our success and failure callback methods
        new CardDatabaseConnector (listSuccesses, listFailures).fetchConnectionsList ();
    }

    // This method is called when database retrieval fails
    private void profileFetchCallBackFailure()
    {
        // TODO could not grab information about the profile from the database
    }

    // The callback method when fetching the list of connections is successful
    // The connections should be fetched to the database, and then added to the profiles list of connections
    private void cardListFetchCallBackSuccess()
    {
        // Remove & clear the loading bar
        View loadingView = rootView.findViewById(R.id.loadingRotation);
        loadingView.clearAnimation();
        loadingView.setVisibility(View.INVISIBLE);

        // Success!!!
        // Now, we can finally setup all the UI
        Profile ourProfile = (new CardDatabaseConnector()).GetActiveUser(); // This cant be null, wouldn't make sense

        Log.d ("connection", "Setting up view with the connections...");
        CardGenerator.insertToView(ourProfile.getConnections(), rootView, rootInflater, getContext()); // Cards fragment setup
    }

    private void cardListFetchCallBackFailure()
    {
        // TODO could not grab information about the users connections from the database
    }

    // Setups up the UI for the users profile
    private void setupUIForCardsList(Profile myProfile)
    {
        Log.d ("connection", "Settings up profiles card list...");

        CardGenerator.insertToView(myProfile.getConnections(), rootView, rootInflater, getContext());
    }
}
package redbull.ecard.LogicLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnConnectionsGetListener;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;

// This class grabs the cards from the database or data-layer
// Essentially, its the connection between the database and the ui layer
public class CardDatabaseConnector {

    static Profile cachedProfile;
    static boolean fetching;

    // Function to execute and return the information to when the data is fetched
    ArrayList<RunnableCallBack> successes;
    ArrayList<RunnableCallBack> failures;

    // CallBack functions
    // arr[0] = success
    // arr[1] = failure
    public CardDatabaseConnector(ArrayList<RunnableCallBack> callBackSuccess, ArrayList<RunnableCallBack> callBackFailure) {
        if (successes == null)
        {
            successes = new ArrayList<RunnableCallBack>();
        }
        if (failures == null)
        {
            failures = new ArrayList<RunnableCallBack>();
        }

        /*
        for (RunnableCallBack i : callBackSuccess)
        {
            successes.add (i);
        }

        for (RunnableCallBack i : callBackFailure)
        {
            failures.add(i);
        }
         */
        this.successes = callBackSuccess;
        this.failures = callBackFailure;
    }

    public CardDatabaseConnector(){}

    // Fetches the profile from the database, and then caches the result
    // Returns the profile if the profile is already cached, otherwise, it will call the corresponding callback function
    public Profile fetchProfileInformation()
    {
        if (cachedProfile == null) {
            ProfileLogic logic = ProfileLogic.getInstance().getProfile(FirebaseAuth.getInstance().getUid());

            //logic.enableLocalPersistence();
            logic.addOnProfileGetListener(new OnProfileGetListener() {
                @Override
                public void onSuccess(@NonNull Profile profile) {
                    Log.d("test", "Fetching cards");
                    Log.d("test", profile.getUID());
                    Log.d("test", profile.getContact().getEmailAddress());
                    cachedProfile = profile;

                    if (successes != null) {
                        for (int i = 0; i < successes.size(); i++)
                            successes.get(i).run();
                    }

                    successes.clear();
                }

                @Override
                public void onProfileNotFound() {
                    // Empty
                }

                @Override
                public void onFailure() {
                    Log.d("test", "Failed to fetch cards");

                    if (failures != null) {
                        for (int i = 0; i < failures.size(); i++)
                            failures.get(i).run();
                    }

                    failures.clear();
                }
            });
        }

        return cachedProfile;
    }

    // Initialize the connects for the profile
    public void initConnectionsOfProfile(ArrayList<Profile> profiles, int index)
    {
        Log.d("fetch", "Profiles to fill: " + profiles.size());
        Log.d("fetch", "current profile being filled: " + index);
        if (index >= profiles.size())
        {
            // We have successfully initialized all the connections for this profile
            if (successes != null) {
                for (int i = 0; i < successes.size(); i++)
                    successes.get(i).run();
            }

            successes.clear();
        }

        else
        {
            Log.d("fetch", "We are fetching the profile information..");

            // Grab the info for the current profiles index
            ProfileLogic.getInstance().getProfile(profiles.get(index).getUID()).addOnProfileGetListener(new OnProfileGetListener() {
                @Override
                public void onSuccess(@NonNull Profile profile) {

                    Log.d("fetch", "Got the profile: " + profile.getName().toString());

                    // We got the connection for this profile
                    // Replace the old value that only had the UID with the actual profiles information
                    cachedProfile.getConnections().set(index, profile);

                    Log.d("test", "profile: " + profile.getContact().toString());

                    // Fetch the next profile in this connection
                    initConnectionsOfProfile(profiles, index + 1);
                }

                @Override
                public void onProfileNotFound() {
                    // Empty
                }

                @Override
                public void onFailure() {
                    Log.d("test", "Failed to fetch cards");

                    // Failed to fetch callback
                    if (failures != null) {
                        for (int i = 0; i < failures.size(); i++)
                            failures.get(i).run();
                    }

                    failures.clear();
                }
            });
        }
    }

    public void fetchConnectionsList()
    {
        Log.d ("tessss", "Fetching the connections..");
        if (cachedProfile == null)
        {
            // TODO create a custom exception
            // Throw an exception
            // You should call fetchProfileExceptions first, otherwise there is no profile to insert the connections to
            Log.d ("tessss", "ERROR: no cached profile exists. Please fetch the profile first.");
            return;
        }

        else
        {
            Log.d ("tessss", "Grabbing the conects..");
            Log.d ("tessss", "" + cachedProfile.getConnections().size());
            Log.d ("tessss", "" + cachedProfile.hasFetchedConnections());
            if (!cachedProfile.hasFetchedConnections())
            {
                Log.d ("tessss", "This should executed");

                ShareLogic instance = ShareLogic.getInstance(cachedProfile);
                instance.getConnections().addOnConnectionsGetListener(new OnConnectionsGetListener() {

                    @Override
                    public void onAllReadSuccess(@NonNull ArrayList<Profile> profiles) {
                        // The array of connections
                        Log.d ("tessss", "dwadwadwdwadwadwadwadwadwa");
                        if (cachedProfile == null)
                        {
                            // Throw an exception
                            Log.d ("tessss", "No cached profile exists");
                            return;
                        }

                        Log.d ("tessss", "the size of the callback"  + profiles.size());
                        Log.d ("tessss", "1size of the callback: "  + profiles.get(0).getContact().toString());

                        cachedProfile.setConnections(profiles);
                        initConnectionsOfProfile (profiles, 0);
                    }

                    @Override
                    public void onSuccess(@NonNull Profile profile) {
                        // Empty
                    }

                    @Override
                    public void onFailure() {
                        Log.d("test", "Failed to fetch cards");

                        if (failures != null) {
                            for (int i = 0; i < failures.size(); i++)
                                failures.get(i).run();
                        }

                        failures.clear();
                    }
                });
            }
        }
    }

    // Grab the information associated with the currently logged in user
    // Retrieve this information from the database
    // If this information is not cached, it will fetch the information first.
    public Profile GetActiveUser()
    {
        if (cachedProfile == null)
        {
            fetchProfileInformation();
            return null;
        }

        return cachedProfile;
    }

    // Update profile
    // Add information to the database
    // If the profile already exists, the information is overrided
    public void ServiceUpdate(Profile profile)
    {
        // TODO
        // Please complete this method
    }

    // The user has updated the template they would like to use for displaying their profile
    // Update the database with the new information
    public void TemplateUpdate(int newTemplateFormat)
    {
        // This is currently incomplete on the UI Side
        // 'newTemplateFormat' contains the new template for the user

        // A template is represented as an integer.
        // This template will be used to display someones card a specific way for someone who has scanned it
    }

    // Save the profile to our list of saved profiles
    // Update the database with the new information
    public void SavedProfile(Profile profile)
    {
        // This is currently incomplete on the UI Side
        // When a QR code is scanned, this method will be called to save the profile that was scanned to the current user

        // This way if they log out, they still have the card saved that they have previously scanned
        // Note that, this is connected to the GrabCardInstances() call, since a saved profile will be added to that list of returned items.
    }

    // Returns whether or not we have the profile cached
    public static boolean profileIsCached()
    {
        return cachedProfile != null;
    }

    // Returns the profile associated with the user, if any is cached, without calling the database (NULL) = none
    public static Profile getCachedUserProfile()
    {
        return cachedProfile;
    }

    // Update the currently cached profile
    public static void SetCurrentProfile(Profile profile)
    {
        cachedProfile = profile;
    }
}

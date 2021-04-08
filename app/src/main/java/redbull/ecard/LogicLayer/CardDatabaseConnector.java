package redbull.ecard.LogicLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnConnectionsGetListener;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;

// This class grabs the cards from the database or data-layer
// Essentially, its the connection between the database and the ui layer
public class CardDatabaseConnector {

    static Profile cachedProfile;
    static Profile scannerProfile;
    static boolean fetching;

    // Function to execute and return the information to when the data is fetched
    ArrayList<RunnableCallBack> successes;
    ArrayList<RunnableCallBack> failures;

    // Setting up with CallBack functions
    public CardDatabaseConnector(ArrayList<RunnableCallBack> callBackSuccess, ArrayList<RunnableCallBack> callBackFailure) {
        if (successes == null)
        {
            successes = new ArrayList<RunnableCallBack>();
        }
        if (failures == null)
        {
            failures = new ArrayList<RunnableCallBack>();
        }

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

            logic.addOnProfileGetListener(new OnProfileGetListener() {
                @Override
                public void onSuccess(@NonNull Profile profile) {
                    cachedProfile = profile;

                    if (successes != null) {
                        for (int i = 0; i < successes.size(); i++)
                            successes.get(i).run();

                        successes.clear();
                    }
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

                        failures.clear();
                    }
                }
            });
        }

        return cachedProfile;
    }
    public Profile fetchScannerProfileInformation(String uid)
    {
            ProfileLogic logic = ProfileLogic.getInstance().getProfile(uid);

            //logic.enableLocalPersistence(); <--- FIXME doesn't work?
            logic.addOnProfileGetListener(new OnProfileGetListener() {
                @Override
                public void onSuccess(@NonNull Profile profile) {
                    Log.d("test", "Fetching cards");
                    Log.d("test", profile.getUID());
                    Log.d("test", profile.getContact().getEmailAddress());
                    scannerProfile = profile;

                    if (successes != null) {
                        for (int i = 0; i < successes.size(); i++)
                            successes.get(i).run();

                        successes.clear();
                    }
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

                        failures.clear();
                    }
                }
            });

        return scannerProfile;
    }

    // Initialize the connections for the profile
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

                successes.clear();
            }
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

                        failures.clear();
                    }
                }
            });
        }
    }

    public void fetchConnectionsList()
    {
        Log.d ("connection", "Fetching the connections..");
        if (cachedProfile == null)
        {
            // TODO create a custom exception
            // Throw an exception
            // You should call fetchProfileExceptions first, otherwise there is no profile to insert the connections to
            return;
        }

        else
        {
            if (!cachedProfile.hasFetchedConnections())
            {
                ShareLogic instance = ShareLogic.getInstance(cachedProfile);
                instance.getConnections().addOnConnectionsGetListener(new OnConnectionsGetListener() {

                    @Override
                    public void onAllReadSuccess(@NonNull ArrayList<Profile> profiles) {
                        // The array of connections
                        if (cachedProfile == null)
                        {
                            // Throw an exception
                            Log.d ("connection", "No cached profile exists");
                            return;
                        }

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

                            failures.clear();
                        }
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
    // If the profile already exists, the information is overided
    // The profile should also be added to the list of cards, without an excess database call
    public void profileUpdate()
    {
        if (cachedProfile == null)
            return;

        // Update the current users profile information
        ProfileLogic.getInstance().createProfile(cachedProfile);
    }

    // Update the description of the cached profile
    public void updateDescription(String description)
    {
        if (cachedProfile == null)
            return;

        cachedProfile.setDescription(description);
    }

    public void updateService(String service)
    {
        if (cachedProfile == null)
            return;

        cachedProfile.setService(service);
    }

    public void updateContact (Contact contact)
    {
        if (cachedProfile == null)
            return;

        cachedProfile.setContact(contact);
    }

    public void updateHouseAddress (String roadAddress)
    {
        if (cachedProfile == null)
            return;

        cachedProfile.updateHouseAddress(roadAddress);
    }

    // The user has updated the template they would like to use for displaying their profile
    // Update the database with the new information
    public void templateUpdate(int newTemplateFormat)
    {
        // 'newTemplateFormat' contains the new template for the user
        Profile.setViewedTemplate(newTemplateFormat);
    }

    // Returns the template associated with the displayed profile
    public int fetchTemplate()
    {
        return Profile.getViewedTemplate();
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
    public static void setCurrentProfile(Profile profile)
    {
        cachedProfile = profile;
    }
    public static Profile getScannerProfile(){
        return scannerProfile;
    }
}

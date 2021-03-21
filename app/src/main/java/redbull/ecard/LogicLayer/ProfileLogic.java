package redbull.ecard.LogicLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnProfileGetListener;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;
import redbull.ecard.PersistenceLayer.ProfilePersistence;


public class ProfileLogic extends Logic{
    private static final  String TAG = "ProfileLogic";
    private ProfilePersistence profilePersistence;
    private OnProfileGetListener profileGetListener;
    private ProfileLogic(ProfilePersistence profilePersistence) {
        this.profilePersistence = profilePersistence;
    }
    public static ProfileLogic getInstance(){
        return new ProfileLogic(ProfilePersistence.getInstance());
    }

    public void createProfile(Profile profile) {
        this.profilePersistence.create(profile);
    }
    public void enableLocalPersistence(){
        profilePersistence.setLocalPersistenceEnabled(true);
    }
    public void disableLocalPersistence(){
        profilePersistence.setLocalPersistenceEnabled(false);
    }

    public void enableLocalPersistenceRefresh(){
        profilePersistence.setPersistenceSync(true);
    }
    public void disableLocalPersistenceRefresh(){
        profilePersistence.setPersistenceSync(false);
    }

/// return:
    public ProfileLogic getProfile(String uid) {
        ((ProfilePersistence)this.profilePersistence.read(uid)).addOnProfileReadCompleteListener(new OnReadCompleteListener() {
            @Override
            public void onSuccess(@NonNull Model model) {
                if (model instanceof Profile) {
                    Log.d(TAG, ((Profile)model).toString());
                    if (profileGetListener != null) {
                        profileGetListener.onSuccess((Profile) model);
                    } else {
                        Log.d(TAG, "Null listener given");
                    }
                }
            }


            @Override
            public void onFailure() {
                Log.d(TAG, "No data was retrieved");
            }
        });
        return this;
    }


    public void addOnProfileGetListener(OnProfileGetListener onProfileGetListener) {
        this.profileGetListener = onProfileGetListener;
    }
}

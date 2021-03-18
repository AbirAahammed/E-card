package redbull.ecard.LogicLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.Listeners.OnConnectionsGetListener;
import redbull.ecard.PersistenceLayer.ConnectionPersistence;
import redbull.ecard.PersistenceLayer.Listeners.OnConnectionReadCompleteListener;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;

public class ShareLogic extends Logic{
    private static final String TAG = "ShareLogic";

    private ConnectionPersistence connectionPersistence;
    private OnConnectionsGetListener connectionsGetListener;
    private OnConnectionReadCompleteListener onConnectionReadCompleteListener;

    private Profile profile;
    private ShareLogic(ConnectionPersistence connectionPersistence, Profile profile) {
        this.connectionPersistence = connectionPersistence;
        this.profile = profile;
    }

    public static ShareLogic getInstance(Profile profile) {
        return new ShareLogic(ConnectionPersistence.getInstance(profile), profile);
    }

    public void createConnection(String uid) {
        this.connectionPersistence.create(uid);
    }

    public ShareLogic getConnections() {
        ArrayList<Profile> profiles = new ArrayList<>();
        ((ConnectionPersistence)this.connectionPersistence.read()).addOnCompleteListener(new OnReadCompleteListener() {
            @Override
            public void onSuccess(@NonNull Model model) {
                if (connectionsGetListener != null)
                    connectionsGetListener.onSuccess((Profile)model);
                profiles.add((Profile)model);
                if (profiles.size() == profile.getConnections().size() && connectionsGetListener != null){
                    connectionsGetListener.onAllReadSuccess(profiles);
                }
            }

            @Override
            public void onFailure() {

            }
        });
        return this;
    }

    public void addOnConnectionsGetListener(OnConnectionsGetListener connectionsGetListener) {
        this.connectionsGetListener = connectionsGetListener;
    }
}

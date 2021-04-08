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
    private ArrayList<RunnableCallBack> onSuccess;
    private ArrayList<RunnableCallBack> onFailure;

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

    public void setConnectionsCallBack(ArrayList<RunnableCallBack> success, ArrayList<RunnableCallBack> failure)
    {
        this.onSuccess = success;
        this.onFailure = failure;
    }

    public ShareLogic getConnections() {
        ArrayList<Profile> profiles = new ArrayList<>();
        ((ConnectionPersistence)this.connectionPersistence.read()).addOnCompleteListener(new OnReadCompleteListener() {
            @Override
            public void onSuccess(@NonNull Model model) {
                if (connectionsGetListener != null)
                    connectionsGetListener.onSuccess((Profile)model);

                profiles.add((Profile)model);
                profile.fetchedCon();
                if (profiles.size() == profile.getConnections().size() && connectionsGetListener != null){
                    connectionsGetListener.onAllReadSuccess(profiles);
                }

                Log.d ("con", "Getting there");
                if (onSuccess != null) {
                    for (int i = 0; i < onSuccess.size(); i++)
                            onSuccess.get(i).run();

                    onSuccess.clear();
                }
            }

            @Override
            public void onProfileNotFound() {
                Log.i(TAG, "This id is invalid");
            }

            @Override
            public void onFailure() {
                Log.d ("connection", "Getting thereFAIL");
                if (onFailure != null) {
                    for (int i = 0; i < onFailure.size(); i++)
                        onFailure.get(i).run();

                    onFailure.clear();
                }
            }
        });

        return this;
    }

    public void addOnConnectionsGetListener(OnConnectionsGetListener connectionsGetListener) {
        this.connectionsGetListener = connectionsGetListener;
    }
}

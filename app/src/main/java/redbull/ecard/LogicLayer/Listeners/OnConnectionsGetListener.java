package redbull.ecard.LogicLayer.Listeners;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Profile;

public interface OnConnectionsGetListener {
    void onSuccess(@NonNull Profile profile);
    void onAllReadSuccess(@NonNull ArrayList<Profile> profiles);
    void onFailure();
}

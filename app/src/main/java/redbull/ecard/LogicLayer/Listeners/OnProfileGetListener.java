package redbull.ecard.LogicLayer.Listeners;

import androidx.annotation.NonNull;

import redbull.ecard.DataLayer.Profile;

public interface OnProfileGetListener {
    void onSuccess(@NonNull Profile profile);
    void onFailure();
}

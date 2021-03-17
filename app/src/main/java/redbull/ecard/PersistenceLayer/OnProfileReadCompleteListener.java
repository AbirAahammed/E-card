package redbull.ecard.PersistenceLayer;

import androidx.annotation.NonNull;

import redbull.ecard.DataLayer.ProfileDBO;

public interface OnProfileReadCompleteListener {
    void onSuccess(@NonNull ProfileDBO var1);
    void onFailure();
}















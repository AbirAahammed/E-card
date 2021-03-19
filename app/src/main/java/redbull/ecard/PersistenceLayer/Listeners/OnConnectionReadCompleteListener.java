package redbull.ecard.PersistenceLayer.Listeners;

import androidx.annotation.NonNull;

import redbull.ecard.DataLayer.Model;

public interface OnConnectionReadCompleteListener {
    void onSuccess(@NonNull Model model);
    void onFailure();
}

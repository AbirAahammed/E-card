package redbull.ecard.PersistenceLayer.Listeners;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import redbull.ecard.DataLayer.Model;

public interface OnReadCompleteListener {
    void onSuccess(@NonNull Model model);
    void onFailure();
}















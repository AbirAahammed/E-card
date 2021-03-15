package redbull.ecard.PersistenceLayer;

import android.content.AsyncTaskLoader;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.ProfileDBO;

public class ProfilePersistence extends Persistence {
    private static final String TABLENAME  = "Profile";

    private ProfileDBO profileDBO;
    public ProfilePersistence(FirebaseDatabase firebaseDatabaseInstance) {
        super(firebaseDatabaseInstance, TABLENAME);
        profileDBO = new ProfileDBO();
    }


    @Override
    public void create(Model model) {
        // do a check before casting
        if (model instanceof ProfileDBO){
            ProfileDBO profile = (ProfileDBO) model;
            dbTableRef.child(Long.toString(profile.getID())).child("uid").setValue(profile.getUid());
            dbTableRef.child(Long.toString(profile.getID())).child("name").setValue(profile.getName());
            dbTableRef.child(Long.toString(profile.getID())).child("contact").setValue(profile.getContact());
            dbTableRef.child(Long.toString(profile.getID())).child("address").setValue(profile.getAddress());
        }
        else {
            String ERROR = "Expected a profile class";
            throw new ClassCastException(ERROR);
        }
    }

    @Override
    public ProfileDBO read(Long id) {
        final ProfileDBO[] profileDBO = {new ProfileDBO()};
        
       dbTableRef.child(id.toString()).get().addOnCompleteListener(task -> {
           if (!task.isSuccessful()) {
               Log.e("firebase", "Error getting data", task.getException());
           }
           else {
               Log.d("ABIRTAG", String.valueOf(task.getResult().getValue()));
               String json = String.valueOf(task.getResult().getValue());
               try {
                   profileDBO[0] = profileDBO[0].map(json);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
        return null;
    }

    @Override
    public void update(Model model) {

    }

    @Override
    public void delete(Long id) {

    }
}

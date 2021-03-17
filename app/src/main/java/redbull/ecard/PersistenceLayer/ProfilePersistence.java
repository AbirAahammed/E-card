package redbull.ecard.PersistenceLayer;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.ProfileDBO;


public class ProfilePersistence extends Persistence {
    private static final String TABLENAME  = "Profile";
    private OnProfileReadCompleteListener readListener;
    public ProfilePersistence(FirebaseDatabase firebaseDatabaseInstance) {
        super(firebaseDatabaseInstance, TABLENAME);
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
    public PersistenceInterface read(Long id) {
        final ProfileDBO profileDBO = new ProfileDBO();
        dbTableRef.child(id.toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                profileDBO.map((HashMap<String, Object>) task.getResult().getValue());
                readListener.onSuccess(profileDBO);
            }
        });
        return this;
    }

    public void addOnProfileReadCompleteListener(OnProfileReadCompleteListener onProfileReadCompleteListener){
        this.readListener = onProfileReadCompleteListener;
    }
    @Override
    public void update(Model model) {

    }

    @Override
    public void delete(Long id) {

    }
}

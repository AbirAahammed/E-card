package redbull.ecard.PersistenceLayer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import kotlin.NotImplementedError;
import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.PersistenceLayer.Listeners.OnConnectionReadCompleteListener;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;

public class ConnectionPersistence implements PersistenceInterface {
    private static final String TAG = "ConnectionPersistence";
    private OnReadCompleteListener readListener;
    private OnConnectionReadCompleteListener connectReadListener;
    private static final String TABLENAME  = "profiles";
    private static final String CONNECTION  = "connections";
    private FirebaseDatabase firebaseDatabaseInstance;
    private DatabaseReference dbTableRef;
    private Profile profile;
    private static long maxid=0L;
    public ConnectionPersistence(FirebaseDatabase firebaseDatabaseInstance, Profile profile) {
        this.firebaseDatabaseInstance = firebaseDatabaseInstance;
        this.dbTableRef = firebaseDatabaseInstance.getReference(TABLENAME);
        this.profile = profile;

    }
    public static ConnectionPersistence getInstance(Profile profile) {

        return new ConnectionPersistence(FirebaseDatabase.getInstance(), profile);
    }

    public void create(String uid) {
        this.dbTableRef.child(profile.getUID()).child(CONNECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                    Log.i("Connection",""+maxid);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        TODO Check if the id exists here
        this.dbTableRef.child(profile.getUID()).child(CONNECTION).child(String.valueOf(maxid)).setValue(uid);
    }
    @Override
    public void create(Model model) {
        throw new NotImplementedError();
    }

    @Override
    public PersistenceInterface read(String uid) {
        throw new NotImplementedError();
    }

    public PersistenceInterface read() {
        for (int i = 0; i < profile.getConnections().size(); i++) {
            Profile connection = new Profile(profile.getConnections().get(i).getUID());
            this.dbTableRef.child(profile.getConnections().get(i).getUID()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    connection.mapConnection((HashMap<String, Object>) task.getResult().getValue());
                    readListener.onSuccess(connection);
                }
            });
        }
        return this;
    }

    public void addOnCompleteListener(OnReadCompleteListener onReadCompleteListener) {
        this.readListener = onReadCompleteListener;
    }

    public void addOnConnectionReadCompleteListener(OnConnectionReadCompleteListener onConnectionReadCompleteListener){
        this.connectReadListener = onConnectionReadCompleteListener;
    }
    @Override
    public void update(Model model) {

    }

    @Override
    public void delete(Long id) {

    }
}

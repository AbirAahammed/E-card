package redbull.ecard.PersistenceLayer;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import redbull.ecard.DataLayer.Model;

public abstract class Persistence implements PersistenceInterface {
    protected FirebaseDatabase firebaseDatabaseInstance;
    protected DatabaseReference dbTableRef;

    public Persistence() {
    }

    public Persistence(FirebaseDatabase firebaseDatabaseInstance, String tableName) {
        this.firebaseDatabaseInstance = firebaseDatabaseInstance;
        this.dbTableRef = this.firebaseDatabaseInstance.getReference(tableName);
    }
}

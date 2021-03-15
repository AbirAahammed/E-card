package redbull.ecard.PersistenceLayer;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Name;

public class NamePersistence extends Persistence{
    private static final String TABLENAME  = "Name";


    public NamePersistence(FirebaseDatabase firebaseDatabaseInstance) {
        super(firebaseDatabaseInstance, TABLENAME);

    }

    @Override
    public void create(Model model) {

    }

    @Override
    public Model read(Long id) {
        return null;
    }

    @Override
    public void update(Model model) {

    }

    @Override
    public void delete(Long id) {

    }
}

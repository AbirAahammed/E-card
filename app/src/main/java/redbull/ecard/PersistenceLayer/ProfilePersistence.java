package redbull.ecard.PersistenceLayer;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.DataLayer.Services;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;


public class ProfilePersistence implements PersistenceInterface  {
    private static final String TABLENAME  = "profiles";
    private OnReadCompleteListener readListener;
    private FirebaseDatabase firebaseDatabaseInstance;
    private DatabaseReference dbTableRef;
    public ProfilePersistence(FirebaseDatabase firebaseDatabaseInstance) {
        this.firebaseDatabaseInstance = firebaseDatabaseInstance;
        this.dbTableRef = firebaseDatabaseInstance.getReference(TABLENAME);
    }

    public static  ProfilePersistence getInstance() {
        return new ProfilePersistence(FirebaseDatabase.getInstance());
    }
    public void setLocalPersistenceEnabled(boolean bool){
        firebaseDatabaseInstance.setPersistenceEnabled(bool);
    }

    public void setPersistenceSync(boolean bool) {
        this.dbTableRef.keepSynced(bool);
    }
    @Override
    public void create(Model model) {
        // do a check before casting
        if (model instanceof Profile){
            Profile profile = (Profile) model;
            createUserName(dbTableRef.child(profile.getUID()).child("name"), profile.getName());
            createUserAddress(dbTableRef.child(profile.getUID()).child("address"), profile.getAddress());
            createUserContact(dbTableRef.child(profile.getUID()).child("contact"), profile.getContact());
            createUserDescription(dbTableRef.child(profile.getUID()).child("description"), profile.getDescription());
//            createUserServices(dbTableRef.child(profile.getUID()).child("serviceIndexes"), profile.getServices());
            dbTableRef.child(profile.getUID()).child("service").setValue(profile.getService());
        }
        else {
            String ERROR = "Expected a profile class";
            throw new ClassCastException(ERROR);
        }
    }

    private void createUserName(DatabaseReference dbUserNameRef, Name name){
        dbUserNameRef.child("firstName").setValue(name.getFirstName());
        dbUserNameRef.child("lastName").setValue(name.getLastName());
        dbUserNameRef.child("middleName").setValue(name.getMiddleName());
    }
    private void createUserAddress(DatabaseReference dbUserAddressRef, Address address){
        dbUserAddressRef.child("roadNumber").setValue(address.getRoadNumber());
        dbUserAddressRef.child("houseNumber").setValue(address.getHouseNumber());
        dbUserAddressRef.child("postalCode").setValue(address.getPostalCode());
        dbUserAddressRef.child("city").setValue(address.getCity());
        dbUserAddressRef.child("province").setValue(address.getProvince());
        dbUserAddressRef.child("country").setValue(address.getCountry());
    }
    private void createUserContact(DatabaseReference dbUserContactRef, Contact contact){
        dbUserContactRef.child("cellPhone").setValue(contact.getCellPhone());
        dbUserContactRef.child("homePhone").setValue(contact.getHomePhone());
        dbUserContactRef.child("emailAddress").setValue(contact.getEmailAddress());
    }
    private void createUserDescription(DatabaseReference dbUserDescriptionRef, String description) {
        dbUserDescriptionRef.child("description").setValue(description);
    }

//    TODO NO required in the way currently service implemented as we only have one service for one person
//    private void createUserServices(DatabaseReference dbUserServiceRef, Services services) {
//        // Creates a string of comma-separated indexes that match the indexes of the ServicesTypes enum.
//        String serviceIndexesDBFormat = services.getIndexesInDBFormat();
//        dbUserServiceRef.child("serviceIndexes").setValue(serviceIndexesDBFormat);
//    }

    @Override
    public PersistenceInterface read(String uid) {
        final Profile profile = new Profile();
        dbTableRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().getValue() == null) {
                    readListener.onProfileNotFound();
                }
                else {
                    profile.map((HashMap<String, Object>) task.getResult().getValue());
                    readListener.onSuccess(profile);
                }
            }
        });
        return this;
    }

    public void addOnProfileReadCompleteListener(OnReadCompleteListener onReadCompleteListener){
        this.readListener = onReadCompleteListener;
    }
    @Override
    public void update(Model model) {

    }

    @Override
    public void delete(Long id) {

    }
}

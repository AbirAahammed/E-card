package redbull.ecard.PersistenceLayer;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Model;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.PersistenceLayer.Listeners.OnReadCompleteListener;

/**
 * The entry point for creating, accessing or updating a Profile. You can get an instance by calling {@link
 * ProfilePersistence#getInstance()}. 
 * To create a profile {@link ProfilePersistence#create(Model)}
 * To read a profile {@link ProfilePersistence#read(String)}
 */
public class ProfilePersistence implements PersistenceInterface  {
    private static final String TABLENAME  = "profiles";
    private OnReadCompleteListener readListener;
    private FirebaseDatabase firebaseDatabaseInstance;
    private DatabaseReference dbTableRef;
    public ProfilePersistence(FirebaseDatabase firebaseDatabaseInstance) {
        this.firebaseDatabaseInstance = firebaseDatabaseInstance;
        this.dbTableRef = firebaseDatabaseInstance.getReference(TABLENAME);
    }


    /**
     * A default instance of ProfilePersistence
     *
     * @return An instance of ProfilePersistenct
     */
    public static  ProfilePersistence getInstance() {
        return new ProfilePersistence(FirebaseDatabase.getInstance());
    }

    /**
     * Sets Local Persistence value to input value, if set to TRUE the firebase data is stored in
     * device persistence.
     *
     * @param bool Value to set for local persistence
     */
    public void setLocalPersistenceEnabled(boolean bool){
        firebaseDatabaseInstance.setPersistenceEnabled(bool);
    }


    /**
     * turn on or off Firebase DB sync with local storage for auto syncing.
     * @param bool Value to set for local data sync with Firebase
     */
    public void setPersistenceSync(boolean bool) {
        this.dbTableRef.keepSynced(bool);
    }


    /**
     * Creates a Profile  given the required profile object
     *
     * Sets the profile name using {@link #createUserName(DatabaseReference, Name)}
     * Sets the profile address using {@link #createUserAddress(DatabaseReference, Address)}
     * @param model A profile object
     */
    @Override
    public void create(Model model) {
        if (model instanceof Profile){
            Profile profile = (Profile) model;
            createUserName(dbTableRef.child(profile.getUID()).child("name"), profile.getName());
            createUserAddress(dbTableRef.child(profile.getUID()).child("address"), profile.getAddress());
            createUserContact(dbTableRef.child(profile.getUID()).child("contact"), profile.getContact());
            createUserDescription(dbTableRef.child(profile.getUID()).child("description"), profile.getDescription());
            dbTableRef.child(profile.getUID()).child("service").setValue(profile.getService());
        }
        else {
            String ERROR = "Expected a profile class";
            throw new ClassCastException(ERROR);
        }
    }

    /**
     * Sets the name of the profile given the name object
     *
     * @param dbUserNameRef firebase db refrence for the name instnace url
     * @param name name object to set
     */
    private void createUserName(DatabaseReference dbUserNameRef, Name name){
        dbUserNameRef.child("firstName").setValue(name.getFirstName());
        dbUserNameRef.child("lastName").setValue(name.getLastName());
        dbUserNameRef.child("middleName").setValue(name.getMiddleName());
    }

    /**
     * Sets the Users address under the profile designated by dbUserAddressRef
     *
     * @param dbUserAddressRef firebase table ref
     * @param address the address object
     */
    private void createUserAddress(DatabaseReference dbUserAddressRef, Address address){
        dbUserAddressRef.child("roadNumber").setValue(address.getRoadNumber());
        dbUserAddressRef.child("houseNumber").setValue(address.getHouseNumber());
        dbUserAddressRef.child("postalCode").setValue(address.getPostalCode());
        dbUserAddressRef.child("city").setValue(address.getCity());
        dbUserAddressRef.child("province").setValue(address.getProvince());
        dbUserAddressRef.child("country").setValue(address.getCountry());
    }

    /**
     * Sets the Users contact under the profile designated by dbUserContactRef

     * @param dbUserContactRef firebase db reference for contact
     * @param contact
     */
    private void createUserContact(DatabaseReference dbUserContactRef, Contact contact){
        dbUserContactRef.child("cellPhone").setValue(contact.getCellPhone());
        dbUserContactRef.child("homePhone").setValue(contact.getHomePhone());
        dbUserContactRef.child("emailAddress").setValue(contact.getEmailAddress());
    }

    /**
     * Sets the Users description under the profile designated by dbUserDescriptionRef

     * @param dbUserDescriptionRef firebase db reference for description
     * @param description the description as a string
     */
    private void createUserDescription(DatabaseReference dbUserDescriptionRef, String description) {
        dbUserDescriptionRef.child("description").setValue(description);
    }


    /**
     * Reads the profile of the UID string given. It returns an instance of itself and then caller
     * have to latch a listener to the returned object
     *
     * @param uid the uid of the profile
     * @return itself
     */
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

    /**
     * Adds the listener to this instance
     *
     * @param onReadCompleteListener an instance of {@link OnReadCompleteListener}
     */
    public void addOnProfileReadCompleteListener(OnReadCompleteListener onReadCompleteListener){
        this.readListener = onReadCompleteListener;
    }

    /**
     * Not implemented
     * @param model
     */
    @Override
    public void update(Model model) {

    }

    /**
     * Not implemented
     * @param id
     */
    @Override
    public void delete(Long id) {

    }
}

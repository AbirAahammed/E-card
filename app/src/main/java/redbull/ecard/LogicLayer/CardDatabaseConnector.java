package redbull.ecard.LogicLayer;

import com.google.firebase.auth.FirebaseAuth;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Profile;

// This class grabs the cards from the database or data-layer
// Essentially, its the connection between the database and the ui layer
public class CardDatabaseConnector {

    public CardDatabaseConnector(){}

    // Returns the cards associated with the database for the currently active user
    public Card[] GrabCardInstances()
    {
        // TODO
        // Please complete this method
        // User 'userProfile' to know which user this is grabbing from
        // This should return all cards associated with the user.
        // That is, everyone who they have 'previously scanned' / saved.
        // This is a dummy return value
        // May find FirebaseAuth.getInstance().getCurrentUser() useful
        return new Card[] {
                new Card (
                        "", new Name("tim", "doh ", "jim"),
                        new Contact("123", "123", "123"),
                        new Address(), "We like stuff", 0
                ),
                new Card (
                        "", new Name("james", "bob ", "dylan"),
                        new Contact("(204)call-meba)", "123", "321@gmail.com"),
                        new Address(), "We also like stuff, except its better so...", 1
                )
        };
    }
}

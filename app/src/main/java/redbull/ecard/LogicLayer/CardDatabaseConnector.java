package redbull.ecard.LogicLayer;

import com.google.firebase.auth.FirebaseAuth;

import redbull.ecard.DataLayer.Address;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Name;

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
                ),
                new Card (
                        "", new Name("boo", "bob ", "dylan"),
                        new Contact("(204)120-2134)", "123", "patty@gmail.com"),
                        new Address(), "Don't call me, I suck at life", 1
                ),
                new Card (
                        "", new Name("james", "bob ", "dylan"),
                        new Contact("(204)call-meba)", "123", "321@gmail.com"),
                        new Address(), "We also like stuff, except its better so...", 1
                ),
                new Card (
                        "", new Name("boo", "bob ", "dylan"),
                        new Contact("(204)120-2134)", "123", "patty@gmail.com"),
                        new Address(), "Don't call me, I suck at life", 1
                )
        };
    }

    // Grab the information associated with the currently logged in user
    // Retrieve this information from the database
    public Card GetActiveUser()
    {

        // TODO
        // Please complete this method
        // Return a Name object that contains the Name of the current user

        // This is a dummy value
        return new Card (
                "", new Name("Sarah", " Krystal ", "Schenider"),
                new Contact("(204)120-2134)", null, "sSchenider@gmail.com"),
                new Address(), "Expert Consultation", 1
        );
    }

    // The user has updated the service description of their profile
    // Update the database with this information
    public void ServiceUpdate(String newInfo)
    {
        // TODO
        // Please complete this method
        // 'newInfo' contains the string that the user has entered to update their profile
    }

    // The user has updated the Contact of their profile
    // Update the database with the new information
    public void ContactUpdate(Contact newContact)
    {
        // TODO
        // Please complete this method
        // 'newContact' contains the new contact information of the user

        // If the user did not make any changes then the value is null
        // For example, if a user only updated their Cell number, home and email is null
        // Thus, null means => DO NOT update
    }

    // The user has updated the template they would like to use for displaying their profile
    // Update the database with the new information
    public void TemplateUpdate(int newTemplateFormat)
    {
        // This is currently incomplete on the UI Side
        // 'newTemplateFormat' contains the new template for the user

        // A template is represented as an integer.
        // This template will be used to display someones card a specific way for someone who has scanned it
    }

    // Save the profile to our list of saved profiles
    // Update the database with the new information
    public void SavedProfile(Card profile)
    {
        // This is currently incomplete on the UI Side
        // When a QR code is scanned, this method will be called to save the profile that was scanned to the current user

        // This way if they log out, they still have the card saved that they have previously scanned
        // Note that, this is connected to the GrabCardInstances() call, since a saved profile will be added to that list of returned items.
    }
}

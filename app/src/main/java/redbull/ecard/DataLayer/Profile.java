package redbull.ecard.DataLayer;

/*
 * This class contains all of the information/variables of a user's profile.
 */

public class Profile {
	// Variables
	private Name name;
	private long uID;
	private Contact contact;
	private Address address;
	
	// Constructors
	public Profile() {
		// Default Values
		this.name = new Name();
		this.uID = -1;
		this.contact = new Contact();
		this.address = new Address();
	}

	public Profile(Name name, long uID, Contact contact, Address address) {
		this.name = name;
		this.uID = uID;
		this.contact = contact;
		this.address = address;
	}



	// Methods
	// Get methods
	public Name getName() {
		return this.name;
	}

	public long getUID() {
		return this.uID;
	}

	public Contact getContact() {
		return this.contact;
	}

	public Address getAddress() {
		return this.address;
	}


}

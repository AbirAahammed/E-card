package redbull.ecard.DataLayer;

/*
 * This class contains all of the information/variables of a profile's business card.
 */

public class Card {
	// Variables
	private String serviceName;
	private Name name; //Should this be uID?
	private Contact contact;
	private Address address;
	
	// Constructors
	public Card() {
		// Default Values
		this.serviceName = "Service name not given";
		this.name = new Name();
		this.contact = new Contact();
		this.address = new Address();
	}

	public Card(String serviceName, Name name, Contact contact, Address address) {
		this.serviceName = serviceName;
		this.name = name;
		this.contact = contact;
		this.address = address;
	}



	// Methods
	// Get Methods
	public String getServiceName() {
		return this.serviceName;
	}

	public Name getName() {
		return this.name;
	}

	public Contact getContact() {
		return this.contact;
	}

	public Address getAddress() {
		return this.address;
	}

}

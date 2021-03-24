package redbull.ecard.DataLayer;

/*
 * This class contains all of the information/variables of a profile's business card.
 */

public class  Card {
	// Variables
	private String serviceName;
	private Name name; //Should this be uID?
	private Contact contact;
	private Address address;
	private int templateNum;
	private String description;

	// Constructors
	public Card() {
		// Superclass default value
//		super();

		// Default Values
		this ("Service name not given", new Name(), new Contact(), new Address(), null, 0);
	}

	public Card(String serviceName, Name name, Contact contact, Address address, String description, int templateNum) {
		this.serviceName = serviceName;
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.description = description;
		this.templateNum = templateNum;
	}

	// Returns true if this card does not have any information attached
	public boolean IsValid()
	{
		return this.name != null && this.name.IsValid() && this.contact != null && this.contact.ValidContact()
				&& this.address != null && this.address.IsValid();
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
	public int getTemplateNum() { return this.templateNum; }


	public String getDescription() { return this.description == null ? "" : this.description; }

}

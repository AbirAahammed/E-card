package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of a Contact.
 */

public class Contact {
	// Variables
	private String cellPhone;
	private String homePhone;
	private String emailAddress;
	
	//Constructors
	public Contact() {
		// Default Values
		this.cellPhone = "No cell phone number given";
		this.homePhone = "No home phone number given";
		this.emailAddress = "No email address given";
	}


	public Contact(String cellPhone, String homePhone, String emailAddress) {
		this.cellPhone = cellPhone;
		this.homePhone = homePhone;
		this.emailAddress = emailAddress;
	}

	// Atleast one form of contact information must be valid
	// TODO need to verify that the email is valid, and phone numbers. Should this logic layer?
	public boolean ValidContact()
	{
		return this.cellPhone != null || this.homePhone != null || this.emailAddress != null;
	}

	// Methods
	// Get Methods
	public String getCellPhone() {
		return this.cellPhone;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}
	
}

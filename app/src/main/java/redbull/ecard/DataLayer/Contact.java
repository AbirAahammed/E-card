package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of a Contact.
 */

import java.util.HashMap;

public class Contact extends Model {
	// Variables
	private String cellPhone;
	private String homePhone;
	private String emailAddress;
	
	//Constructors
	public Contact() {
		// Superclass default value
		super();

		// Default Values
		this.cellPhone = "No cell phone number given";
		this.homePhone = "No home phone number given";
		this.emailAddress = "No email address given";
	}


	public Contact(String cellPhone, String homePhone, String emailAddress) {
		super();

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


	@Override
	public String toString() {
		return "Contact{" +
				"cellPhone='" + cellPhone + '\'' +
				", homePhone='" + homePhone + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				'}';
	}

	public void map(HashMap<String, String> map) {
		this.cellPhone = map.get("cellPhone");
		this.homePhone = map.get("homePhone");
		this.emailAddress = map.get("emailAddress");
	}
}

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
		this.cellPhone = "No cell phone number found";
		this.homePhone = "No home phone number found";
		this.emailAddress = "No email address found";
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

	// Returns the cell number in a formatted format
	// That is, (204) xxx-yyyy
	public String GetFormattedCell() {
		StringBuilder ret = new StringBuilder("(");

		for (int i = 0; i < this.cellPhone.length(); i++)
		{
			char appendingChar = this.cellPhone.charAt(i);
			if (i < 3)
			{
				ret.append(appendingChar);
			}
			else if (i == 3)
			{
				ret.append(')');
				ret.append(appendingChar);
			}
			else if (i == 6)
			{
				ret.append('-');
				ret.append(appendingChar);
			}
		}

		String retVal = ret.toString();
		return retVal.equals("(") ? null : retVal;
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

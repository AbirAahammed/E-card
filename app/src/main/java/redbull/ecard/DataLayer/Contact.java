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

	// Constants
	private final int VALID_PHONE_NUM_LEN = 10;
	
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
	public boolean validContact()
	{
		return validCell() || this.homePhone != null || validEmail();
	}

	// A valid cell number
	public boolean validCell()
	{
		boolean isValid = true;

		for (int i = 0; i < this.cellPhone.length(); i++)
		{
			if (!((this.cellPhone.charAt(i) >= '0' && this.cellPhone.charAt(i) <= '9') ||
			// Valid cell chars
					this.cellPhone.charAt(i) == '(' || this.cellPhone.charAt(i) == ')' ||
					this.cellPhone.charAt(i) == '-'
			))
				isValid = false;
		}
															// A phone number may include () or -, with one occurrence, some numbers are also longer
		return this.cellPhone != null && isValid && this.cellPhone.length() >= VALID_PHONE_NUM_LEN && this.cellPhone.length() <= VALID_PHONE_NUM_LEN + 3;
	}

	// Returns true if the email is valid
	// For our purposes, an email is valid as long as it has the '@' symbol, followed by some address
	public boolean validEmail()
	{
		final int MIN_REMAINDER_LEN = 3; // Should have atleast 3 characters after @ '@a.b'

		int index = this.emailAddress.indexOf('@');
		return this.emailAddress != null && index != -1 && this.emailAddress.substring(index).length() >= MIN_REMAINDER_LEN;
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

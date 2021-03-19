package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of a Contact.
 */

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


	public Contact(String cellPhone, String homePhone, String emailAddress, long id) {
		super(id);
		this.cellPhone = cellPhone;
		this.homePhone = homePhone;
		this.emailAddress = emailAddress;
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

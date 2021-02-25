package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of a user's name.
 */

public class Name {
	// Variables
	private String firstName;
	private String lastName;
	private String middleName;
	
	//Constructors
	public Name() {
		// Default Values
		this.firstName = "First name not given";
		this.lastName = "Last name not given";
		this.middleName = "Middle name not given";
	}


	public Name(String firstName, String lastName, String middleName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}



	// Methods
	// Get Methods
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

}
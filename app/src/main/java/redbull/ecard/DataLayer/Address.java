package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of an address.
 */

import java.util.HashMap;

public class Address extends Model {
	// Variables
	private String roadNumber;
	private String houseNumber;
	private String postalCode;
	private String city;
	private String province;
	private String country;

	//Constructors
	public Address() {
		// Superclass default value
		super();

		// Default Values
		this.roadNumber = "No road number given";
		this.houseNumber = "No house number given";
		this.postalCode = "No postal code given";
		this.city = "No city given";
		this.province = "No province given";
		this.country = "No country given";
	}

	// Returns true if the character x is a number
	private boolean isNumber(char x)
	{
		return x >= '0' && x <= '9';
	}

	// Returns true if the formatted address is valid
	// The address should at-least have a number
	public boolean isValid()
	{
		String formattedCheck = this.getFormattedAddress();
		boolean numValid = false;

		// Check if a number exists before a word, if there is, it is valid
		for (int i = 0; i < formattedCheck.length(); i++)
		{
			if (isNumber(formattedCheck.charAt(i)))
			{
				numValid = true;
				break;
			}
			else if (formattedCheck.charAt(i) != ' ')
			{
				// If its not just a space, its a word, but there is no number yet
				break;
			}
		}


		return formattedCheck != null && numValid;
	}

	public Address(String roadNumber, String houseNumber, String postalCode, 
		String city, String province, String country) {
		super();

		this.roadNumber = roadNumber;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
	}

	// Setters
	public void setHouseNumber (String roadNumber) { this.houseNumber = roadNumber; }

	// Methods
	//Get Methods
	public String getRoadNumber() {
		return this.roadNumber;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public String getCity() {
		return this.city;
	}

	public String getProvince() {
		return this.province;
	}

	public String getCountry() {
		return this.country;
	}

	@Override
	public String toString() {
		return "Address{" +
				"roadNumber='" + roadNumber + '\'' +
				", houseNumber='" + houseNumber + '\'' +
				", postalCode='" + postalCode + '\'' +
				", city='" + city + '\'' +
				", province='" + province + '\'' +
				", country='" + country + '\'' +
				'}';
	}

	public void map(HashMap<String, String> map) {
		this.roadNumber = map.get("roadNumber");
		this.houseNumber = map.get("houseNumber");
		this.postalCode = map.get("postalCode");
		this.city = map.get("city");
		this.province = map.get("province");
		this.country = map.get("country");
	}

	public String getFormattedAddress()
	{
		return this.houseNumber;
	}
}

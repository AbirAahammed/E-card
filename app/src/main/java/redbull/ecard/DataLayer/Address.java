package redbull.ecard.DataLayer;


/*
 * This class contains all of the information/variables of an address.
 */

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


	public Address(String roadNumber, String houseNumber, String postalCode, 
		String city, String province, String country, long id) {
		// Superclass value
		super(id);
		this.roadNumber = roadNumber;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
	}
	


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
}

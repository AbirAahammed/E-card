package redbull.ecard.DataLayer;

/*
 * This class contains all of the information/variables of a user's profile.
 */

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Profile extends Model{
	// Variables
	private Name name;
	private String uID;
	private Contact contact;
	private Address address;
	private ArrayList<Profile> connections;
	private String description;
	private Services services;
	// Constructors
	public Profile() {
		// Superclass default value
		super();

		// Default Values
		this.name = new Name();
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
		this.contact = new Contact();
		this.address = new Address();
		this.connections = new ArrayList<>();
		this.description = "No description provided";
		this.services = new Services();
	}

	public Profile(Name name, Contact contact, Address address, String description, Services services) {
		super();
		this.name = name;
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
		this.contact = contact;
		this.address = address;
		this.connections = new ArrayList<>();
		this.description = description;
		this.services = new Services();
		this.services.addServices(services); // Add service to the list of services
	}


	public Profile(String uid){
		this.name = new Name();
		this.uID = uid;
		this.contact = new Contact();
		this.address = new Address();
		this.connections = new ArrayList<>();
		this.description = "No description set";
		this.services = new Services();
	}



	// Methods
	// Get methods
	public Name getName() {
		return this.name;
	}

	public String getUID() {
		return this.uID;
	}

	public Contact getContact() {
		return this.contact;
	}

	public Address getAddress() {
		return this.address;
	}

	public String getuID() {
		return uID;
	}

	public ArrayList<Profile> getConnections() {
		return connections;
	}

	public String getDescription() {
		return description;
	}

	public Services getServices() {
		return services;
	}

	@Override
	public String toString() {
		return "Profile{" +
				"name=" + name +
				", uID='" + uID + '\'' +
				", contact=" + contact +
				", address=" + address +
				", description=" + description +
				", services=" + services +
				'}';
	}

	// Get the data for the current profile
	public void map(HashMap<String, Object> map) {
		this.name.map((HashMap<String, String>) map.get("name"));
		this.address.map((HashMap<String, String>) map.get("address"));
		this.contact.map((HashMap<String, String>) map.get("contact"));
		ArrayList<String> connectionList = (ArrayList<String>) map.get("connections");
		if (connectionList != null) {
			for (String s : connectionList) {
				this.connections.add(new Profile(s));
			}
		}
		if(map.get("description") instanceof String) {
			this.description = (String) map.get("description");
		}
		this.services.map((HashMap<String, Object>) map.get("serviceIndexes"));
 	}

 	// Get the data for another profile
	public void mapConnection(HashMap<String, Object> map) {
		this.name.map((HashMap<String, String>) map.get("name"));
		this.address.map((HashMap<String, String>) map.get("address"));
		this.contact.map((HashMap<String, String>) map.get("contact"));
		if(map.get("description") instanceof String) {
			this.description = (String) map.get("description");
		}
		this.services.map((HashMap<String, Object>) map.get("serviceIndexes"));
	}
}

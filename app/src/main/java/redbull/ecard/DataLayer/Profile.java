package redbull.ecard.DataLayer;

/*
 * This class contains all of the information/variables of a user's profile.
 */

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends Model{
	// Variables
	private Name name;
	private String uID;
	private Contact contact;
	private Address address;
	private ArrayList<Profile> connections;
	private boolean fetchedConnections;
	private String description;
	private String service;
	private static int viewedTemplate = 1; // The template the user is viewing from (default 1)

	// Constructors
	public Profile() {
		// Superclass default value
		this (new Name(), new Contact(), new Address (), "No Description provided", "No service");

		// Default Values
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

	public Profile(Name name, Contact contact, Address address, String description, String service) {
		super();
		this.name = name;
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
		this.contact = contact;
		this.address = address;
		this.connections = new ArrayList<>();
		this.description = description;
		this.service = service;
	}


	public Profile(String uid){
		this.name = new Name();
		this.uID = uid;
		this.contact = new Contact();
		this.address = new Address();
		this.connections = new ArrayList<>();
		this.description = "No description set";
		this.service = "Not Defined";
	}

	// Copy constructor
	// Copy the information from the other profile
	// DOES NOT copy connections
	public Profile(Profile profile)
	{
		this.name = profile.name;
		this.uID = profile.uID;
		this.contact = profile.contact;
		this.description = profile.description;
		this.service = profile.service;
		this.connections = profile.connections;
	}


	// Setters
	public void setConnections(ArrayList<Profile> connections)
	{
		this.connections = connections;
	}

	public void setDescription (String description) { this.description = description; }

	public void setContact (Contact contact) { this.contact = contact; }

	public void setService (String service) {
		Log.d("Tag", "Updating service: " + service);this.service = service; }

	public void setUID(String uID) { this.uID = uID; }

	public void updateHouseAddress(String road) { this.address.setHouseNumber(road); }

	// Connections have been fetched
	public void fetchedCon() { this.fetchedConnections = true; }

	public static void setViewedTemplate(int newTemplate) { viewedTemplate = newTemplate; }

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

	public boolean hasFetchedConnections() { return this.fetchedConnections; }

	public Address getAddress() {
		return this.address;
	}

	public String getID() {
		return uID;
	}

	public ArrayList<Profile> getConnections() {
		return connections;
	}

	public String getDescription() {
		return description;
	}

	public String getService() {
		return service;
	}

	public static int getViewedTemplate() {
		return viewedTemplate;
	}

	// Returns false if this card does not have sufficient information attached
	public boolean isValid()
	{
		return this.name != null && this.contact != null && this.contact.validContact()
				&& this.address != null && this.address.isValid();
	}

	@Override
	public String toString() {
		return "Profile{" +
				"name=" + name +
				", uID='" + uID + '\'' +
				", contact=" + contact +
				", address=" + address +
				", description=" + description +
				", services=" + service +
				'}';
	}

	// Returns true if the current profile has the profile p as a connection
	public boolean hasConnection (Profile p)
	{
		// We already have that UID
		for (int i = 0; i < this.connections.size(); i++)
		{
			if (this.connections.get(i).getUID().equals(p.getID()))
				return true;
		}

		return false;
	}

	// Get the data for the current profile
	public void map(HashMap<String, Object> map) {
		if (map == null)
			return;

		if(map.get("name") != null) {
			this.name.map((HashMap<String, String>) map.get("name"));
		}
		if(map.get("address") != null) {
			this.address.map((HashMap<String, String>) map.get("address"));
		}
		if(map.get("contact") != null) {
			this.contact.map((HashMap<String, String>) map.get("contact"));
		}
		ArrayList<String> connectionList = (ArrayList<String>) map.get("connections");
		if (connectionList != null) {
			for (String s : connectionList) {
				this.connections.add(new Profile(s));
			}
		}
		if(map.get("description") != null) {
			this.description = (String) map.get("description");
		}

		// Ensure the profile on the database has services
		if(map.get("service") != null) {
			this.service = map.get("service").toString();
		}

 	}

 	// Get the data for another profile
	public void mapConnection(HashMap<String, Object> map) {
		if(map==null){
			return;
		}
		if(map.get("name") != null) {
			this.name.map((HashMap<String, String>) map.get("name"));
		}
		if(map.get("address") != null) {
			this.address.map((HashMap<String, String>) map.get("address"));
		}
		if(map.get("contact") != null) {
			this.contact.map((HashMap<String, String>) map.get("contact"));
		}
		if(map.get("description") != null) {
			this.description = (String) map.get("description");
		}
		if(map.get("service") != null) {
			this.service = map.get("service").toString();
		}





	}
}

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
	// Constructors
	public Profile() {
		// Superclass default value
		super();

		// Default Values
		this.name = new Name();
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
		this.contact = new Contact();
		this.address = new Address();
		this.connections = new ArrayList<Profile>();
	}

	public Profile(Name name, Contact contact, Address address) {
		super();
		this.name = name;
		this.uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
		this.contact = contact;
		this.address = address;
		this.connections = new ArrayList<>();
	}


	public Profile(String uid){
		this.name = new Name();
		this.uID = uid;
		this.contact = new Contact();
		this.address = new Address();
		this.connections = new ArrayList<Profile>();
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

	@Override
	public String toString() {
		return "Profile{" +
				"name=" + name +
				", uID='" + uID + '\'' +
				", contact=" + contact +
				", address=" + address +
				'}';
	}

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
 	}
	public void mapConnection(HashMap<String, Object> map) {
		this.name.map((HashMap<String, String>) map.get("name"));
		this.address.map((HashMap<String, String>) map.get("address"));
		this.contact.map((HashMap<String, String>) map.get("contact"));
	}
}

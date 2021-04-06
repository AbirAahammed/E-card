package redbull.ecard.DataLayer;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Sevices Class is used to manage/hold a list of services that a user wants to provide on their card.
 * Due to a judgement call, we decided that services would be better done as a single string.
 * Users instead will offer 1 service maximum via their business card.
 *
 * Despite Profile using a string for service, this class does have its purpose.
 * This class provides functionality to provide multiple services if future developments requested it,
 * and because it is an object rather than a string, its possible to attach attributes to the services
 * if a future development wanted something (such as tags!).
 */

@SuppressWarnings("ALL")
public class Services extends Model {

    // The index location of the service in the ServiceTypes enumeration (For storing easily in DB)
    private  ArrayList<Integer> indexes;
    // The name of the service in the ServiceTypes enumereation.
    private ArrayList<String> services;

    // Default constructor
    // index is the index in the array, value is the Service type
    public Services() {
        this.indexes = new ArrayList<Integer>();
//        this.indexes.add(ServiceTypes.NOSERVICES.getValue());
        this.services = new ArrayList<String>();
//        this.services.add(ServiceTypes.NOSERVICES.getKey());
    }

    // index is the index in the array, value is the Service type
    public Services(ArrayList<ServiceTypes> serviceTypes) {
        this.indexes = new ArrayList<Integer>();
        this.services = new ArrayList<String>();
        for(int i = 0; i < serviceTypes.size(); i++) {
            this.indexes.add(serviceTypes.get(i).getValue()); // Retrieve the index of the Service
            this.services.add(serviceTypes.get(i).getKey()); // Retrieve the name of the Service
        }

    }

    // Adds more services to the array of services
    public void addServices(Services services) {
        for(int i = 0; i < services.getServices().size(); i++) {
            this.services.add(services.getServices().get(i)); // Retrieve the name of the Service
        }
        for(int i = 0; i < services.getIndexes().size(); i++) {
            this.indexes.add(services.getIndexes().get(i)); // Retrieve the index of the Service
        }
    }

    /*
     * Looks like this: "1,6,4,9,5"
     * Where each number represents the index of the Enumeration type (ServiceTypes).
     * Used to save space when adding the list of services to the Database for each user.
     */
    public String getIndexesInDBFormat() {
        String servicesDBFormat = "";
        for(int i = 0; i < indexes.size(); i++) {
            //Add comma if not first:
            if(i != 0) {
                servicesDBFormat += ",";
            }
            servicesDBFormat += indexes.get(i);
        }
        return servicesDBFormat;
    }

    @Override
    public String toString() {
        return "Services{" +
                "indexes=" + indexes +
                ", services=" + services +
                '}';
    }

    // Get methods
    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    // Maps values from DB
    // Profiles -> uid -> serviceIndexes -> servicesIndexes -> value
    public void map(HashMap<String, Object> map) {
        String indexString = (String) map.get("serviceIndexes");
        String[] arr = indexString.split(",");
        // Fill indexes from map's return value.
        for(int i = 0; i < arr.length; i++) {
            this.indexes.add(new Integer(arr[i]));
        }
        // Retrieve services from enum by index
        for(int i = 0; i < indexes.size(); i++) {
            String s = ServiceTypes.values()[indexes.get(i)].getKey();
            this.services.add(s);
        }
    }
}

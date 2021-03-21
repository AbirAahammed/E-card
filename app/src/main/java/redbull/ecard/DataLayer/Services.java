package redbull.ecard.DataLayer;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class Services {

    // The index location of the service in the ServiceTypes enumeration (For storing easily in DB)
    private  ArrayList<Integer> indexes;
    // The name of the service in the ServiceTypes enumereation.
    private ArrayList<String> services;

    // Default constructor
    // index is the index in the array, value is the Service type
    public Services() {
        this.indexes = new ArrayList<Integer>();
        this.indexes.add(ServiceTypes.NOSERVICES.getValue());
        this.services = new ArrayList<String>();
        this.services.add(ServiceTypes.NOSERVICES.getKey());
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
    public String getServicesInDBFormat() {
        String servicesDBFormat = "";
        for(int i = 0; i < services.size(); i++) {
            //Add comma if not first:
            if(i != 0) {
                servicesDBFormat += ",";
            }
            servicesDBFormat += services.get(i);
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

    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void map(HashMap<String, Object> map) {
        this.indexes = (ArrayList<Integer>) map.get("indexes");
        this.services = (ArrayList<String>) map.get("lastName");
        ArrayList<String> indexList = (ArrayList<String>) map.get("indexes");
        if (indexList != null) {
            for (String s : indexList) {
                this.indexes.add(new Integer(s));
            }
        }

        ArrayList<String> serviceList = (ArrayList<String>) map.get("services");
        if (serviceList != null) {
            for (String s : serviceList) {
                this.services.add(s);
            }
        }
    }
}

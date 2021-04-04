package redbull.ecard.PersistenceLayer;

import redbull.ecard.DataLayer.Model;

/**
 * PersistenceInterface.java
 * An interface for all persistence object in ECard Project
 * @author abir ahammed
 * @since 03-15-2021
 */
interface PersistenceInterface {

    /**
     * Creates an entry given the required object
     * @param model
     */
    public void create(Model model);

    /**
     * Reads an entry in firebase realtime db given the uid as a string.
     * @param uid
     * @return An instance of itself to attach the listener in a single statement
     */
    public PersistenceInterface read(String uid);

    /**
     * Updates an entry with the updated value of model
     * @param model
     */
    public void update(Model model);

    /**
     * Deletes an entry with the uid as a string
     * @param id
     */
    public void delete(Long id);

}



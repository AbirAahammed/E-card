package redbull.ecard.DataLayer;

/*
 * This class provides the ID that all objects will use to relate table data in the database.
 */

public abstract class Model {
    protected long id;

    // Constructors (set up ID field)
    public Model() {
        this.id = -1;
    }

    public Model(long id){
        this.id = id;
    }

    // Get method
    public long getID() {
        return this.id;
    }

}




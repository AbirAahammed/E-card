package redbull.ecard.DataLayer;


/*
 *  Note: To access the enum at a specific index:
 *  serviceTypes.values()[3];
 *  Further more, you can get the key and value of the above ServiceTypes object obtained.
 *
 */
public enum ServiceTypes {
    NOSERVICES("NOSERVICES", 0),
    PLUMBER("PLUMBER", 1),
    TEACHER("TEACHER", 2),
    TUTOR("TUTOR", 3),
    DRIVER("DRIVER", 4),
    LAWYER("LAWYER", 5),
    SOFTWAREDEVELOPER("SOFTWARE DEVELOPER", 6);



    private final String key;
    private final int value;

    ServiceTypes(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Service{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}

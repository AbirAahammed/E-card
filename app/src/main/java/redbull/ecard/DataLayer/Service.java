package redbull.ecard.DataLayer;

@SuppressWarnings("ALL")
public enum Service {
    DRIVER("DRIVER", 4),
    LAWYER("LAWYER", 5),
    PLUMBER("PLUMBER", 1),
    SOFTWAREDEVELOPER("SOFTWARE DEVELOPER", 6),
    TEACHER("TEACHER", 2),
    TUTOR("TUTOR", 3);


    private final String key;
    private final Integer value;


    Service(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}

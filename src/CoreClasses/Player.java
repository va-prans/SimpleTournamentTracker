package CoreClasses;

/**
 * Created by NSA on 21-04-2017.
 */
public class Player {

    private String name;
    private String uniqueID;

    public Player(String uniqueID, String name) {
        this.name = name;
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}

package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class Roommate {

    private int id;
    private String name;
    private long phoneNumber;

    public Roommate(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Roommate()
    {
        //do nothing
    }

    public Roommate(int id, String name, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

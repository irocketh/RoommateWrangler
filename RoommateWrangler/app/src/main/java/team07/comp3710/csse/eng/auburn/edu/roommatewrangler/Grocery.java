package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class Grocery {
    private String type;
    private int roommate;
    private int id;

    public Grocery(int roommate, String type, int id) {
        this.roommate = roommate;
        this.type = type;
        this.id = id;
    }

    public Grocery(String type, int roommate) {
        this.type = type;
        this.roommate = roommate;
    }

    public Grocery()
    {
        //do nothing
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GroceryType getEnumType()
    {
        String tempType = type.toLowerCase();
        switch (tempType) {
            case "eggs":
                return GroceryType.EGGS;
            case "bread":
                return GroceryType.BREAD;
            case "fast_food_pizza":
                return GroceryType.FAST_FOOD_PIZZA;
            case "milk":
                return GroceryType.MILK;
            default:
                return null;
        }
    }

    public int getRoommate() {
        return roommate;
    }

    public void setRoommate(int roommate) {
        this.roommate = roommate;
    }

}

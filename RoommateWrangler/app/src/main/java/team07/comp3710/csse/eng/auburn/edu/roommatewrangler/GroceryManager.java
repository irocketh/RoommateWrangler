package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class GroceryManager {

    private ArrayList<Grocery> groceryList = new ArrayList<Grocery>();

    public GroceryManager(Context context)
    {
        loadGroceries(context);
    }

    public void loadGroceries(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        groceryList = helper.getAllGroceries();
        helper.closeDB();
    }

    public ArrayList<Grocery> getGroceries()
    {
        return groceryList;
    }



}

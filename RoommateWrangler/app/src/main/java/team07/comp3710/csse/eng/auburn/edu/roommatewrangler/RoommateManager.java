package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class RoommateManager {
    private ArrayList<Roommate> roommateList = new ArrayList<Roommate>();

    public RoommateManager(Context context)
    {
        loadRoommates(context);
    }

    public void loadRoommates(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        roommateList = helper.getAllRoommates();
        helper.closeDB();
    }

    public int getNumRoommates()
    {
        return roommateList.size();
    }

    public ArrayList<Roommate> getRoommates()
    {
        return roommateList;
    }

    public String getNameFromId(int id)
    {
        String toRet = "";
        for (Roommate rm : roommateList)
        {
            if (rm.getId() == id)
            {
                toRet = rm.getName();
            }
        }

        return toRet;
    }

    public boolean roommateAdded()
    {
        return (roommateList.size() > 0);
    }

}

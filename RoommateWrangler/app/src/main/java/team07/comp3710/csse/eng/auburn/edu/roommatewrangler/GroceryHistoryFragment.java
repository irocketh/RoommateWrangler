package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/20/2015.
 */

public class GroceryHistoryFragment extends Fragment {
    TextView name;
    TextView type;
    TextView count;
    private final static String NAME = "name";
    private final static String TYPE = "type";
    private final static String COUNT = "count";
    String nameStrings;
    String typeStrings;
    String countStrings;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View groceryHis = inflater.inflate(R.layout.view_grocery_history, container, false);

         name = (TextView)groceryHis.findViewById(R.id.grocery_name);
         type = (TextView)groceryHis.findViewById(R.id.grocery_types);
         count = (TextView)groceryHis.findViewById(R.id.grocery_times);

        if (nameStrings == null)
        {
            setTextViews();
        }
        else
        {
            name.setText(nameStrings);
            type.setText(typeStrings);
            count.setText(countStrings);
        }

        return groceryHis;
    }

    private void setTextViews()
    {
        RoommateManager rMan = new RoommateManager(getActivity());
        GroceryManager gMan = new GroceryManager(getActivity());
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder typeBuilder = new StringBuilder();
        StringBuilder countBuilder = new StringBuilder();
        ArrayList<Grocery> groceries = gMan.getGroceries();
        ArrayList<Roommate> roommates = rMan.getRoommates();

        for (Roommate rm : roommates)
        {
            nameBuilder.append(rm.getName() + "\n\n\n\n\n");
            typeBuilder.append("Eggs\nBread\nMilk\nFast Food/Pizza\n\n");
            int eggCount = 0;
            int breadCount = 0;
            int milkCount = 0;
            int FF_PizzaCount = 0;

            for (Grocery gr : groceries)
            {
                if (gr.getRoommate() == rm.getId())
                {
                    GroceryType type = gr.getEnumType();
                    switch (type) {
                        case EGGS:
                            eggCount++;
                            break;
                        case BREAD:
                            breadCount++;
                            break;
                        case MILK:
                            milkCount++;
                            break;
                        case FAST_FOOD_PIZZA:
                            FF_PizzaCount++;
                            break;
                    }
                }
            }
            countBuilder.append(eggCount + "\n" + breadCount + "\n" + milkCount + "\n"
                    + FF_PizzaCount + "\n\n");
        }
        name.setText(nameBuilder.toString());
        nameStrings = nameBuilder.toString();
        type.setText(typeBuilder.toString());
        typeStrings = typeBuilder.toString();
        count.setText(countBuilder.toString());
        countStrings = countBuilder.toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME, nameStrings);
        outState.putString(TYPE, typeStrings);
        outState.putString(COUNT, countStrings);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
        {
            nameStrings = savedInstanceState.getString(NAME);
            typeStrings = savedInstanceState.getString(TYPE);
            countStrings = savedInstanceState.getString(COUNT);
        }
    }
}

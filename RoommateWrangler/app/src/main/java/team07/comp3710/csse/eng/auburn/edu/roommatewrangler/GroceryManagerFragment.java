package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class GroceryManagerFragment extends Fragment {
    private Button addPurchaseButton;
    private Button viewGroceryHistoryButton;
    private GroceryType type;
    private OnNewGroceryFrag mCallback;
    private boolean addingGrocery;
    private static final String ADDING_GROCERY = "addingGrocery";
    private RadioButton eggs;
    private RadioButton bread;
    private RadioButton ff_pizza;
    private RadioButton milk;
    private int curPosition;
    private static final char EGGS = 'e';
    private static final char BREAD = 'b';
    private static final char FF_PIZZA = 'f';
    private static final char MILK = 'm';
    private static final String TYPE = "type";
    private static final String POSITION = "position";
    Spinner roommateSpinner;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnNewGroceryFrag {
        public void onViewGroceryHistorySelected();
        public void onGroceryManResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View manGroView = inflater.inflate(R.layout.fragment_manage_groceries, container, false);

        addPurchaseButton = (Button) manGroView.findViewById(R.id.add_purchased_item_button);
        addPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoommateManager rMan = new RoommateManager(getActivity());
                if (rMan.roommateAdded()) {
                    addGroceryDisplay();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.add_roommate_grocery, Toast.LENGTH_LONG);
                }
            }
        });

        viewGroceryHistoryButton = (Button) manGroView.findViewById(R.id.grocery_history_button);
        viewGroceryHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewGroceryHistorySelected();
            }
        });

        return manGroView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnNewGroceryFrag) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewHaiku");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ADDING_GROCERY, addingGrocery);
        if (getSpinnerPosition() != -1) {
            outState.putInt(POSITION, getSpinnerPosition());
        }

        if (type != null) {
            switch (type) {
                case EGGS:
                    outState.putChar(TYPE, EGGS);
                    break;
                case BREAD:
                    outState.putChar(TYPE, BREAD);
                    break;
                case MILK:
                    outState.putChar(TYPE, MILK);
                    break;
                case FAST_FOOD_PIZZA:
                    outState.putChar(TYPE, FF_PIZZA);
                    break;
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            addingGrocery = savedInstanceState.getBoolean(ADDING_GROCERY);
            if (addingGrocery) {
                curPosition = savedInstanceState.getInt(POSITION);
                switch (savedInstanceState.getChar(TYPE)) {
                    case EGGS:
                        type = GroceryType.EGGS;
                        break;
                    case BREAD:
                        type = GroceryType.BREAD;
                        break;
                    case MILK:
                        type = GroceryType.MILK;
                        break;
                    case FF_PIZZA:
                        type = GroceryType.FAST_FOOD_PIZZA;
                        break;
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (addingGrocery) {
            addGroceryDisplay();
        }
        mCallback.onGroceryManResume();
    }

    private void addGroceryDisplay() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View addView = inflater.inflate(R.layout.add_grocery, null);
        RoommateManager rMang = new RoommateManager(getActivity());
        final ArrayList<Roommate> roommates = rMang.getRoommates();
        String[] roommateNames = new String[roommates.size()];
        int i = 0;

        for (Roommate rm : roommates) {
            roommateNames[i] = rm.getName();
            i++;
        }
        roommateSpinner = (Spinner) addView.findViewById(R.id.roommate_name_spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, roommateNames);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        roommateSpinner.setAdapter(spinnerAdapter);
        eggs = (RadioButton)addView.findViewById(R.id.eggs_button);
        eggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = GroceryType.EGGS;
            }
        });
        bread = (RadioButton)addView.findViewById(R.id.bread_button);
        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = GroceryType.BREAD;
            }
        });
        ff_pizza = (RadioButton)addView.findViewById(R.id.ff_pizza_button);
        ff_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = GroceryType.FAST_FOOD_PIZZA;
            }
        });
        milk= (RadioButton)addView.findViewById(R.id.milk_button);
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = GroceryType.MILK;
            }
        });

        if (addingGrocery) {
            roommateSpinner.setSelection(curPosition);
            if (type != null) {
                switch (type) {
                    case EGGS:
                        eggs.setChecked(true);
                        break;
                    case BREAD:
                        bread.setChecked(true);
                        break;
                    case MILK:
                        milk.setChecked(true);
                        break;
                    case FAST_FOOD_PIZZA:
                        ff_pizza.setChecked(true);
                        break;
                }
            }
        }

        addingGrocery = true;
        final AlertDialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_grocery_title)
                .setView(addView)
                .setPositiveButton(R.string.confirm_add_text, null)
                .setNegativeButton(R.string.cancel_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                addingGrocery = false;
                            }
                        }).create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(isTypeSelected())
                        {
                            String roommate = spinnerAdapter.getItem(getSpinnerPosition());
                            int roommateNum = 0;
                            for (Roommate rm : roommates) {
                                if (rm.getName().equalsIgnoreCase(roommate)) {
                                    roommateNum = rm.getId();
                                }
                            }
                            addGrocery(roommateNum);
                            Toast.makeText(getActivity(), R.string.add_successful, Toast.LENGTH_SHORT).show();
                            addingGrocery = false;
                            //Dismiss once everything is OK.
                            d.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        d.show();
    }


    private void addGrocery(int roommateNum)
    {

        Grocery grocery = new Grocery(type.name().toString(), roommateNum);
        DatabaseHelper dbHelp = new DatabaseHelper(getActivity());
        dbHelp.createGrocery(grocery);
        dbHelp.closeDB();
    }

    private int getSpinnerPosition()
    {
        if (roommateSpinner != null)
        {
            return roommateSpinner.getSelectedItemPosition();
        }
        return -1;
    }

    private boolean isTypeSelected()
    {
        if (eggs.isChecked())
        {
            return true;
        }
        if (bread.isChecked())
        {
            return true;
        }
        if (ff_pizza.isChecked())
        {
            return true;
        }
        if (milk.isChecked())
        {
            return true;
        }
        return false;
    }

}

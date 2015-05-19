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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class MainMenuFragment extends Fragment {
    private Button addRoommateButton;
    private Button manageBillsButton;
    private Button manageGroceriesButton;
    private Button resetButton;
    private EditText name;
    private EditText number;
    private String curName;
    private String curNumber;
    private OnNewManagerFrag mCallback;
    private boolean addingRoommate;
    private static final String ADDING_ROOMMATE = "addingRoommate";
    private static final String DATABASE_NAME = "roommateManager";
    private static final String NAME = "name";
    private static final String NUMBER = "number";

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnNewManagerFrag {
        public void onManageBillsSelected();
        public void onManageGroceriesSelected();
        public void onMainMenuResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View menuView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        addRoommateButton = (Button)menuView.findViewById(R.id.add_roommate_button);
        addRoommateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoommateManager rMan = new RoommateManager(getActivity());
                if (rMan.getNumRoommates() <= 5) {
                    addRoommateDisplay();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.too_many_roommates, Toast.LENGTH_LONG);
                }
            }
        });


        manageBillsButton = (Button)menuView.findViewById(R.id.manage_bills_button);
        manageBillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onManageBillsSelected();
            }
        });

        manageGroceriesButton = (Button)menuView.findViewById(R.id.manage_groceries_button);
        manageGroceriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onManageGroceriesSelected();
            }
        });
        resetButton = (Button)menuView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().deleteDatabase(DATABASE_NAME);
                Toast.makeText(getActivity(), R.string.reset, Toast.LENGTH_SHORT).show();
            }
        });

        return menuView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnNewManagerFrag) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewHaiku");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCallback != null) {
            mCallback.onMainMenuResume();
        }
        else
        {
            OnNewManagerFrag a = (OnNewManagerFrag)getActivity();
            a.onMainMenuResume();
        }
        if (addingRoommate)
        {
            addRoommateDisplay();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ADDING_ROOMMATE, addingRoommate);
        if (name != null)
        {
            outState.putString(NAME, name.getText().toString());
        }
        if (number != null)
        {
            outState.putString(NUMBER, number.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
        {
            addingRoommate = savedInstanceState.getBoolean(ADDING_ROOMMATE);
            if (addingRoommate)
            {
                curName = savedInstanceState.getString(NAME);
                curNumber = savedInstanceState.getString(NUMBER);
            }
        }
    }

    private void addRoommateDisplay()
    {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View addView = inflater.inflate(R.layout.add_roommate, null);
        name = (EditText)addView.findViewById(R.id.roommate_name);
        number = (EditText)addView.findViewById(R.id.roommate_number);
        if (addingRoommate)
        {
            name.setText(curName);
            number.setText(curNumber);
        }

        addingRoommate = true;
        final AlertDialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_roommate_title)
                .setView(addView)
                .setPositiveButton(R.string.confirm_add_roommate, null)
                .setNegativeButton(R.string.cancel_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                addingRoommate = false;
                            }
                        }).create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

        @Override
        public void onShow(DialogInterface dialog) {

            Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(isValidName(name.getText().toString()) && isValidNumber(number.getText().toString()))
                    {
                        addRoommate(addView);
                        Toast.makeText(getActivity(), R.string.add_successful, Toast.LENGTH_SHORT).show();
                        addingRoommate = false;
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

    private void addRoommate(View view)
    {
            Roommate roommate = new Roommate(name.getText().toString(), Long.parseLong(number.getText().toString()));
            DatabaseHelper dbHelp = new DatabaseHelper(getActivity());
            dbHelp.createRoommate(roommate);
            dbHelp.closeDB();
    }
    private boolean isValidName(String aName)
    {
        if ((aName != null) && !aName.isEmpty())
        {
            return true;
        }
        return false;
    }
    private boolean isValidNumber(String aNumber)
    {
        try {
        long tempLong = Long.parseLong(aNumber);
        int length = String.valueOf(tempLong).length();
        if (length == 10)
        {
            return true;
        }
    }
        catch (NumberFormatException e)
        {
            return false;
        }
        return false;
    }

}



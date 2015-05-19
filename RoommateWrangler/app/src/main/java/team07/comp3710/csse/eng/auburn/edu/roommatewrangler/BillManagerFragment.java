package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class BillManagerFragment extends Fragment {
    private Button addBillButton;
    private Button viewBillHistoryButton;
    private Button managePaymentButton;
    private Button billReminderButton;
    private BillType type;
    private OnNewBillFrag mCallback;
    private boolean adding;
    private double curAmount;
    private static final String ADDING = "adding";
    private static final String AMOUNT = "amount";
    private static final String TYPE = "type";
    private static final char WATER = 'w';
    private static final char POWER = 'p';
    private static final char INTERNET_CABLE = 'i';
    private static final char TRASH = 't';
    private static final char LANDSCAPE = 'l';

    private EditText amount;
    private RadioButton water;
    private RadioButton power;
    private RadioButton internetCable;
    private RadioButton trash;
    private RadioButton landscape;


    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnNewBillFrag {
        public void onViewBillHistorySelected();
        public void onManagePaymentSelected();
        public void onBillReminderSelected();
        public void onBillManResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View manBillView = inflater.inflate(R.layout.fragment_manage_bill, container, false);

        addBillButton = (Button)manBillView.findViewById(R.id.add_bill_button);
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoommateManager rMan = new RoommateManager(getActivity());
                if (rMan.roommateAdded()) {
                    addBillDisplay();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.add_roommate_bill, Toast.LENGTH_LONG);
                }
            }
        });

        viewBillHistoryButton = (Button)manBillView.findViewById(R.id.view_history_button);
        viewBillHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewBillHistorySelected();
            }
        });

        managePaymentButton = (Button)manBillView.findViewById(R.id.manage_bill_payment_button);
        managePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onManagePaymentSelected();
            }
        });

        billReminderButton = (Button)manBillView.findViewById(R.id.send_bill_reminder_button);
        billReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onBillReminderSelected();
            }
        });

        return manBillView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnNewBillFrag)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewHaiku");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ADDING, adding);
        outState.putDouble(AMOUNT,getCurAmount());
        if (type != null) {
            switch (type) {
                case WATER:
                    outState.putChar(TYPE, WATER);
                    break;
                case POWER:
                    outState.putChar(TYPE, POWER);
                    break;
                case INTERNET_CABLE:
                    outState.putChar(TYPE, INTERNET_CABLE);
                    break;
                case TRASH:
                    outState.putChar(TYPE, TRASH);
                    break;
                case LANDSCAPING:
                    outState.putChar(TYPE, LANDSCAPE);
                    break;
            }
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            adding = savedInstanceState.getBoolean(ADDING);
            if (adding) {
                curAmount = savedInstanceState.getDouble(AMOUNT);
                switch (savedInstanceState.getChar(TYPE)) {
                    case WATER:
                        type = BillType.WATER;
                        break;
                    case POWER:
                        type = BillType.POWER;
                        break;
                    case LANDSCAPE:
                        type = BillType.LANDSCAPING;
                        break;
                    case TRASH:
                        type = BillType.TRASH;
                        break;
                    case INTERNET_CABLE:
                        type = BillType.INTERNET_CABLE;
                        break;
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adding)
        {
            addBillDisplay();
        }
        mCallback.onBillManResume();
    }

    private void addBillDisplay()
    {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View addView = inflater.inflate(R.layout.add_bill, null);
        amount = (EditText)addView.findViewById(R.id.amount_input);

        water = (RadioButton)addView.findViewById(R.id.water_button);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = BillType.WATER;
            }
        });
        power= (RadioButton)addView.findViewById(R.id.power_button);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = BillType.POWER;
            }
        });
        internetCable= (RadioButton)addView.findViewById(R.id.internet_cable_button);
        internetCable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = BillType.INTERNET_CABLE;
            }
        });
        trash= (RadioButton)addView.findViewById(R.id.trash_button);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = BillType.TRASH;
            }
        });
        landscape= (RadioButton)addView.findViewById(R.id.landscape_button);
        landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = BillType.LANDSCAPING;
            }
        });
        if (adding)
        {
            if (type != null)
            {
                switch (type)
                {
                    case LANDSCAPING:
                        landscape.setChecked(true);
                        break;
                    case TRASH:
                        trash.setChecked(true);
                        break;
                    case WATER:
                        water.setChecked(true);
                        break;
                    case POWER:
                        power.setChecked(true);
                        break;
                    case INTERNET_CABLE:
                        internetCable.setChecked(true);
                        break;
                }
            }
            amount.setText(Double.toString(curAmount));
        }
        adding = true;
        final AlertDialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_bill_title)
                .setView(addView)
                .setPositiveButton(R.string.confirm_add_bill, null)
                .setNegativeButton(R.string.cancel_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                adding = false;
                            }
                        }).create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(isValidAmount()&& isTypeSelected())
                        {
                            addBill(addView);
                            Toast.makeText(getActivity(), R.string.add_successful, Toast.LENGTH_SHORT).show();
                            adding = false;
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

    private void addBill(View view)
    {
        RoommateManager rMang = new RoommateManager(getActivity());
        ArrayList<Roommate> roommates = rMang.getRoommates();
        BillManager bMang = new BillManager(getActivity());
        curAmount = Double.parseDouble(amount.getText().toString());
        double amountDivided = bMang.divideBill(curAmount, rMang.getNumRoommates());

        for (Roommate rm : roommates) {

            Bill bill = new Bill(type.name(), rm.getId(), amountDivided);
            DatabaseHelper dbHelp = new DatabaseHelper(getActivity());
            dbHelp.createBill(bill);
            dbHelp.closeDB();
        }
    }

    private double getCurAmount() {
        if (amount != null)
        {
            try {
                return Double.parseDouble(amount.getText().toString());
            }
            catch (NumberFormatException e)
            {
                return 0;
            }
        }
        return 0;
    }


    private boolean isValidAmount()
    {
        boolean toRet = false;
        if (amount == null)
        {
            return false;
        }

            try {
                Double.parseDouble(amount.getText().toString());
                toRet = true;
            }
            catch (NumberFormatException e)
            {
                toRet = false;
            }

        return toRet;
    }

    private boolean isTypeSelected()
    {
        if (power.isChecked())
        {
            return true;
        }
        if (water.isChecked())
        {
            return true;
        }
        if (internetCable.isChecked())
        {
            return true;
        }
        if (landscape.isChecked())
        {
            return true;
        }
        if (trash.isChecked())
        {
            return true;
        }
        return false;
    }
}

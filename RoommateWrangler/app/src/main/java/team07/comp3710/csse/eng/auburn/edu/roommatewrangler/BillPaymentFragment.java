package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.Toast;

/**
 * Created by Margaret Caufield on 4/20/2015.
 */

public class BillPaymentFragment extends ListFragment {
    private ArrayList<Bill> bills;
    private int layout;
    private boolean billSelected;
    private static final String BILL_SELECTED = "billSelected";
    private static final String POSITION = "position";
    private int curPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        resetAdaptor(formatListStrings());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
        {
            billSelected = savedInstanceState.getBoolean(BILL_SELECTED);
            if (billSelected)
            {
                curPosition = savedInstanceState.getInt(POSITION);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (billSelected)
        {
            onBillSelected(curPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(BILL_SELECTED, billSelected);
        outState.putInt(POSITION, curPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        billSelected = true;
        onBillSelected(position);

    }

    private void onBillSelected(final int position)
    {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View addView = inflater.inflate(R.layout.view_confirm_paid, null);
        curPosition = position;

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.mark_paid)
                .setView(addView)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int whichButton)
                            {
                                BillManager bMan = new BillManager(getActivity());
                                Bill b = bills.get(position);
                                b.markBillPaid();
                                bMan.updateBill(b, getActivity());
                                resetAdaptor(formatListStrings());
                                Toast.makeText(getActivity(), R.string.bill_marked_paid, Toast.LENGTH_SHORT).show();
                                billSelected = false;
                            }
                        })
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                billSelected = false;
                            }
                        }).show();
    }

    private String[] formatListStrings()
    {
        BillManager bMan = new BillManager(getActivity());
        RoommateManager rMan = new RoommateManager(getActivity());
        bills = bMan.getUnpaidBills(getActivity());
        String[] unpaidBillInfo = new String[bills.size()];
        int i = 0;
        for (Bill b: bills)
        {
            String roommateName = rMan.getNameFromId(b.getRoommateNumber());
            unpaidBillInfo[i] = roommateName + "\t " + b.getBillType() + "\t $" + b.getAmount();
            i++;
        }
        return unpaidBillInfo;
    }

    private void resetAdaptor(String[] info)
    {
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, info));
    }

}

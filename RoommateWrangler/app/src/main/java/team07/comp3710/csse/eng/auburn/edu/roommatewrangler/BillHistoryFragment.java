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
public class BillHistoryFragment extends Fragment {
    private TextView name;
    private TextView type;
    private TextView amount;
    private TextView paid;
    private String nameText;
    private String typeText;
    private String amountText;
    private String paidText;
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String AMOUNT = "amount";
    private static final String PAID = "paid";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View billHis = inflater.inflate(R.layout.view_bill_history, container, false);

        name = (TextView) billHis.findViewById(R.id.bill_his_name);
        type = (TextView) billHis.findViewById(R.id.bill_his_type);
        amount = (TextView) billHis.findViewById(R.id.bill_his_amount);
        paid = (TextView) billHis.findViewById(R.id.bill_his_paid);
        if (nameText == null)
        {
            setTextViews();
        }
        else
        {
            name.setText(nameText);
            type.setText(typeText);
            amount.setText(amountText);
            paid.setText(paidText);
        }

        return billHis;
    }

    private void setTextViews()
    {
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder typeBuilder = new StringBuilder();
        StringBuilder amountBuilder = new StringBuilder();
        StringBuilder paidBuilder = new StringBuilder();

        BillManager bMan = new BillManager(getActivity());
        RoommateManager rMan = new RoommateManager(getActivity());

        ArrayList<Bill> unpaidBills = bMan.getUnpaidBills(getActivity());
        ArrayList<Bill> paidBills = bMan.getPaidBills(getActivity());

        for (Bill bill : unpaidBills) {
            nameBuilder.append(rMan.getNameFromId(bill.getRoommateNumber()) + "\n");
            switch (bill.getEnumType()) {
                case WATER:
                    typeBuilder.append("Water\n");
                    break;
                case POWER:
                    typeBuilder.append("Power\n");
                    break;
                case INTERNET_CABLE:
                    typeBuilder.append("Internet/Cable\n");
                    break;
                case LANDSCAPING:
                    typeBuilder.append("Landscaping\n");
                    break;
                case TRASH:
                    typeBuilder.append("Trash\n");
                    break;
                default:
                    typeBuilder.append("Unknown\n");
            }

            amountBuilder.append("$" + bill.getAmount() + "\n");
            paidBuilder.append("No\n");
        }
        for (Bill bill : paidBills) {
            nameBuilder.append(rMan.getNameFromId(bill.getRoommateNumber()) + "\n");
            switch (bill.getEnumType()) {
                case WATER:
                    typeBuilder.append("Water\n");
                    break;
                case POWER:
                    typeBuilder.append("Power\n");
                    break;
                case INTERNET_CABLE:
                    typeBuilder.append("Internet/Cable\n");
                    break;
                case LANDSCAPING:
                    typeBuilder.append("Landscaping\n");
                    break;
                case TRASH:
                    typeBuilder.append("Trash\n");
                    break;
                default:
                    typeBuilder.append("Unknown\n");
            }

            amountBuilder.append("$" + bill.getAmount() + "\n");
            paidBuilder.append("Yes\n");
        }
        name.setText(nameBuilder.toString());
        nameText = nameBuilder.toString();
        type.setText(typeBuilder.toString());
        typeText = typeBuilder.toString();
        amount.setText(amountBuilder.toString());
        amountText = amountBuilder.toString();
        paid.setText(paidBuilder.toString());
        paidText = paidBuilder.toString();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME,nameText);
        outState.putString(TYPE,typeText);
        outState.putString(AMOUNT,amountText);
        outState.putString(PAID,paidText);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
        {
            nameText = savedInstanceState.getString(NAME);
            typeText = savedInstanceState.getString(TYPE);
            amountText = savedInstanceState.getString(AMOUNT);
            paidText = savedInstanceState.getString(PAID);
        }
    }
}

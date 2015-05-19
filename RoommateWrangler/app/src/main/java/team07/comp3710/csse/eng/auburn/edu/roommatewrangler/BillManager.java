package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.content.Context;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */
public class BillManager {
    private ArrayList<Bill> billList = new ArrayList<Bill>();

    public BillManager(Context context)
    {
        loadBills(context);
    }

    public void loadBills(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        billList = helper.getAllBills();
        helper.closeDB();
    }

    public double divideBill(double bill, int numRoomies)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        if (numRoomies != 0) {
            double unformatted = bill / numRoomies;

            String formatted = formatter.format(unformatted);
            if (formatted.endsWith(".00")) {
                int centsIndex = formatted.lastIndexOf(".00");
                if (centsIndex != -1) {
                    formatted = formatted.substring(1, centsIndex);
                }
            }
            try {
                return Double.parseDouble(formatted);
            }
            catch (Exception e)
            {
                return Double.parseDouble(formatted.substring(1));
            }
        }
        return -1;
    }

    public ArrayList<Bill> getBills()
    {
        return billList;
    }

    public ArrayList<Bill> getUnpaidBills(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        ArrayList<Bill> toRet = helper.getAllUnpaidBills();
        helper.closeDB();
        return toRet;
    }
    public ArrayList<Bill> getPaidBills(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        ArrayList<Bill> toRet = helper.getAllPaidBills();
        helper.closeDB();
        return toRet;
    }

    public void updateBill(Bill bill, Context context)
    {
        DatabaseHelper dbHelp = new DatabaseHelper(context);
        dbHelp.updateBill(bill);
        dbHelp.closeDB();
    }

}

package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/22/2015.
 */

public class BillReminderFragment extends Fragment {
    private ArrayList<RoommateTotalPair> pairs;
    Button sendReminderButton;

    private class RoommateTotalPair {
        Roommate roommate;
        double total;

        public Roommate getRoommate() {
            return roommate;
        }

        public void setRoommate(Roommate roommate) {
            this.roommate = roommate;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View billReminder = inflater.inflate(R.layout.fragment_bill_reminder, container, false);
        RoommateManager rMan = new RoommateManager(getActivity());
        BillManager bMan = new BillManager(getActivity());
        ArrayList<Bill> unpaidBills = bMan.getUnpaidBills(getActivity());
        ArrayList<Roommate> roommates = rMan.getRoommates();
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder totalBuilder = new StringBuilder();
        pairs = new ArrayList<RoommateTotalPair>();
        int i = 0;
        for (Roommate rm : roommates)
        {
            nameBuilder.append(rm.getName() + "\n");
            RoommateTotalPair tempPair = new RoommateTotalPair();
            tempPair.setRoommate(rm);
            double total = 0;
            for (Bill b : unpaidBills)
            {
                if (b.getRoommateNumber() == rm.getId())
                {
                    total += b.getAmount();
                }
            }
            totalBuilder.append("$" + total + "\n");
            tempPair.setTotal(total);
            pairs.add(tempPair);
            i++;
        }
        TextView names = (TextView)billReminder.findViewById(R.id.bill_reminder_name);
        TextView totals = (TextView)billReminder.findViewById(R.id.bill_reminder_total);

        names.setText(nameBuilder.toString());
        totals.setText(totalBuilder.toString());

       sendReminderButton = (Button)billReminder.findViewById(R.id.confirm_reminder_button);
        sendReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean remSent = false;
                for (RoommateTotalPair p : pairs)
                {
                    if (p.getTotal() > 0)
                    {
                        remSent = true;
                        StringBuilder messageBuilder = new StringBuilder();
                        messageBuilder.append("Hey " + p.getRoommate().getName());
                        messageBuilder.append(", your bill total is $" + p.getTotal());
                        messageBuilder.append("\nThis is an automatically generated reminder");
                        long tempNum = p.getRoommate().getPhoneNumber();
                        sendSMS(String.valueOf(tempNum), messageBuilder.toString());
                        Toast.makeText(getActivity(), R.string.reminders_sent, Toast.LENGTH_SHORT).show();

                    }
                }
                if (!remSent)
                {
                    Toast.makeText(getActivity(), R.string.no_unpaid_bills, Toast.LENGTH_LONG).show();
                }
            }
        });

        super.onCreateView(inflater, container, savedInstanceState);
        return billReminder;
    }

    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager smsMan = SmsManager.getDefault();
        smsMan.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

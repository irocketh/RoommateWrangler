package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */
public class MainActivity extends FragmentActivity implements BillManagerFragment.OnNewBillFrag,
GroceryManagerFragment.OnNewGroceryFrag, MainMenuFragment.OnNewManagerFrag{
    private MainMenuFragment mainMenuFrag;
    private BillManagerFragment billManFrag;
    private GroceryManagerFragment groceryManFrag;
    private BillHistoryFragment billHisFrag;
    private BillPaymentFragment billPayFrag;
    private GroceryHistoryFragment groceryHisFrag;
    private BillReminderFragment billReminderFrag;
    private char curFrag;
    private static final String MAIN_MENU = "mainMenuFrag";
    private static final String BILL_MAN = "billManFrag";
    private static final String GROCERY_MAN = "groceryManFrag";
    private static final String BILL_HIS = "billHisFrag";
    private static final String BILL_PAY = "billPayFrag";
    private static final String GROCERY_HIS = "groceryHisFrag";
    private static final String BILL_REMINDER = "billReminderFrag";
    private static final String CURRENT_FRAG = "currentFrag";
    private static final char MAIN_MENU_KEY = 'm';
    private static final char BILL_MAN_KEY = 'b';
    private static final char GROCERY_MAN_KEY = 'g';
    private static final char BILL_HIS_KEY = 'h';
    private static final char BILL_PAY_KEY = 'p';
    private static final char GROCERY_HIS_KEY = 'i';
    private static final char BILL_REMINDER_KEY = 'r';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            switch (savedInstanceState.getChar(CURRENT_FRAG))
            {
                case MAIN_MENU_KEY:
                    curFrag = MAIN_MENU_KEY;
                    mainMenuFrag =(MainMenuFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, MAIN_MENU);
                    break;
                case BILL_MAN_KEY:
                    curFrag = BILL_MAN_KEY;
                    billManFrag =(BillManagerFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, BILL_MAN);
                    break;
                case GROCERY_MAN_KEY:
                    curFrag = GROCERY_MAN_KEY;
                    groceryManFrag =(GroceryManagerFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, GROCERY_MAN);
                    break;
                case BILL_HIS_KEY:
                    curFrag = BILL_HIS_KEY;
                    billHisFrag =(BillHistoryFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, BILL_HIS);
                    break;
                case BILL_PAY_KEY:
                    curFrag = BILL_PAY_KEY;
                    billPayFrag =(BillPaymentFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, BILL_PAY);
                    break;
                case BILL_REMINDER_KEY:
                    curFrag = BILL_REMINDER_KEY;
                    billReminderFrag =(BillReminderFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, BILL_REMINDER);
                    break;
                case GROCERY_HIS_KEY:
                    curFrag = GROCERY_HIS_KEY;
                    groceryHisFrag =(GroceryHistoryFragment)getSupportFragmentManager().getFragment(
                            savedInstanceState, GROCERY_HIS);
                    break;
                default:
                    mainMenuFrag = new MainMenuFragment();
                    curFrag = MAIN_MENU_KEY;
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, mainMenuFrag);
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();

                    break;
            }
            return;
        }
        else
        {
            mainMenuFrag = new MainMenuFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, mainMenuFrag);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putChar(CURRENT_FRAG, curFrag);

            switch (curFrag) {
                case MAIN_MENU_KEY:
                    if (mainMenuFrag != null)
                {
                    getSupportFragmentManager().putFragment(outState, MAIN_MENU, mainMenuFrag);
                }
                    break;
                case BILL_MAN_KEY:
                    if (billManFrag != null) {
                        getSupportFragmentManager().putFragment(outState, BILL_MAN, billManFrag);
                    }
                    break;
                case BILL_HIS_KEY:
                    if (billHisFrag != null) {
                        getSupportFragmentManager().putFragment(outState, BILL_HIS, billHisFrag);
                    }
                    break;
                case BILL_PAY_KEY:
                    if (billPayFrag != null) {
                        getSupportFragmentManager().putFragment(outState, BILL_PAY, billPayFrag);
                    }
                    break;
                case BILL_REMINDER_KEY:
                    if (billReminderFrag != null) {
                        getSupportFragmentManager().putFragment(outState, BILL_REMINDER, billReminderFrag);
                    }
                    break;
                case GROCERY_HIS_KEY:
                    if (groceryHisFrag != null) {
                        getSupportFragmentManager().putFragment(outState, GROCERY_HIS, groceryHisFrag);
                    }
                    break;
                case GROCERY_MAN_KEY:
                    if (groceryManFrag != null)
                    {
                    getSupportFragmentManager().putFragment(outState, GROCERY_MAN, groceryManFrag);
                    }
                    break;
            }


        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onManageBillsSelected()
    {
        billManFrag = new BillManagerFragment();
        curFrag = BILL_MAN_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, billManFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void onManageGroceriesSelected()
    {
        groceryManFrag = new GroceryManagerFragment();
        curFrag = GROCERY_MAN_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, groceryManFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void onViewBillHistorySelected()
    {
        billHisFrag = new BillHistoryFragment();
        curFrag = BILL_HIS_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, billHisFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    public void onManagePaymentSelected()
    {
        billPayFrag = new BillPaymentFragment();
        curFrag = BILL_PAY_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, billPayFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    public void onViewGroceryHistorySelected()
    {
        groceryHisFrag = new GroceryHistoryFragment();
        curFrag = GROCERY_HIS_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, groceryHisFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    public void onBillReminderSelected() {
        billReminderFrag = new BillReminderFragment();
        curFrag = BILL_REMINDER_KEY;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, billReminderFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onGroceryManResume() {
        curFrag = GROCERY_MAN_KEY;
    }

    @Override
    public void onBillManResume() {
        curFrag = BILL_MAN_KEY;
    }

    @Override
    public void onMainMenuResume() {
        curFrag = MAIN_MENU_KEY;
    }
}

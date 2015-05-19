package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */
public class Bill {
    private double amount;
    private int roommateNumber;
    private String billType;
    private boolean paid;
    private int id;

    public Bill(String billType, int roommateNumber, double amount) {
        this.billType = billType;
        this.roommateNumber = roommateNumber;
        this.amount = amount;
        paid = false;
    }
    public Bill(String billType, int roommateNumber, double amount, boolean paid) {
        this.billType = billType;
        this.roommateNumber = roommateNumber;
        this.amount = amount;
        this.paid = paid;
    }

    public Bill(float amount, String billType) {
        this.amount = amount;
        this.billType = billType;
        paid = false;
    }

    public Bill()
    {
        //do nothing
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public int getRoommateNumber() {
        return roommateNumber;
    }

    public void setRoommateNumber(int roommateNumber) {
        this.roommateNumber = roommateNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void markBillPaid()
    {
        paid = true;
    }

    public boolean isPaid()
    {
        return paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BillType getEnumType()
    {
        String tempType = billType.toLowerCase();
        switch (tempType)
        {
            case "water": return BillType.WATER;
            case "power": return BillType.POWER;
            case "internet_cable": return BillType.INTERNET_CABLE;
            case "trash": return BillType.TRASH;
            case "landscaping": return BillType.LANDSCAPING;
            default: return null;
        }
    }

}

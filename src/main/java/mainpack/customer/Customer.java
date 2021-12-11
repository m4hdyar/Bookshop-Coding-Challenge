package mainpack.customer;

public class Customer {
    public int customerID;
    private String firstName;
    private String lastName;
    private int moneyInCents;

    public Customer(int customerID, String firstName, String lastName, int moneyInCents) {
        this.customerID=customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyInCents = moneyInCents;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getMoneyInCents() {
        return moneyInCents;
    }
    public void deductMoney(int amount) throws InsufficientMoneyException {
        if (moneyInCents>=amount){
            moneyInCents -= amount;
        }else{
            throw new InsufficientMoneyException("Available money is not enough");
        }
    }
    public void addMoney(int amount) throws IllegalArgumentException{
        if(amount>0){
            moneyInCents += amount;
        }
        else{
            throw new IllegalArgumentException("Amount of money can not be negative.");
        }
    }
}

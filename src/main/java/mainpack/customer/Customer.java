package mainpack.customer;

import java.util.Objects;

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

    public int getCustomerID() {
        return customerID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerID == customer.customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID);
    }
}

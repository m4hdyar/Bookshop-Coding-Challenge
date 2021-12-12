package factories;


import mainpack.customer.Customer;
import mainpack.customer.CustomerSet;

public class CustomerFactory {
    CustomerSet customerSet;

    protected CustomerFactory() {
        this.customerSet = new CustomerSet();
    }

    public Customer getNewCustomer(int customerID, String customerFirstName, String customerLastName, int customerMoneyInCents){
        Customer newCustomer=new Customer(customerID,customerFirstName,customerLastName,customerMoneyInCents);
        customerSet.addCustomer(newCustomer);
        return newCustomer;
    }
}

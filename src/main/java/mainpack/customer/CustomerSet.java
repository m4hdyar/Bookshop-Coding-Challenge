package mainpack.customer;

import java.util.HashSet;
import java.util.Set;

public class CustomerSet {

    private final Set<Customer> customerSet = new HashSet<>();
    public boolean addCustomer(Customer customer) {
        return customerSet.add(customer);
    }
    public Customer getCustomerByID(int customerID) {
        for (Customer element : customerSet) {
            if (element.getCustomerID()==customerID) return element;
        }
        return null;
    }
    public void removeCustomer(Customer customer) {
        customerSet.remove(customer);
    }

    public int getSetSize() {
        return customerSet.size();
    }

}

package mainpack.customer;

import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
    Done:
    1- Add a customer from the set and get that customer by ID
    2- Add a customer to the set and get that customer by ID
    3- Get a customer when the customer exists in the set
    4- Get a customer when the customer does not exist in the set
    5- Add two identical customers to the set
    6- Add two nonidentical customers to the set
 */

class CustomerSetTest {
    CustomerSet customerSet;

    Customer firstTestCustomer;
    int firstTestCustomerID = SampleData.getCustomerID(0);
    String firstTestCustomerFirstName =SampleData.getCustomerFirstName(0),
            firstTestCustomerLastName =SampleData.getCustomerLastName(0);
    int    firstTestCustomerMoneyInCents =SampleData.getCustomerMoneyInCents(0);

    Customer secondTestCustomer;
    int secondTestCustomerID = SampleData.getCustomerID(1);
    String secondTestCustomerFirstName =SampleData.getCustomerFirstName(1),
            secondTestCustomerLastName =SampleData.getCustomerLastName(1);
    int    secondTestCustomerMoneyInCents =SampleData.getCustomerMoneyInCents(1);

    @BeforeEach
    void createCustomers(){
        firstTestCustomer = new Customer(firstTestCustomerID, firstTestCustomerFirstName, firstTestCustomerLastName, firstTestCustomerMoneyInCents);
        secondTestCustomer = new Customer(secondTestCustomerID, secondTestCustomerFirstName, secondTestCustomerLastName, secondTestCustomerMoneyInCents);
    }

    @BeforeEach
    void createCustomerSet(){
        customerSet = new CustomerSet();
    }

    @Test
    void addACustomerToTheSet(){
        customerSet.addCustomer(firstTestCustomer);
        Customer actualCustomer = customerSet.getCustomerByID(firstTestCustomerID);
        Assertions.assertEquals(firstTestCustomer,actualCustomer);
    }

    @Test
    void removeACustomerFromASetWith1CustomerShouldResultInEmptySet(){
        customerSet.addCustomer(firstTestCustomer);
        customerSet.removeCustomer(firstTestCustomer);

        Assertions.assertEquals(0, customerSet.getSetSize());
    }

    @Test
    void removeACustomerFromASetWith2Customers(){
        customerSet.addCustomer(firstTestCustomer);
        customerSet.addCustomer(secondTestCustomer);
        customerSet.removeCustomer(firstTestCustomer);

        Customer actualCustomer = customerSet.getCustomerByID(secondTestCustomerID);
        Assertions.assertEquals(secondTestCustomer,actualCustomer);

        actualCustomer = customerSet.getCustomerByID(firstTestCustomerID);
        Assertions.assertNull(actualCustomer);

        Assertions.assertEquals(1, customerSet.getSetSize());
    }

    @Test
    void getACustomerWhenTheCustomerExistsInTheSet(){
        customerSet.addCustomer(firstTestCustomer);

        Customer actualCustomer = customerSet.getCustomerByID(firstTestCustomerID);
        Assertions.assertEquals(firstTestCustomer,actualCustomer);
    }

    @Test
    void getACustomerWhenTheCustomerDoesNotExistInTheSetShouldReturnNull(){
        customerSet.addCustomer(firstTestCustomer);

        Customer actualCustomer = customerSet.getCustomerByID(secondTestCustomerID);
        Assertions.assertNull(actualCustomer);
    }

    @Test
    void addTwoCustomerWithIdenticalIDToSetShouldNotAddSecondCustomer(){
        boolean isAddingSuccessful;
        customerSet.addCustomer(firstTestCustomer);

        Customer secondCustomerWithEqualID=new Customer(firstTestCustomer.getCustomerID(), firstTestCustomer.getFirstName(), firstTestCustomer.getLastName(), firstTestCustomer.getMoneyInCents());
        isAddingSuccessful= customerSet.addCustomer(secondCustomerWithEqualID);

        Assertions.assertFalse(isAddingSuccessful);
        Assertions.assertEquals(1, customerSet.getSetSize());
    }
    @Test
    void addTwoCustomerWithDifferentIDToSetShouldAddSecondCustomer(){
        boolean isAddingSuccessful;
        customerSet.addCustomer(firstTestCustomer);

        Customer secondCustomerWithDifferentID=new Customer(secondTestCustomer.getCustomerID(), firstTestCustomer.getFirstName(), firstTestCustomer.getLastName(),
                firstTestCustomer.getMoneyInCents());
        isAddingSuccessful= customerSet.addCustomer(secondCustomerWithDifferentID);

        Assertions.assertTrue(isAddingSuccessful);
        Assertions.assertEquals(2, customerSet.getSetSize());
    }
}
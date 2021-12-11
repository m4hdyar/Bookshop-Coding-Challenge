package mainpack.customer;

import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

    int testCustomerID=SampleData.getCustomerID(0);
    String testFirstName = SampleData.getCustomerFirstName(0);
    String testLastName =SampleData.getCustomerLastName(0);
    int testMoneyInCents=SampleData.getCustomerMoneyInCents(0);

    Customer testCustomer;

    @BeforeEach
    void createCustomer(){
        testCustomer = new Customer(testCustomerID, testFirstName, testLastName, testMoneyInCents);
    }
    @Test
    void getCustomerID() {
        Assertions.assertEquals(testCustomerID,testCustomer.getCustomerID());
    }
    @Test
    void getFirstName() {
        Assertions.assertEquals(testFirstName,testCustomer.getFirstName());
    }
    @Test
    void getLastName() {

        Assertions.assertEquals(testLastName,testCustomer.getLastName());
    }
    @Test
    void getMoneyInCents() {
        Assertions.assertEquals(testMoneyInCents,testCustomer.getMoneyInCents());
    }

    @Test
    void deductMoneyWhenMoneyIsEnough() {
        int moneyToDeduct=testMoneyInCents/2;
        try{
            testCustomer.deductMoney(moneyToDeduct);
        }catch (IllegalStateException ex){
            Assertions.fail();
        }
        Assertions.assertEquals(testMoneyInCents-moneyToDeduct,testCustomer.getMoneyInCents());
    }
    @Test
    void deductMoneyWhenMoneyIsNotEnoughThrowsException() {
        int moneyToDeduct=testMoneyInCents+1;
        try{
            testCustomer.deductMoney(moneyToDeduct);
        }catch (IllegalStateException ex){
            return;
        }
        Assertions.fail();
    }

    @Test
    void addMoneyArgumentGreaterEqual0() {
        int moneyToAdd=1;
        try{
            testCustomer.addMoney(moneyToAdd);
        }catch (IllegalArgumentException ex){
            Assertions.fail();
        }
        Assertions.assertEquals(testMoneyInCents+moneyToAdd,testCustomer.getMoneyInCents());
    }
    @Test
    void addMoneyArgumentSmallerThan0() {
        int moneyToAdd=-1;
        try{
            testCustomer.addMoney(moneyToAdd);
        }catch (IllegalArgumentException ex){
            return;
        }
        Assertions.fail();
    }

    @Test
    void twoEqualCustomersMeansTwoEqualID() {
        Customer testCustomer2= new Customer(testCustomer.getCustomerID(),testCustomer.getFirstName(),testCustomer.getLastName(),testCustomer.getMoneyInCents());
        Assertions.assertEquals(testCustomer, testCustomer2);
    }

    @Test
    void twoDifferentCustomersMeansTwoDifferentID() {
        Customer testCustomer2= new Customer(0,testCustomer.getFirstName(),testCustomer.getLastName(),testCustomer.getMoneyInCents());
        Assertions.assertNotEquals(testCustomer, testCustomer2);
    }


}
import mainpack.customer.Customer;
import mainpack.customer.InsufficientMoneyException;
import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

    String testFirstName = SampleData.getCustomerFirstName(0);
    String testLastName =SampleData.getCustomerLastName(0);
    int testMoneyInCents=SampleData.getCustomerMoneyInCents(0);

    Customer testCustomer;

    @BeforeEach
    void createCustomer(){
        testCustomer = new Customer(testFirstName, testLastName,testMoneyInCents);
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
        }catch (InsufficientMoneyException ex){
            Assertions.fail();
        }
        Assertions.assertEquals(testMoneyInCents-moneyToDeduct,testCustomer.getMoneyInCents());
    }
    @Test
    void deductMoneyWhenMoneyIsNotEnoughThrowsException() {
        int moneyToDeduct=testMoneyInCents+1;
        try{
            testCustomer.deductMoney(moneyToDeduct);
        }catch (InsufficientMoneyException ex){
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


}
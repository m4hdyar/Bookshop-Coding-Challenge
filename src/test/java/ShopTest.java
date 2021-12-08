import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
    Test Notes:
    1- Test getters and setters
    2- Test add money to sales
    3- Test add "negative money" to sales
    3- Test add a book to the shop

 */
class ShopTest {
    Shop testShop;
    int testShopID=1;
    String testShopName="Book Store";
    long testShopSales=500;



    @BeforeEach
    void createShop(){
        testShop = new Shop(testShopID,testShopName,testShopSales);
    }

    @Test
    void getID() {
        Assertions.assertEquals(testShopID,testShop.getID());
    }

    @Test
    void getName() {
        Assertions.assertEquals(testShopName,testShop.getName());
    }

    @Test
    void getSales() {
        Assertions.assertEquals(testShopSales,testShop.getSales());
    }

    @Test
    void add300CentToSalesShouldIncreaseSalesBy300(){
        testShop.addToSales(300);
        Assertions.assertEquals(testShopSales+300,testShop.getSales());
    }
    @Test
    void addNegativeSalesShouldThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->testShop.addToSales(-300));

    }


}

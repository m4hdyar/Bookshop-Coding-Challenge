import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
    Test Notes:
    Done:
    1- Test getters and setters
    2- Test add money to sales
    3- Test add "negative money" to sales
    4- Test add a book to the shop
    5- Add new Stock Entity with valid ISBN to shop stock.
    In Progress:
    6- Don't add Stock Entity with invalid ISBN to shop stock.
    7- Add only quantity if new Stock Entity has a book that already exist in the stock.
 */
class ShopTest {
    Shop testShop;
    int testShopID=1;
    String testShopName="Book Store";
    long testShopSales=500;


    Book firstTestBook;
    String firstBookTitle ="Title",
            firstBookGenre ="Adventure",
            firstBookISBN ="978-3442267819";
    int firstBookPageNumber =532;
    int firstTestBookPrice = 100;
    int firstTestBookQuantity = 100;

    int testQuantity=300;
    int testPrice=100;
    StockEntity testStockEntity;


    @BeforeEach
    void createShop(){
        testShop = new Shop(testShopID,testShopName,testShopSales);
        firstTestBook = new Book(firstBookTitle, firstBookPageNumber, firstBookGenre, firstBookISBN);
        testStockEntity=new StockEntity();
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
    @Test
    void addNewValidISBNStockEntityShouldIncreaseStockSizeBy1(){
        int stockSetSizeBeforeTest = testShop.getStockSetSize();
        testShop.addStockEntityToStock(testStockEntity);
        Assertions.assertEquals(stockSetSizeBeforeTest+1,testShop.getStockSetSize());
    }
    @Test
    void addNewInvalidISBNStockEntityShouldThrowException(){
    }

    //Stock related Tests


}

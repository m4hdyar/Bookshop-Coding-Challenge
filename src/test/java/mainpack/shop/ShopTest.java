package mainpack.shop;

import mainpack.book.BookISBNSet;
import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
    6- Don't add Stock Entity with invalid ISBN to shop stock.
    7- Only update quantity and price if new Stock Entity has a book that already exist in the stock.
    8- Buy should add to price to shop sales and deduct quantity from stock entity
    10- Buy should remove Stock entity when requested quantity == Stock entity quantity
    11- ISBNExists should return true if the book exists
    12- ISBNExists should return false if the book not exists
    13- getBookPrice should return the price of the stock entity of that ISBN
    In Progress:
 */
class ShopTest {
    Shop testShop;
    int testShopID=SampleData.getShopID(0);
    String testShopName= SampleData.getShopName(0);
    long testShopSales=SampleData.getShopSalesInCents(0);

    String firstTestBookISBN =SampleData.getValidISBN(0);
    int firstTestBookPrice = 100;
    int firstTestBookQuantity = 100;


    StockEntity testStockEntity;

    static BookISBNSet bookISBNSet;
    @BeforeAll
    static void createISBNSet(){
        bookISBNSet=new BookISBNSet();
        SampleData.addAllValidISBNsToBookISBNSet(bookISBNSet);
    }

    @BeforeEach
    void createShop(){
        testShop = new Shop(bookISBNSet,testShopID,testShopName,testShopSales);
        testStockEntity=new StockEntity(firstTestBookISBN,firstTestBookPrice,firstTestBookQuantity);
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
    void addANewValidISBNStockEntityShouldIncreaseStockSizeBy1(){
        int stockSetSizeBeforeTest = testShop.getStockSetSize();
        testShop.addStockEntityToStock(testStockEntity);
        Assertions.assertEquals(stockSetSizeBeforeTest+1,testShop.getStockSetSize());
    }

    @Test
    void addNewInvalidISBNStockEntityShouldThrowException(){
        StockEntity invalidISBNBook = new StockEntity(SampleData.getInvalidISBN(0),SampleData.getStockPriceInCents(0),SampleData.getStockQuantity(0));
        int stockSetSizeBeforeTest = testShop.getStockSetSize();
        Assertions.assertThrows(IllegalArgumentException.class,() ->testShop.addStockEntityToStock(invalidISBNBook));
        System.out.println("You could reach me.");
        Assertions.assertEquals(stockSetSizeBeforeTest,testShop.getStockSetSize());
    }

    @Test
    void ifISBNExistsInStockUpdateQuantityAndPrice(){
        String sameISBN = SampleData.getValidISBN(0);
        int priceOfNewStock=777;
        int quantityOfNewStock=250;
        StockEntity sameISBNStockEntity =new StockEntity(sameISBN,priceOfNewStock,quantityOfNewStock);

        int stockEntityQuantityBeforeTest = testStockEntity.getQuantity();
        testShop.addStockEntityToStock(testStockEntity);
        int stockSetSizeBeforeTest = testShop.getStockSetSize();

        testShop.addStockEntityToStock(sameISBNStockEntity);

        Assertions.assertEquals(stockSetSizeBeforeTest,testShop.getStockSetSize());
        Assertions.assertEquals(priceOfNewStock,testStockEntity.getPriceInCents());
        Assertions.assertEquals(stockEntityQuantityBeforeTest+quantityOfNewStock,testStockEntity.getQuantity());
    }

    @Test
    void twoEqualShopsMeansTwoEqualID() {
        Shop testShop2= new Shop(bookISBNSet,testShop.getID(),testShop.getName(),testShop.getSales());
        Assertions.assertEquals(testShop, testShop2);
    }

    @Test
    void twoDifferentShopsMeansTwoDifferentID() {
        Shop testShop2= new Shop(bookISBNSet,0,testShop.getName(),testShop.getSales());
        Assertions.assertNotEquals(testShop, testShop2);
    }


    @Test
    void buyShouldAddPriceToShopSalesAndDeductQuantityIfQuantityMoreThanRequested() {
        testShop.addStockEntityToStock(testStockEntity);
        int requestedQuantity = testStockEntity.getQuantity()/2;

        int quantityBeforeTest=testStockEntity.getQuantity();
        long salesBeforeTest=testShop.getSales();
        testShop.buy(testStockEntity.getBookISBN(),requestedQuantity);

        Assertions.assertEquals(quantityBeforeTest-requestedQuantity, testStockEntity.getQuantity());
        Assertions.assertEquals(salesBeforeTest+((long) testStockEntity.getPriceInCents() * requestedQuantity), testShop.getSales());
    }
    @Test
    void buyShouldAddPriceToShopSalesAndRemoveStockEntityFromSetIfQuantitySameAsRequested() {
        testShop.addStockEntityToStock(testStockEntity);
        int requestedQuantity = testStockEntity.getQuantity();

        int stockSetSizeBeforeTest=testShop.getStockSetSize();
        long salesBeforeTest=testShop.getSales();
        testShop.buy(testStockEntity.getBookISBN(),requestedQuantity);

        Assertions.assertEquals(stockSetSizeBeforeTest-1, testShop.getStockSetSize());
        Assertions.assertEquals(salesBeforeTest+((long) testStockEntity.getPriceInCents() * requestedQuantity), testShop.getSales());
    }

    @Test
    void isbnExistsShouldReturnTrueWhenISBNExistsInTheStock() {
        testShop.addStockEntityToStock(testStockEntity);
        Assertions.assertTrue(testShop.doesISBNExistInThisShop(testStockEntity.getBookISBN()));
    }
    @Test
    void isbnExistsShouldReturnFalseWhenISBNDoesNotExistInTheStock() {
        testShop.addStockEntityToStock(testStockEntity);
        Assertions.assertFalse(testShop.doesISBNExistInThisShop(SampleData.getValidISBN(2)));
    }

    @Test
    void getBookPriceShouldReturnPriceOfThePriceOfStockEntity() {
        testShop.addStockEntityToStock(testStockEntity);
        Assertions.assertEquals(testStockEntity.getPriceInCents(),testShop.getBookPrice(testStockEntity.getBookISBN()));
    }
}

import Util.SampleData;
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
    int testShopID=SampleData.getShopID(0);
    String testShopName= SampleData.getShopName(0);
    long testShopSales=SampleData.getShopSalesInCents(0);


    Book firstTestBook;
    String firstBookTitle =SampleData.getBookName(0),
            firstBookGenre =SampleData.getBookGenre(0),
            firstBookISBN =SampleData.getValidISBN(0);
    int firstBookPageNumber =SampleData.getBookPageNumbers(0);

    int firstTestBookPrice = 100;
    int firstTestBookQuantity = 100;


    StockEntity testStockEntity;


    @BeforeEach
    void createShop(){
        testShop = new Shop(testShopID,testShopName,testShopSales);
        firstTestBook = new Book(firstBookTitle, firstBookPageNumber, firstBookGenre, firstBookISBN);
        testStockEntity=new StockEntity(firstTestBook,firstTestBookPrice,firstTestBookQuantity);
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
        Book invalidISBNBook = new Book(SampleData.getBookName(0),SampleData.getBookPageNumbers(1),SampleData.getBookGenre(0),SampleData.getInvalidISBN(0));
        int stockSetSizeBeforeTest = testShop.getStockSetSize();
        Assertions.assertThrows(IllegalArgumentException.class,() ->testShop.addStockEntityToStock(testStockEntity));
        System.out.println("You could reach me.");
        Assertions.assertEquals(stockSetSizeBeforeTest,testShop.getStockSetSize());
    }

    @Test
    void ifISBNExistsUpdateQuantityAndPrice(){
        Book sameISBNBook = new Book(SampleData.getBookName(0),SampleData.getBookPageNumbers(0),SampleData.getBookGenre(0),SampleData.getInvalidISBN(0));
        int priceOfNewStock=777;
        int quantityOfNewStock=250;
        StockEntity sameISBNStockEntity =new StockEntity(sameISBNBook,priceOfNewStock,quantityOfNewStock);

        int stockEntityPriceBeforeTest = testStockEntity.getPriceInCents();
        int stockEntityQuantityBeforeTest = testStockEntity.getQuantity();
        testShop.addStockEntityToStock(testStockEntity);
        int stockSetSizeBeforeTest = testShop.getStockSetSize();

        testShop.addStockEntityToStock(sameISBNStockEntity);

        Assertions.assertEquals(stockSetSizeBeforeTest,testShop.getStockSetSize());
        Assertions.assertEquals(stockEntityPriceBeforeTest+priceOfNewStock,testStockEntity.getPriceInCents());
        Assertions.assertEquals(stockEntityQuantityBeforeTest+quantityOfNewStock,testStockEntity.getQuantity());
    }



}

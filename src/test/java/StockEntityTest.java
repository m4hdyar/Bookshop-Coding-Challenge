import Util.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockEntityTest {

    StockEntity testStockEntity;
    String firstTestBookISBN =SampleData.getValidISBN(0);

    int stockPriceInCents=SampleData.getStockPriceInCents(0);
    int stockQuantity=SampleData.getStockQuantity(0);

    @BeforeEach
    void createStockEntity(){
        testStockEntity = new StockEntity(firstTestBookISBN,stockPriceInCents,stockQuantity);
    }

    @Test
    void getBookISBN() {
        Assertions.assertEquals(firstTestBookISBN, testStockEntity.getBookISBN());
    }

    @Test
    void getPriceInCents() {
        Assertions.assertEquals(stockPriceInCents, testStockEntity.getPriceInCents());
    }

    @Test
    void getQuantity() {
        Assertions.assertEquals(stockQuantity, testStockEntity.getQuantity());
    }

    @Test
    void updateWithNewStockShouldChangePriceAndUpdateQuantity(){
        StockEntity newStockEntity = new StockEntity(testStockEntity.getBookISBN(), 175132, testStockEntity.getQuantity());
        int priceBeforeTest=testStockEntity.getPriceInCents();
        int quantityBeforeTest=testStockEntity.getQuantity();

        testStockEntity.updateWithStock(newStockEntity);
        Assertions.assertEquals(175132,testStockEntity.getPriceInCents());
        Assertions.assertEquals(quantityBeforeTest*2,testStockEntity.getQuantity());
    }

    @Test
    void createStockEntityWithNegativePriceShouldThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new StockEntity(firstTestBookISBN,-100,300));
    }
    @Test
    void createStockEntityWithNegativeQuantityShouldThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new StockEntity(firstTestBookISBN,100,-300));
    }

}
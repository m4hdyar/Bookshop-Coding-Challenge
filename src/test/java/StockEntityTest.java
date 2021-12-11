import Util.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockEntityTest {

    StockEntity testStockEntity;
    Book firstTestBook;
    String firstBookTitle = SampleData.getBookName(0),
            firstBookGenre =SampleData.getBookGenre(0),
            firstBookISBN =SampleData.getValidISBN(0);
    int firstBookPageNumber =SampleData.getBookPageNumbers(0);

    int stockPriceInCents=SampleData.getStockPriceInCents(0);
    int stockQuantity=SampleData.getStockQuantity(0);

    @BeforeEach
    void createStockEntity(){
        firstTestBook = new Book(firstBookTitle, firstBookPageNumber, firstBookGenre, firstBookISBN);
        testStockEntity = new StockEntity(firstTestBook,stockPriceInCents,stockQuantity);
    }

    @Test
    void getBook() {
        Assertions.assertEquals(firstTestBook, testStockEntity.getBook());
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
        StockEntity newStockEntity = new StockEntity(testStockEntity.getBook(), testStockEntity.getPriceInCents()+100, testStockEntity.getQuantity());
        int priceBeforeTest=testStockEntity.getPriceInCents();
        int quantityBeforeTest=testStockEntity.getQuantity();

        testStockEntity.updateWithStock(newStockEntity);
        Assertions.assertEquals(priceBeforeTest+100,testStockEntity.getPriceInCents());
        Assertions.assertEquals(quantityBeforeTest*2,testStockEntity.getQuantity());
    }

    @Test
    void createStockEntityWithNegativePriceShouldThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new StockEntity(firstTestBook,-100,300));
    }
    @Test
    void createStockEntityWithNegativeQuantityShouldThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->new StockEntity(firstTestBook,100,-300));
    }

}
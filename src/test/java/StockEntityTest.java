import Util.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockEntityTest {

    StockEntity stockEntity;
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
        stockEntity = new StockEntity(firstTestBook,stockPriceInCents,stockQuantity);
    }

    @Test
    void getBook() {
        Assertions.assertEquals(firstTestBook,stockEntity.getBook());
    }

    @Test
    void getPriceInCents() {
        Assertions.assertEquals(stockPriceInCents,stockEntity.getPriceInCents());
    }

    @Test
    void getQuantity() {
        Assertions.assertEquals(stockQuantity,stockEntity.getQuantity());
    }

    @Test
    void addQuantityBy100ShouldIncreaseQuantityBy100(){
        int quantityBeforeTest=stockEntity.getQuantity();
        stockEntity.addQuantity(100);
        Assertions.assertEquals(quantityBeforeTest+100,stockEntity.getQuantity());
    }
}
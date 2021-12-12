package mainpack.transaction;

import mainpack.book.Book;
import mainpack.book.BookISBNSet;
import mainpack.book.Genre;
import mainpack.customer.Customer;
import mainpack.customer.CustomerSet;
import mainpack.shop.Shop;
import mainpack.shop.ShopSet;
import mainpack.shop.StockEntity;
import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static mainpack.utils.SampleData.getValidISBN;

/*
    Done:
    1- If a customer has enough money to buy a book, the ISBN is correct and the shop has enough quantity
     the money must be deducted from customer and added to the shop sales. The number of shop stock should be reduced too.
    2- If a customer hasn't enough money to buy a book, throw InsufficientMoneyException. And nothing should change.
    3- If ISBN was not correct throw IncorrectISBNException. And nothing should change.
    4- If the shop has not that book 5- or the quantity was low throw InsufficientBookQuantity. And nothing should change.
    In Progress:

 */
class TransactionProcessorTest {
    static BookISBNSet bookISBNSet;
    ShopSet shopSet;
    CustomerSet customerSet;
    TransactionProcessor testTransactionProcessor;

    Book testBook;
    String bookTitle= SampleData.getBookName(0);
    Genre bookGenre=SampleData.getBookGenre(0);
    String bookValidISBN= getValidISBN(0);
    String bookInvalidISBN=SampleData.getInvalidISBN(0);
    int bookPageNumber=SampleData.getBookPageNumbers(0);

    StockEntity testStockEntity;
    String firstTestBookISBN = getValidISBN(0);
    int stockPriceInCents=SampleData.getStockPriceInCents(0);
    int stockQuantity=SampleData.getStockQuantity(0);


    //Customer money may vary depending on the test
    Customer testCustomer;
    int testCustomerID=SampleData.getCustomerID(0);
    String testCustomerFirstName = SampleData.getCustomerFirstName(0);
    String testCustomerLastName =SampleData.getCustomerLastName(0);

    Shop testShop;
    int testShopID=SampleData.getShopID(0);
    String testShopName= SampleData.getShopName(0);
    long testShopSales=SampleData.getShopSalesInCents(0);


    int testRequestedQuantity;
    int customerMoneyBeforeTest;


    @BeforeAll
    static void createISBNSet(){
        bookISBNSet=new BookISBNSet();
        SampleData.addAllValidISBNsToBookISBNSet(bookISBNSet);
    }
    @BeforeEach
    void createShopAndStockEntity(){
        testShop=new Shop(bookISBNSet,testShopID,testShopName,testShopSales);
        testStockEntity=new StockEntity(firstTestBookISBN,stockPriceInCents,stockQuantity);
        testShop.addStockEntityToStock(testStockEntity);
    }
    @BeforeEach
    void createTransactionProcessor(){
        testTransactionProcessor=new TransactionProcessor(bookISBNSet);
    }

//    private Customer getCustomerWithEnoughMoney(){
//
//    }
//    private Book getBookWithValidISBN(){
//
//    }
//    private Shop getShopWithEnoughQuantity(){
//
//    }

    private void successfulTransaction() throws Exception{
        testBook=new Book(bookTitle,bookPageNumber,bookGenre,bookValidISBN);
        testRequestedQuantity =stockQuantity/2;
        customerMoneyBeforeTest = stockPriceInCents*testRequestedQuantity*2;
        testCustomer = new Customer(testCustomerID,testCustomerFirstName,testCustomerLastName, customerMoneyBeforeTest);
        testTransactionProcessor.transact(testBook,testShop,testCustomer, testRequestedQuantity);
    }
    @Test
    void enoughMoneyCorrectISBNAndEnoughBookQuantityShouldDeductCustomerMoney(){
        Assertions.assertDoesNotThrow(this::successfulTransaction);
        Assertions.assertEquals(customerMoneyBeforeTest -(testRequestedQuantity * stockPriceInCents),testCustomer.getMoneyInCents());
        //testTransactionProcessor.transact(bookWithValidISBN,customerWithEnoughMoney,shopWithEnoughQuantity);
    }
    @Test
    void enoughMoneyCorrectISBNAndEnoughBookQuantityShouldAddPriceToSales(){
        long testShopSales=testShop.getSales();
        Assertions.assertDoesNotThrow(this::successfulTransaction);
        Assertions.assertEquals(testShopSales + ((long) testRequestedQuantity * stockPriceInCents),testShop.getSales());
    }
    @Test
    void enoughMoneyCorrectISBNAndEnoughBookQuantityShouldDeductShopQuantity(){
        Assertions.assertDoesNotThrow(this::successfulTransaction);
        Assertions.assertEquals(stockQuantity-testRequestedQuantity,testShop.getBookQuantity(testBook.getBookISBN()));
    }

    @Test
    void transactionWithNotEnoughMoneyShouldThrowInsufficientMoneyException(){
        testBook=new Book(bookTitle,bookPageNumber,bookGenre,bookValidISBN);
        testRequestedQuantity =stockQuantity/2;
        customerMoneyBeforeTest = stockPriceInCents*testRequestedQuantity/2;
        testCustomer = new Customer(testCustomerID,testCustomerFirstName,testCustomerLastName, customerMoneyBeforeTest);
        Assertions.assertThrows(InsufficientMoneyException.class,()->testTransactionProcessor.transact(testBook,testShop,testCustomer, testRequestedQuantity));

        Assertions.assertEquals(customerMoneyBeforeTest,testCustomer.getMoneyInCents());
        Assertions.assertEquals(stockQuantity,testShop.getBookQuantity(testBook.getBookISBN()));
        Assertions.assertEquals(testShopSales,testShop.getSales());
    }
    @Test
    void whenBookISBNNotCorrectShouldThrowIncorrectISBNException(){
        testBook=new Book(bookTitle,bookPageNumber,bookGenre,SampleData.getInvalidISBN(0));
        testRequestedQuantity =stockQuantity/2;
        customerMoneyBeforeTest = stockPriceInCents*testRequestedQuantity*2;
        testCustomer = new Customer(testCustomerID,testCustomerFirstName,testCustomerLastName, customerMoneyBeforeTest);
        Assertions.assertThrows(IncorrectISBNException.class,()->testTransactionProcessor.transact(testBook,testShop,testCustomer, testRequestedQuantity));

        Assertions.assertEquals(customerMoneyBeforeTest,testCustomer.getMoneyInCents());
        Assertions.assertEquals(testShopSales,testShop.getSales());
    }
    @Test
    void whenBookNotInTheShopShouldThrowInsufficientBookQuantity(){
        testBook=new Book(bookTitle,bookPageNumber,bookGenre,SampleData.getValidISBN(2));
        testRequestedQuantity =stockQuantity/2;
        customerMoneyBeforeTest = stockPriceInCents*testRequestedQuantity*2;
        testCustomer = new Customer(testCustomerID,testCustomerFirstName,testCustomerLastName, customerMoneyBeforeTest);
        Assertions.assertThrows(InsufficientBookQuantityException.class,()->testTransactionProcessor.transact(testBook,testShop,testCustomer, testRequestedQuantity));

        Assertions.assertEquals(customerMoneyBeforeTest,testCustomer.getMoneyInCents());
        Assertions.assertEquals(testShopSales,testShop.getSales());
    }
    @Test
    void whenRequestedQuantityMoreThanShopShouldThrowInsufficientBookQuantity(){
        testBook=new Book(bookTitle,bookPageNumber,bookGenre,bookValidISBN);
        testRequestedQuantity =stockQuantity*2;
        customerMoneyBeforeTest = stockPriceInCents*testRequestedQuantity*2;
        testCustomer = new Customer(testCustomerID,testCustomerFirstName,testCustomerLastName, customerMoneyBeforeTest);
        Assertions.assertThrows(InsufficientBookQuantityException.class,()->testTransactionProcessor.transact(testBook,testShop,testCustomer, testRequestedQuantity));

        Assertions.assertEquals(customerMoneyBeforeTest,testCustomer.getMoneyInCents());
        Assertions.assertEquals(stockQuantity,testShop.getBookQuantity(testBook.getBookISBN()));
        Assertions.assertEquals(testShopSales,testShop.getSales());
    }

}
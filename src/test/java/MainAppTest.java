import factories.ApplicationFactory;
import factories.CustomerFactory;
import factories.ShopFactory;
import mainpack.book.Book;
import mainpack.book.BookISBNSet;
import mainpack.book.Genre;
import mainpack.customer.Customer;
import mainpack.shop.Shop;
import mainpack.shop.StockEntity;
import mainpack.shop.StockEntityComparePair;
import mainpack.transaction.IncorrectISBNException;
import mainpack.transaction.InsufficientBookQuantityException;
import mainpack.transaction.InsufficientMoneyException;
import mainpack.transaction.TransactionProcessor;
import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//NOTE: These are not  real unit-tests, but some tests for testing the entire program and the required functionalities.
public class MainAppTest {

    ApplicationFactory applicationFactory;
    ShopFactory shopFactory;
    CustomerFactory customerFactory;
    TransactionProcessor transactionProcessor;

    //Each test is completely isolated.
    @BeforeEach
    void instantiatingFactoriesAndNeededVariables(){
        applicationFactory=new ApplicationFactory();
        shopFactory=applicationFactory.getShopFactory();
        customerFactory=applicationFactory.getCustomerFactory();
        transactionProcessor=applicationFactory.getTransactionProcessor();
        //Add some books with ISBN Title Genre and...
        applicationFactory.addSampleDataToISBNSet();
    }

    @Test
    void addingBookToTheShop(){
        Shop shopUnderTest=shopFactory.getNewShop(1,"Shop-1");
        String validBookISBNToAddToTheShop= "978-3608963762";
        String anotherValidBookISBNToAddToTheShop= "978-3442267747";
        String inValidBookISBNToAddToTheShop="142222222222";

        int priceOfEachBook=200;
        int quantityOfBook=100;
        //Adding two books with valid ISBN to the shop
        StockEntity stockEntityWithValidISBN = new StockEntity(validBookISBNToAddToTheShop,priceOfEachBook,quantityOfBook);
        shopUnderTest.addStockEntityToStock(stockEntityWithValidISBN);
        Assertions.assertEquals(1,shopUnderTest.getStockSetSize());

        StockEntity anotherStockEntityWithValidISBN = new StockEntity(anotherValidBookISBNToAddToTheShop,priceOfEachBook,quantityOfBook);
        shopUnderTest.addStockEntityToStock(anotherStockEntityWithValidISBN);
        Assertions.assertEquals(2,shopUnderTest.getStockSetSize());

        //Adding a book with invalid ISBN to the shop should only throw an exception
        StockEntity stockEntityWithInvalidISBN = new StockEntity(inValidBookISBNToAddToTheShop,priceOfEachBook,quantityOfBook);
        Assertions.assertThrows(IllegalArgumentException.class,()->shopUnderTest.addStockEntityToStock(stockEntityWithInvalidISBN));
        Assertions.assertEquals(2,shopUnderTest.getStockSetSize());

    }

    @Test
    void removingDuplicatesFromAShop(){
        Shop shopUnderTest=shopFactory.getNewShop(1,"Shop-1");
        String bookISBNToAddToTheShop= "978-3608963762";
        int priceOfEachBook=200;
        //We need to add 100 books to the
        int quantityOfBook=100;

        StockEntity stockEntity = new StockEntity(bookISBNToAddToTheShop,priceOfEachBook,quantityOfBook);
        shopUnderTest.addStockEntityToStock(stockEntity);
        //Ensure that stockEntity is already added to the Shop inventory
        Assertions.assertEquals(1,shopUnderTest.getStockSetSize());

        //Adding another 300 books with same ISBN
        int quantitySecondAdd=300;
        StockEntity anotherStockEntityWithSameISBN=new StockEntity(bookISBNToAddToTheShop,priceOfEachBook,quantitySecondAdd);
        shopUnderTest.addStockEntityToStock(anotherStockEntityWithSameISBN);
        //Shop inventory size should not change. The only thing that needs to change is the quantity
        Assertions.assertEquals(1,shopUnderTest.getStockSetSize());
        Assertions.assertEquals(quantityOfBook+quantitySecondAdd, shopUnderTest.getBookQuantity(bookISBNToAddToTheShop));

    }

    @Test
    void filterBooksByGenre(){
        Shop shopUnderTest=shopFactory.getNewShop(1,"Shop-1");
        //978-3608963762 and 978-3442267747 book have genre: Adventure
        String firstBookISBN= "978-3608963762",
                secondBookISBN="978-3442267747";
        //978-758245159 has genre: Biography
        String thirdBookISBN="978-758245159";
        //978-3841335180 has genre: Comic
        String fourthBookISBN="978-3841335180";

        int priceOfEachBook=200;
        int quantityOfBook=100;

        StockEntity firstStockEntity = new StockEntity(firstBookISBN,priceOfEachBook,quantityOfBook);
        StockEntity secondStockEntity = new StockEntity(secondBookISBN,priceOfEachBook,quantityOfBook);
        StockEntity thirdStockEntity = new StockEntity(thirdBookISBN,priceOfEachBook,quantityOfBook);
        StockEntity fourthStockEntity = new StockEntity(fourthBookISBN,priceOfEachBook,quantityOfBook);

        shopUnderTest.addStockEntityToStock(firstStockEntity);
        shopUnderTest.addStockEntityToStock(secondStockEntity);
        shopUnderTest.addStockEntityToStock(thirdStockEntity);
        shopUnderTest.addStockEntityToStock(fourthStockEntity);

        //Checking genre: Adventure
        Set<StockEntity> filteredResultGenreAdventure=shopUnderTest.getNewFilteredSet(Genre.Adventure);
        Assertions.assertEquals(2,filteredResultGenreAdventure.size());

        //Checking if these books have genre Adventure (only book 1 and 2)
        Set<StockEntity> expectedSet = new HashSet<>();
        expectedSet.add(firstStockEntity);
        expectedSet.add(secondStockEntity);
        Assertions.assertTrue(filteredResultGenreAdventure.containsAll(expectedSet));

        //Checking genre: Comic
        filteredResultGenreAdventure=shopUnderTest.getNewFilteredSet(Genre.Comic);
        Assertions.assertEquals(1,filteredResultGenreAdventure.size());

        //Checking if these books have genre Comic (only book 4)
        expectedSet = new HashSet<>();
        expectedSet.add(fourthStockEntity);
        Assertions.assertTrue(filteredResultGenreAdventure.containsAll(expectedSet));
    }

    @Test
    void compareTwoShops(){

        Shop firstShopUnderTest=shopFactory.getNewShop(1,"Shop-1");
        Shop secondShopUnderTest=shopFactory.getNewShop(2,"Shop-1");
        String firstBookISBN= "978-3608963762",
                secondBookISBN="978-3442267747",
                thirdBookISBN="978-758245159",
                fourthBookISBN="978-3841335180";
        //Both of these shops have third and fourth book
        int firstBookPriceInCents=3000;
        int secondBookPriceInCents=4000;
        int thirdBookPriceInFirstShopInCents=3000;
        int thirdBookPriceInSecondShopInCents=5000;
        int fourthBookPriceInFirstShopInCents=4000;
        int fourthBookPriceInSecondShopInCents=1400;


        int quantity=400;

        //Adding books to the first shop
        StockEntity firstBookFirstShopStockEntity = new StockEntity(firstBookISBN,firstBookPriceInCents,quantity);
        StockEntity thirdBookFirstShopStockEntity = new StockEntity(thirdBookISBN,thirdBookPriceInFirstShopInCents,quantity);
        StockEntity fourthBookFirstShopStockEntity = new StockEntity(fourthBookISBN,fourthBookPriceInFirstShopInCents,quantity);
        firstShopUnderTest.addStockEntityToStock(firstBookFirstShopStockEntity);
        firstShopUnderTest.addStockEntityToStock(thirdBookFirstShopStockEntity);
        firstShopUnderTest.addStockEntityToStock(fourthBookFirstShopStockEntity);

        StockEntity secondBookSecondShopStockEntity = new StockEntity(secondBookISBN,secondBookPriceInCents,quantity);
        StockEntity thirdBookSecondShopStockEntity = new StockEntity(thirdBookISBN,thirdBookPriceInSecondShopInCents,quantity);
        StockEntity fourthBookSecondShopStockEntity = new StockEntity(fourthBookISBN,fourthBookPriceInSecondShopInCents,quantity);
        secondShopUnderTest.addStockEntityToStock(secondBookSecondShopStockEntity);
        secondShopUnderTest.addStockEntityToStock(thirdBookSecondShopStockEntity);
        secondShopUnderTest.addStockEntityToStock(fourthBookSecondShopStockEntity);

        //We have only two books with matching ISBN
        ArrayList<StockEntityComparePair> actualStockEntityComparePairs = firstShopUnderTest.compareBooksWithShop(secondShopUnderTest);
        Assertions.assertEquals(2,actualStockEntityComparePairs.size());

        //Creating expected array list to compare with the actual one
        ArrayList<StockEntityComparePair> expectedStockEntityComparePairs= new ArrayList<>();
        expectedStockEntityComparePairs.add(new StockEntityComparePair(thirdBookFirstShopStockEntity,thirdBookSecondShopStockEntity));
        expectedStockEntityComparePairs.add(new StockEntityComparePair(fourthBookFirstShopStockEntity,fourthBookSecondShopStockEntity));
        Assertions.assertTrue(actualStockEntityComparePairs.containsAll(expectedStockEntityComparePairs));

    }

    @Test
    void sellABookToACustomer(){
        Shop shopUnderTest=shopFactory.getNewShop(1,"Shop-1",1500);
        String firstBookISBN= "978-3608963762",
                secondBookISBN="978-3442267747",
                thirdBookISBNThatDoesNotExistInTheShop="978-758245159";
        int priceOfBook1=200;
        int quantityOfBook1=400;

        int priceOfBook2=100;
        int quantityOfBook2=5;

        StockEntity firstStockEntity = new StockEntity(firstBookISBN,priceOfBook1,quantityOfBook1);
        StockEntity secondStockEntity = new StockEntity(secondBookISBN,priceOfBook2,quantityOfBook2);

        shopUnderTest.addStockEntityToStock(firstStockEntity);
        shopUnderTest.addStockEntityToStock(secondStockEntity);

        Customer customerUnderTest=customerFactory.getNewCustomer(1,"Max","Mustermann",4000);
        //Buying books with incorrect ISBN should throw exception and nothing changes
        Assertions.assertThrows(IncorrectISBNException.class,()->transactionProcessor.transact("41424112424",shopUnderTest,customerUnderTest,1));
        Assertions.assertEquals(4000,customerUnderTest.getMoneyInCents());
        Assertions.assertEquals(1500,shopUnderTest.getSales());
        Assertions.assertEquals(quantityOfBook1,shopUnderTest.getBookQuantity(firstBookISBN));


        //Buying books when shop has not enough quantity or has not that book should throw InsufficientBookQuantityException and nothing changes
        Assertions.assertThrows(InsufficientBookQuantityException.class,()->transactionProcessor.transact(secondBookISBN,shopUnderTest,customerUnderTest,6));
        Assertions.assertThrows(InsufficientBookQuantityException.class,()->transactionProcessor.transact(thirdBookISBNThatDoesNotExistInTheShop,shopUnderTest,customerUnderTest,1));
        Assertions.assertEquals(4000,customerUnderTest.getMoneyInCents());
        Assertions.assertEquals(1500,shopUnderTest.getSales());
        Assertions.assertEquals(quantityOfBook1,shopUnderTest.getBookQuantity(firstBookISBN));

        //Buying books with not enough money should throw exception and nothing changes
        // 150 book 1 with price of 200 = 30000
        Assertions.assertThrows(InsufficientMoneyException.class,()->transactionProcessor.transact(firstBookISBN,shopUnderTest,customerUnderTest,150));
        Assertions.assertEquals(4000,customerUnderTest.getMoneyInCents());
        Assertions.assertEquals(1500,shopUnderTest.getSales());
        Assertions.assertEquals(quantityOfBook1,shopUnderTest.getBookQuantity(firstBookISBN));

        //If all the conditions are right, the book quantity and customer money should be reduced, customer money should be
        final int firstTransactionRequestedQuantity=3;
        try {
            transactionProcessor.transact("978-3608963762",shopUnderTest,customerUnderTest,firstTransactionRequestedQuantity);
        } catch (Exception e) {
            Assertions.fail();
        }
        final int firstSuccessfulTransactionMoney=3*priceOfBook1;
        Assertions.assertEquals(4000-firstSuccessfulTransactionMoney,customerUnderTest.getMoneyInCents());
        Assertions.assertEquals(1500+firstSuccessfulTransactionMoney,shopUnderTest.getSales());
        Assertions.assertEquals(quantityOfBook1-firstTransactionRequestedQuantity,shopUnderTest.getBookQuantity(firstBookISBN));


        //If all the conditions are right and the customer buy all books the stock entity should be removed from the shop
        try {
            transactionProcessor.transact("978-3442267747",shopUnderTest,customerUnderTest,quantityOfBook2);
        } catch (Exception e) {
            Assertions.fail();
        }
        final int secondSuccessfulTransactionMoney=quantityOfBook2*priceOfBook2;
        Assertions.assertEquals(4000-firstSuccessfulTransactionMoney-secondSuccessfulTransactionMoney,customerUnderTest.getMoneyInCents());
        Assertions.assertEquals(1500+firstSuccessfulTransactionMoney+secondSuccessfulTransactionMoney,shopUnderTest.getSales());
        Assertions.assertEquals(1,shopUnderTest.getStockSetSize());

    }
}

package mainpack.shop;

import mainpack.book.BookISBNSet;
import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
    Done:
    1- Add a shop from the set and get that shop by ID
    2- Add a shop to the set and get that shop by ID
    3- Get a shop when the shop exists in the set
    4- Get a shop when the shop does not exist in the set
    5- Add two identical shops to the set
    6- Add two nonidentical shops to the set
 */

class ShopSetTest {
    ShopSet shopSet;

    Shop firstTestShop;
    int firstTestShopID = SampleData.getShopID(0);
    String firstTestShopName =SampleData.getShopName(0);
    long firstTestShopSales =SampleData.getShopSalesInCents(0);

    Shop secondTestShop;
    int secondTestShopID = SampleData.getShopID(1);
    String secondTestShopFirstName =SampleData.getShopName(1);
    long secondTestShopMoneyInCents =SampleData.getShopSalesInCents(1);

    static BookISBNSet bookISBNSet;
    @BeforeAll
    static void createISBNSet(){
        bookISBNSet=new BookISBNSet();
    }

    @BeforeEach
    void createShops(){
        firstTestShop = new Shop(bookISBNSet,firstTestShopID, firstTestShopName, firstTestShopSales);
        secondTestShop = new Shop(bookISBNSet,secondTestShopID, secondTestShopFirstName, secondTestShopMoneyInCents);
    }

    @BeforeEach
    void createShopSet(){
        shopSet = new ShopSet();
    }

    @Test
    void addAShopToTheSet(){
        shopSet.addShop(firstTestShop);
        Shop actualShop = shopSet.getShopByID(firstTestShopID);
        Assertions.assertEquals(firstTestShop,actualShop);
    }

    @Test
    void removeAShopFromASetWith1ShopShouldResultInEmptySet(){
        shopSet.addShop(firstTestShop);
        shopSet.removeShop(firstTestShop);

        Assertions.assertEquals(0, shopSet.getSetSize());
    }

    @Test
    void removeAShopFromASetWith2Shops(){
        shopSet.addShop(firstTestShop);
        shopSet.addShop(secondTestShop);
        shopSet.removeShop(firstTestShop);

        Shop actualShop = shopSet.getShopByID(secondTestShopID);
        Assertions.assertEquals(secondTestShop,actualShop);

        actualShop = shopSet.getShopByID(firstTestShopID);
        Assertions.assertNull(actualShop);

        Assertions.assertEquals(1, shopSet.getSetSize());
    }

    @Test
    void getAShopWhenTheShopExistsInTheSet(){
        shopSet.addShop(firstTestShop);

        Shop actualShop = shopSet.getShopByID(firstTestShopID);
        Assertions.assertEquals(firstTestShop,actualShop);
    }

    @Test
    void getAShopWhenTheShopDoesNotExistInTheSetShouldReturnNull(){
        shopSet.addShop(firstTestShop);

        Shop actualShop = shopSet.getShopByID(secondTestShopID);
        Assertions.assertNull(actualShop);
    }

    @Test
    void addTwoShopWithIdenticalIDToSetShouldNotAddSecondShop(){
        boolean isAddingSuccessful;
        shopSet.addShop(firstTestShop);

        Shop secondShopWithEqualID=new Shop(bookISBNSet,firstTestShop.getID(), firstTestShop.getName(), firstTestShop.getSales());
        isAddingSuccessful= shopSet.addShop(secondShopWithEqualID);

        Assertions.assertFalse(isAddingSuccessful);
        Assertions.assertEquals(1, shopSet.getSetSize());
    }
    @Test
    void addTwoShopWithDifferentIDToSetShouldAddSecondShop(){
        boolean isAddingSuccessful;
        shopSet.addShop(firstTestShop);

        Shop secondShopWithDifferentID=new Shop(bookISBNSet,secondTestShop.getID(), firstTestShop.getName(), firstTestShop.getSales());
        isAddingSuccessful= shopSet.addShop(secondShopWithDifferentID);

        Assertions.assertTrue(isAddingSuccessful);
        Assertions.assertEquals(2, shopSet.getSetSize());
    }
}
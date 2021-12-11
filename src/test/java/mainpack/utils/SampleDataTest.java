package mainpack.utils;

import mainpack.book.BookISBNSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleDataTest {

    BookISBNSet testBookISBNSet;
    @BeforeEach
    void createBookISBNSet(){
        testBookISBNSet=new BookISBNSet();
    }
    @Test
    void addAllValidISBNsToBookISBNSetShouldIncreaseSetSize() {
        SampleData.addAllValidISBNsToBookISBNSet(testBookISBNSet);
        Assertions.assertTrue(testBookISBNSet.getSetSize()>0);
    }
}
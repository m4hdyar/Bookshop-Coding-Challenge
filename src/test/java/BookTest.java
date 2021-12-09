import Util.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    Book testBook;
    String bookTitle= SampleData.getBookName(0),
            bookGenre=SampleData.getBookGenre(0),
            bookISBN=SampleData.getValidISBN(0);
    int bookPageNumber=SampleData.getBookPageNumbers(0);

    @BeforeEach
    void createBook(){
        //TODO: Convert Genre to enum
        testBook= new Book(bookTitle,bookPageNumber,bookGenre,bookISBN);
    }

    @Test
    void testNameGetter(){
        Assertions.assertEquals(bookTitle,testBook.getBookTitle());
    }

    @Test
    void testPageNumbersGetter(){
        Assertions.assertEquals(bookPageNumber,testBook.getBookPageNumber());
    }

    @Test
    void testGenreGetter(){
        Assertions.assertEquals(bookGenre,testBook.getBookGenre());
    }

    @Test
    void testISBNGetter(){
        Assertions.assertEquals(bookISBN,testBook.getBookISBN());
    }


    @Test
    void twoEqualBooksMeansTwoEqualISBN() {
        Book testBook2= new Book(testBook.getBookTitle(),testBook.getBookPageNumber(),testBook.getBookGenre(),testBook.getBookISBN());
        Assertions.assertEquals(testBook, testBook2);
    }

    @Test
    void twoUnequalBooksMeansTwoUnequalISBN() {
        Book testBook2= new Book(testBook.getBookTitle(),testBook.getBookPageNumber(),testBook.getBookGenre(),"Another-ISBN");
        Assertions.assertNotEquals(testBook, testBook2);
    }

    @Test
    void testHashCode() {
    }
}
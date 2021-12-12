package mainpack.book;

import mainpack.utils.SampleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/*
    Done:
    1- Add a book from the set and get that book by ISBN
    2- Add a book to the set and get that book by ISBN
    3- Get a book when the book exists in the set
    4- Get a book when the book does not exist in the set
    5- Add two identical books to the set
    6- Add two nonidentical books to the set
 */
class BookISBNSetTest {
    BookISBNSet testBookISBNSet;

    Book firstTestBook;
    String firstBookTitle = SampleData.getBookName(0);
    Genre firstBookGenre =SampleData.getBookGenre(0);
    String firstBookISBN =SampleData.getValidISBN(0);
    int firstBookPageNumber =SampleData.getBookPageNumbers(0);

    Book secondTestBook;
    String secondBookTitle=SampleData.getBookName(1);
    Genre secondBookGenre=SampleData.getBookGenre(1);
    String secondBookISBN=SampleData.getValidISBN(1);
    int secondBookPageNumber=SampleData.getBookPageNumbers(1);

    @BeforeEach
    void createBook(){
        firstTestBook = new Book(firstBookTitle, firstBookPageNumber, firstBookGenre, firstBookISBN);
        secondTestBook = new Book(secondBookTitle, secondBookPageNumber, secondBookGenre, secondBookISBN);
    }

    @BeforeEach
    void BookISBNList(){
        testBookISBNSet = new BookISBNSet();
    }

    @Test
    void addABookToTheSet(){
        testBookISBNSet.addBook(firstTestBook);
        Book actualBook = testBookISBNSet.getBookByISBN(firstBookISBN);
        Assertions.assertEquals(firstTestBook,actualBook);
    }

    @Test
    void removeABookFromASetWith1BookShouldResultInEmptySet(){
        testBookISBNSet.addBook(firstTestBook);
        testBookISBNSet.removeBook(firstTestBook);

        Assertions.assertEquals(0, testBookISBNSet.getSetSize());
    }

    @Test
    void removeABookFromASetWith2Book(){
        testBookISBNSet.addBook(firstTestBook);
        testBookISBNSet.addBook(secondTestBook);
        testBookISBNSet.removeBook(firstTestBook);

        Book actualBook = testBookISBNSet.getBookByISBN(secondBookISBN);
        Assertions.assertEquals(secondTestBook,actualBook);

        actualBook = testBookISBNSet.getBookByISBN(firstBookISBN);
        Assertions.assertNull(actualBook);

        Assertions.assertEquals(1, testBookISBNSet.getSetSize());
    }

    @Test
    void getABookWhenTheBookExistsInTheSet(){
        testBookISBNSet.addBook(firstTestBook);

        Book actualBook = testBookISBNSet.getBookByISBN(firstBookISBN);
        Assertions.assertEquals(firstTestBook,actualBook);
    }

    @Test
    void getABookWhenTheBookDoesNotExistInTheSetShouldReturnNull(){
        testBookISBNSet.addBook(firstTestBook);

        Book actualBook = testBookISBNSet.getBookByISBN(secondBookISBN);
        Assertions.assertNull(actualBook);
    }

    @Test
    void addTwoBooksWithIdenticalISBNToSetShouldNotAddSecondBook(){
        boolean isAddingSuccessful;
        testBookISBNSet.addBook(firstTestBook);

        Book secondBookWithEqualISBN=new Book(firstTestBook.getBookTitle(),firstTestBook.getBookPageNumber(),firstTestBook.getBookGenre(),firstTestBook.getBookISBN());
        isAddingSuccessful= testBookISBNSet.addBook(secondBookWithEqualISBN);

        Assertions.assertFalse(isAddingSuccessful);
        Assertions.assertEquals(1, testBookISBNSet.getSetSize());
    }
    @Test
    void addTwoBooksWithDifferentISBNToSetShouldAddSecondBook(){
        boolean isAddingSuccessful;
        testBookISBNSet.addBook(firstTestBook);

        Book secondBookWithDifferentISBN=new Book(firstTestBook.getBookTitle(),firstTestBook.getBookPageNumber(),firstTestBook.getBookGenre(),
                secondTestBook.getBookISBN());
        isAddingSuccessful= testBookISBNSet.addBook(secondBookWithDifferentISBN);

        Assertions.assertTrue(isAddingSuccessful);
        Assertions.assertEquals(2, testBookISBNSet.getSetSize());
    }
}
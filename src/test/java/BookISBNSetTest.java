import mainpack.book.Book;
import mainpack.book.BookISBNSet;
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
    BookISBNSet testBookISBNList;

    Book firstTestBook;
    String firstBookTitle = SampleData.getBookName(0),
            firstBookGenre =SampleData.getBookGenre(0),
            firstBookISBN =SampleData.getValidISBN(0);
    int firstBookPageNumber =SampleData.getBookPageNumbers(0);

    Book secondTestBook;
    String secondBookTitle=SampleData.getBookName(1),
            secondBookGenre=SampleData.getBookGenre(1),
            secondBookISBN=SampleData.getValidISBN(1);
    int secondBookPageNumber=SampleData.getBookPageNumbers(1);

    @BeforeEach
    void createBook(){
        firstTestBook = new Book(firstBookTitle, firstBookPageNumber, firstBookGenre, firstBookISBN);
        secondTestBook = new Book(secondBookTitle, secondBookPageNumber, secondBookGenre, secondBookISBN);
    }

    @BeforeEach
    void BookISBNList(){
        testBookISBNList= new BookISBNSet();
    }

    @Test
    void addABookToTheSet(){
        testBookISBNList.addBook(firstTestBook);
        Book actualBook = testBookISBNList.getBookByISBN(firstBookISBN);
        Assertions.assertEquals(firstTestBook,actualBook);
    }

    @Test
    void removeABookFromASetWith1BookShouldResultInEmptySet(){
        testBookISBNList.addBook(firstTestBook);
        testBookISBNList.removeBook(firstTestBook);

        Assertions.assertEquals(0,testBookISBNList.getSetSize());
    }

    @Test
    void removeABookFromASetWith2Book(){
        testBookISBNList.addBook(firstTestBook);
        testBookISBNList.addBook(secondTestBook);
        testBookISBNList.removeBook(firstTestBook);

        Book actualBook = testBookISBNList.getBookByISBN(secondBookISBN);
        Assertions.assertEquals(secondTestBook,actualBook);

        actualBook = testBookISBNList.getBookByISBN(firstBookISBN);
        Assertions.assertNull(actualBook);

        Assertions.assertEquals(1, testBookISBNList.getSetSize());
    }

    @Test
    void getABookWhenTheBookExistsInTheSet(){
        testBookISBNList.addBook(firstTestBook);

        Book actualBook = testBookISBNList.getBookByISBN(firstBookISBN);
        Assertions.assertEquals(firstTestBook,actualBook);
    }

    @Test
    void getABookWhenTheBookDoesNotExistInTheSetShouldReturnNull(){
        testBookISBNList.addBook(firstTestBook);

        Book actualBook = testBookISBNList.getBookByISBN(secondBookISBN);
        Assertions.assertNull(actualBook);
    }

    @Test
    void addTwoBooksWithIdenticalISBNToSetShouldNotAddSecondBook(){
        boolean isAddingSuccessful;
        testBookISBNList.addBook(firstTestBook);

        Book secondBookWithEqualISBN=new Book(firstTestBook.getBookTitle(),firstTestBook.getBookPageNumber(),firstTestBook.getBookGenre(),firstTestBook.getBookISBN());
        isAddingSuccessful=testBookISBNList.addBook(secondBookWithEqualISBN);

        Assertions.assertFalse(isAddingSuccessful);
        Assertions.assertEquals(1,testBookISBNList.getSetSize());
    }
    @Test
    void addTwoBooksWithDifferentISBNToSetShouldAddSecondBook(){
        boolean isAddingSuccessful;
        testBookISBNList.addBook(firstTestBook);

        Book secondBookWithEqualISBN=new Book(firstTestBook.getBookTitle(),firstTestBook.getBookPageNumber(),firstTestBook.getBookGenre(),
                secondTestBook.getBookISBN());
        isAddingSuccessful=testBookISBNList.addBook(secondBookWithEqualISBN);

        Assertions.assertTrue(isAddingSuccessful);
        Assertions.assertEquals(2,testBookISBNList.getSetSize());
    }
}
package mainpack.book;

import java.util.Objects;

public class Book {
    private final String bookTitle;
    private final int bookPageNumber;
    private final Genre bookGenre;
    private final String bookISBN;

    public Book(String bookTitle, int bookPageNumber, Genre bookGenre, String bookISBN) {

        this.bookTitle = bookTitle;
        this.bookPageNumber = bookPageNumber;
        this.bookGenre = bookGenre;
        this.bookISBN = bookISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getBookPageNumber() {
        return bookPageNumber;
    }

    public Genre getBookGenre() {
        return bookGenre;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    //Equal books means equal ISBNs
    @Override
    public boolean equals(Object anotherBook) {
        if (this == anotherBook) return true;
        if (anotherBook == null || getClass() != anotherBook.getClass()) return false;
        Book book = (Book) anotherBook;
        return bookISBN.equals(book.bookISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookISBN);
    }
}

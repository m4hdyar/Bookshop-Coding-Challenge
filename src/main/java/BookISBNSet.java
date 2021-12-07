import java.util.HashSet;
import java.util.Set;

public class BookISBNSet {

    private final Set<Book> bookISBNSet = new HashSet<>();

    public boolean addBook(Book book) {
        return bookISBNSet.add(book);
    }

    public Book getBookByISBN(String bookISBN) {

        for (Book element : bookISBNSet) {
            if (element.getBookISBN().equals(bookISBN)) return element;
        }
        return null;
    }

    public void removeBook(Book testBook) {
        bookISBNSet.remove(testBook);
    }

    public int getSetSize() {
        return bookISBNSet.size();
    }
}

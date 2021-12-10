public class StockEntity {
    private Book book;
    private int priceInCents;
    private int quantity;

    public StockEntity(Book book, int priceInCents, int quantity) {
        this.book = book;
        this.priceInCents = priceInCents;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantityToAdd) {
        quantity+=quantityToAdd;
    }
}

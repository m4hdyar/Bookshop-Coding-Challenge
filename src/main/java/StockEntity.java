public class StockEntity {
    private Book book;
    private int priceInCents;
    private int quantity;

    public StockEntity(Book book, int priceInCents, int quantity) throws IllegalArgumentException{
        if(priceInCents<0 || quantity<0) throw new IllegalArgumentException("Price or Quantity can not be negative");
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


    public void updateWithStock(StockEntity newStockEntity) {
        quantity+=newStockEntity.getQuantity();
        priceInCents=newStockEntity.getPriceInCents();
    }
}

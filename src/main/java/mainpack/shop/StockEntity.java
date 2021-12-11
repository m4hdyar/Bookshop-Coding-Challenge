package mainpack.shop;

public class StockEntity {
    private String bookISBN;
    private int priceInCents;
    private int quantity;

    public StockEntity(String bookISBN, int priceInCents, int quantity) throws IllegalArgumentException{
        if(priceInCents<0 || quantity<0) throw new IllegalArgumentException("Price or Quantity can not be negative");
        this.bookISBN = bookISBN;
        this.priceInCents = priceInCents;
        this.quantity = quantity;
    }

    public String getBookISBN() {
        return bookISBN;
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

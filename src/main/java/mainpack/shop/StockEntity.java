package mainpack.shop;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return Objects.equals(bookISBN, that.bookISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookISBN);
    }

    public void updateWithStock(StockEntity newStockEntity) {
        quantity+=newStockEntity.getQuantity();
        priceInCents=newStockEntity.getPriceInCents();
    }
    public void deductQuantity(int testRequestedQuantity){
        if(quantity>=testRequestedQuantity){
            quantity-=testRequestedQuantity;
        }else{
            throw new IllegalStateException("Stock Entity cannot have negative value");
        }
    }
}

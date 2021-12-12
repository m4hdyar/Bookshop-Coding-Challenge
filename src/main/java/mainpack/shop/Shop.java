package mainpack.shop;

import mainpack.book.BookISBNSet;
import mainpack.book.Genre;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Shop {
    private final int ID;
    private final String name;
    private long sales;

    private final Set<StockEntity> stockSet = new HashSet<>();

    private final BookISBNSet bookISBNSet;

    public Shop(BookISBNSet bookISBNSet, int ID, String name, long sales) {
        this.ID = ID;
        this.name = name;
        this.sales = sales;
        this.bookISBNSet=bookISBNSet;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public long getSales() {
        return sales;
    }

    public int getStockSetSize() {
        return stockSet.size();
    }

    private StockEntity getStockEntityByISBN(String bookIsbn){
        for (StockEntity element : stockSet) {
            if (element.getBookISBN().equals(bookIsbn)) return element;
        }
        return null;
    }

    public boolean isISBNExistInThisShop(String bookIsbn){
        StockEntity stockEntity = getStockEntityByISBN(bookIsbn);

        return stockEntity != null;
    }
    public int getBookPrice(String bookIsbn){
        StockEntity stockEntity = getStockEntityByISBN(bookIsbn);

        if (stockEntity != null) {
            return stockEntity.getPriceInCents();
        }else{
            throw new NullPointerException("ISBN Does not exists!");
        }
    }
    public int getBookQuantity(String bookIsbn) {
        StockEntity stockEntity = getStockEntityByISBN(bookIsbn);

        if (stockEntity != null) {
            return stockEntity.getQuantity();
        }else{
            throw new NullPointerException("ISBN Does not exists!");
        }
    }

    public void addStockEntityToStock(StockEntity newStockEntity) throws IllegalArgumentException {
        StockEntity existingStockEntity = getStockEntityByISBN(newStockEntity.getBookISBN());
        if(existingStockEntity==null){
            if(bookISBNSet.getBookByISBN(newStockEntity.getBookISBN())!=null){
                stockSet.add(newStockEntity);
            }else{
                throw new IllegalArgumentException("ISBN Is not valid");
            }
        }else{
            existingStockEntity.updateWithStock(newStockEntity);
        }
    }


    public void addToSales(int amount)throws IllegalArgumentException{
        if(amount>=0) {
            sales += amount;
        }else{
            throw new IllegalArgumentException("Amount of money must be positive!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return ID == shop.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public void buy(String bookISBN, int requestedQuantity) {
        StockEntity stockEntity = getStockEntityByISBN(bookISBN);
        if (stockEntity == null) throw new NullPointerException("You can't buy a book that does not exist in a shop!");
        if(stockEntity.getQuantity()==requestedQuantity){
            stockSet.remove(stockEntity);
        }else{
            stockEntity.deductQuantity(requestedQuantity);
        }
        this.sales += (long) requestedQuantity *stockEntity.getPriceInCents();
    }

    public Set<StockEntity> getNewFilteredSet(Genre genre){
        Set<StockEntity> newFilteredSet = new HashSet<>();
        for (StockEntity element : stockSet) {
            String bookISBN = element.getBookISBN();
            Genre elementGenre = bookISBNSet.getBookByISBN(bookISBN).getBookGenre();
            if (elementGenre==genre) newFilteredSet.add(element);
        }
        return newFilteredSet;

    }
}

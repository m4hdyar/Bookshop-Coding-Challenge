package mainpack.shop;

import mainpack.book.BookISBNSet;

import java.util.HashSet;
import java.util.Set;

public class Shop {
    private int ID;
    private String name;
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


}

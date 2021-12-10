import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;

public class Shop {
    private int ID;
    private String name;
    private long sales;

    private final Set<StockEntity> stockSet = new HashSet<>();


    public Shop(int ID, String name, long sales) {
        this.ID = ID;
        this.name = name;
        this.sales = sales;
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

    public void addStockEntityToStock(StockEntity testStockEntity) {
        stockSet.add(testStockEntity);
    }


    public void addToSales(int amount)throws IllegalArgumentException{
        if(amount>=0) {
            sales += amount;
        }else{
            throw new IllegalArgumentException("Amount of money must be positive!");
        }
    }


}

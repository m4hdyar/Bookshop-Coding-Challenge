public class Shop {
    private int ID;
    private String name;
    private long sales;

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

    public void addToSales(int amount)throws IllegalArgumentException{
        if(amount>=0) {
            sales += amount;
        }else{
            throw new IllegalArgumentException("Amount of money must be positive!");
        }
    }
}

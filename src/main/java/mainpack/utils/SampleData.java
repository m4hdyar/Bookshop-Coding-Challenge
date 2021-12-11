package mainpack.utils;
//I just provide sample data when you need.
public abstract class SampleData {
    //Books
    private static final String[] validISBNs = {"978-3608963762", "978-3442267747", "978-758245159", "978-3841335180", "978-3442267819"};
    private static final String[] invalidISBNs = {"978-3333333333", "978-4444444444", "978-555555555", "978-6666666666", "978-7777777777"};
    private static final String[] bookNames = {"TestBook1", "TestBook2", "TestBook3", "TestBook4", "TestBook5"};
    private static final String[] bookGenres = {"Adventure","Adventure","Adventure","Adventure","Adventure"};
    private static final int[] bookPageNumbers = {100,250,351,400,532};

    private static final int[] stockPriceInCents = {200,300,400,600,700};
    private static final int[] stockQuantity = {100, 300, 400, 700, 800};

    //Customers
    private static final String[] customerFirstNames = {"Customer1", "Customer2", "Customer3", "Customer4", "Customer5"};
    private static final String[] customerLastNames = {"LastName1", "LastName2", "LastName3", "LastName4", "LastName5"};
    private static final int[]  customerMoneyInCents={100,1000,10000,10000,100000};

    //Shops
    private static final int[] shopID = {1,2,3,4,5};
    private static final String[] shopName={"Shop1","Shop2","Shop3","Shop4","Shop5"};
    private static final long[] shopSalesInCents={0,10000,20000,30000,40000,50000};

    //mainpack.book.Book methods
    public static String getValidISBN(int index){
        return new String(validISBNs[index]);
    }
    public static String getInvalidISBN(int index){
        return new String(invalidISBNs[index]);
    }
    public static String getBookName(int index){
        return new String(bookNames[index]);
    }
    public static String getBookGenre(int index){
        return new String(bookGenres[index]);
    }
    public static int getBookPageNumbers(int index){
        return bookPageNumbers[index];
    }
    public static int getStockPriceInCents(int index){
        return stockPriceInCents[index];
    }
    public static int getStockQuantity(int index){
        return stockQuantity[index];
    }

    //mainpack.Customer methods
    public static String getCustomerFirstName(int index){
        return new String(customerFirstNames[index]);
    }
    public static String getCustomerLastName(int index){
        return new String(customerLastNames[index]);
    }
    public static int getCustomerMoneyInCents(int index){
        return customerMoneyInCents[index];
    }

    //mainpack.shop.Shop methods
    public static int getShopID(int index){
        return shopID[index];
    }
    public static String getShopName(int index){
        return new String(shopName[index]);
    }
    public static long getShopSalesInCents(int index){
        return shopSalesInCents[index];
    }

//    public static void addAllValidISBNsToBookISBNSet(mainpack.book.BookISBNSet bookISBNSet){
//
//    }
}

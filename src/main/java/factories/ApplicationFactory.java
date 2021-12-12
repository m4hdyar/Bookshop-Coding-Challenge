package factories;

import mainpack.book.BookISBNSet;
import mainpack.customer.CustomerSet;
import mainpack.shop.ShopSet;
import mainpack.transaction.TransactionProcessor;
import mainpack.utils.SampleData;

public class ApplicationFactory {

    private final BookISBNSet bookISBNSet;
    private final ShopSet shopSet;
    private final TransactionProcessor transactionProcessor;
    private final ShopFactory shopFactory;
    private final CustomerFactory customerFactory;

    public ApplicationFactory() {
        bookISBNSet = new BookISBNSet();
        transactionProcessor = new TransactionProcessor(bookISBNSet);
        shopSet = new ShopSet();
        shopFactory = new ShopFactory(bookISBNSet,shopSet);
        customerFactory = new CustomerFactory();
    }

    public ShopSet getShopSet(){
        return shopSet;
    }

    public ShopFactory getShopFactory(){return shopFactory;}

    public CustomerFactory getCustomerFactory(){return customerFactory;}

    public TransactionProcessor getTransactionProcessor() {return transactionProcessor;    }

    public void addSampleDataToISBNSet(){
        SampleData.addAllValidISBNsToBookISBNSet(bookISBNSet);
    }

}

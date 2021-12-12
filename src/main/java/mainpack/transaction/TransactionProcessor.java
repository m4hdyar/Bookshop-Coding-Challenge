package mainpack.transaction;

import mainpack.book.Book;
import mainpack.book.BookISBNSet;
import mainpack.customer.Customer;
import mainpack.shop.Shop;

public class TransactionProcessor {

    BookISBNSet bookISBNset;

    public TransactionProcessor(BookISBNSet bookISBNset) {
        this.bookISBNset = bookISBNset;
    }

    private boolean isISBNCorrect(String bookISBN){
        return bookISBNset.getBookByISBN(bookISBN) != null;
    }
    private boolean isQuantityEnough(int bookQuantity,int requestedQuantity) {
        return bookQuantity >= requestedQuantity;
    }
    private boolean isCustomerMoneyEnough(int bookPrice, int requestedQuantity, int customerMoney) {
        return customerMoney >= bookPrice*requestedQuantity;
    }

    public void transact(Book book, Shop shop, Customer customer, int requestedQuantity) throws InsufficientMoneyException,InsufficientBookQuantityException,IncorrectISBNException{
        if(!isISBNCorrect(book.getBookISBN())){
            throw new IncorrectISBNException("Book ISBN is invalid!");
        }else if(!shop.isISBNExistInThisShop(book.getBookISBN())){
            throw new InsufficientBookQuantityException("The shop does not have the book or the quantity is not enough!");
        }

        int bookQuantity=shop.getBookQuantity(book.getBookISBN());
        if(!isQuantityEnough(bookQuantity,requestedQuantity)){
            throw new InsufficientBookQuantityException("The shop does not have the book or the quantity is not enough!");
        }

        int moneyToDeductInCents=shop.getBookPrice(book.getBookISBN())*requestedQuantity;
        int bookPrice=shop.getBookPrice(book.getBookISBN());
        int customerMoney=customer.getMoneyInCents();

        if(!isCustomerMoneyEnough(bookPrice,requestedQuantity,customerMoney)){
            throw new InsufficientMoneyException("Customer has not enough money!");
        }else{
            doTransaction(book, shop, customer, requestedQuantity, moneyToDeductInCents);
        }
    }

    private void doTransaction(Book book, Shop shop, Customer customer, int requestedQuantity, int moneyToDeductInCents) {
        shop.buy(book.getBookISBN(), requestedQuantity);
        customer.deductMoney(moneyToDeductInCents);
    }


}

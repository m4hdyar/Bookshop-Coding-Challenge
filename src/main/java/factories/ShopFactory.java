package factories;

import mainpack.book.BookISBNSet;
import mainpack.shop.Shop;
import mainpack.shop.ShopSet;

public class ShopFactory {
    private final BookISBNSet bookISBNSet;
    private final ShopSet shopSet;

    protected ShopFactory(BookISBNSet bookISBNSet, ShopSet shopSet) {
        this.bookISBNSet = bookISBNSet;
        this.shopSet = shopSet;
    }

    public Shop getNewShop(int shopID, String shopName){
        Shop newShop=new Shop(bookISBNSet,shopID,shopName,0);
        shopSet.addShop(newShop);
        return newShop;
    }
    public Shop getNewShop(int shopID,String shopName,long shopDefaultSales){
        Shop newShop=new Shop(bookISBNSet,shopID,shopName,shopDefaultSales);
        shopSet.addShop(newShop);
        return newShop;
    }
}

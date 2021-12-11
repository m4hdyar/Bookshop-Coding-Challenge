package mainpack.shop;

import java.util.HashSet;
import java.util.Set;

public class ShopSet {

    private final Set<Shop> shopSet = new HashSet<>();
    public boolean addShop(Shop shop) {
        return shopSet.add(shop);
    }
    public Shop getShopByID(int shopID) {
        for (Shop element : shopSet) {
            if (element.getID()==shopID) return element;
        }
        return null;
    }
    public void removeShop(Shop shop) {
        shopSet.remove(shop);
    }

    public int getSetSize() {
        return shopSet.size();
    }

}
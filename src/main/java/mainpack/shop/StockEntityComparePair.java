package mainpack.shop;

import java.util.Objects;

public class StockEntityComparePair {
    public final StockEntity firstStockEntity;
    public final StockEntity secondStockEntity;

    public StockEntityComparePair(StockEntity firstStockEntity, StockEntity secondStockEntity) {
        this.firstStockEntity = firstStockEntity;
        this.secondStockEntity = secondStockEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntityComparePair that = (StockEntityComparePair) o;
        return (Objects.equals(firstStockEntity, that.firstStockEntity) && Objects.equals(secondStockEntity, that.secondStockEntity)) ||
                (Objects.equals(firstStockEntity, that.secondStockEntity) && Objects.equals(secondStockEntity, that.firstStockEntity)) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstStockEntity, secondStockEntity);
    }
}

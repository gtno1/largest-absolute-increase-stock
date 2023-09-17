package org.example;

public class StockAbsIncrease {

    private StockValue firstStockRecord;

    private StockValue lastStockRecord;


    public void addStockValue(StockValue stockValue) {
        if (firstStockRecord == null) {
            firstStockRecord = stockValue;
            return;
        }

        if (stockValue.getDate().before(firstStockRecord.getDate())) {
            firstStockRecord = stockValue;
            return;
        }

        if (lastStockRecord == null) {
            lastStockRecord = stockValue;
            return;
        }

        if (stockValue.getDate().after(lastStockRecord.getDate())) {
            lastStockRecord = stockValue;
        }
    }

    public Double getAbsIncreaseValue() {
        if (firstStockRecord == null || lastStockRecord == null || firstStockRecord.getValue() > lastStockRecord.getValue()) {
            return null;
        }
        return lastStockRecord.getValue() - firstStockRecord.getValue();
    }
}

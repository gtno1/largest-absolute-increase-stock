package org.example;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockPricesAnalyserTest {

    private  StockPricesAnalyser stockPricesAnalyser;

    @Before
    public  void init() {
        stockPricesAnalyser = new StockPricesAnalyser();
    }

    @Test
    public void getValidAbsoluteStockIncreaseValue() throws Exception{
        String validResult = stockPricesAnalyser.calculateAbsoluteIncrease("ValidValues.csv");
        System.out.println(validResult);
        Assert.assertEquals("Company : DLV, absolute increased value : 58.32", validResult);
    }

    @Test
    public void getStockAbsIncrease() throws Exception{
        String invalidResult = stockPricesAnalyser.calculateAbsoluteIncrease("InvalidValues.csv");
        System.out.println(invalidResult);
        Assert.assertEquals("nil", invalidResult);

    }
}
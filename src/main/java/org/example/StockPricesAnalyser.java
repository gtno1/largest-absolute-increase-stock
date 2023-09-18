package org.example;

import com.google.common.base.Preconditions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stock price analyser is used to load the stock prices value in csv file,
 * and calculate stock with the largest absolute increase from its first recording to its last recording.
 */
public class StockPricesAnalyser {
    private static final String NAME = "Name";
    private static final String DATE = "Date";
    private static final String NOTES = "notes";
    private static final String VALUE = "Value";
    private static final String CHANGE = "Change";

    public static final String RESULT_TEMPLATE = "Company : {0}, absolute increased value : {1}";
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    private Map<String, StockAbsIncrease> values;

    private String stockName = "";
    private double absIncreaseValue = -1.0d;

    public StockPricesAnalyser() {
        this.values = new HashMap<>();
    }

    public String calculateAbsoluteIncrease(String valueFilePath) throws IOException {
        Preconditions.checkArgument(!StringUtils.isBlank(valueFilePath), "The stock value file path cannot be null or empty!");
        cleanValues();

        Iterable<CSVRecord> records = loadStockPriceValues(valueFilePath);

        for (CSVRecord csvRecord : records) {
            Optional<StockValue> validStockValue = getValidStockValue(csvRecord);

            if (validStockValue.isPresent()) {
                addStockValue(validStockValue.get());
            }
        }

        return getStockAbsIncrease();
    }

    private Iterable<CSVRecord> loadStockPriceValues(String valueFilePath) throws IOException{
        String[] headers = {NAME, DATE, NOTES, VALUE, CHANGE};

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .build();

        File valueFile = new File(StockPricesAnalyser.class.getClassLoader().getResource(valueFilePath).getFile());

        Reader reader = new FileReader(valueFile);

        return csvFormat.parse(reader);
    }


    public String getStockAbsIncrease() {

        for (Map.Entry<String, StockAbsIncrease> entry : values.entrySet()) {
            String name = entry.getKey();
            Double stockAbsIncreaseValue = entry.getValue().getAbsIncreaseValue();

            if (stockAbsIncreaseValue != null && stockAbsIncreaseValue > absIncreaseValue) {
                stockName = name;
                absIncreaseValue = stockAbsIncreaseValue;
            }
        }

        if (StringUtils.isEmpty(stockName) || absIncreaseValue < 0) {
            return "nil";
        }

        return MessageFormat.format(RESULT_TEMPLATE, stockName, absIncreaseValue);
    }


    private void addStockValue(StockValue value) {
        String name = value.getName();
        StockAbsIncrease stockAbsIncrease = values.getOrDefault(name, new StockAbsIncrease());

        stockAbsIncrease.addStockValue(value);

        Double stockAbsIncreaseValue = stockAbsIncrease.getAbsIncreaseValue();

//        if (stockAbsIncreaseValue != null && stockAbsIncreaseValue > absIncreaseValue) {
//            absIncreaseValue = stockAbsIncreaseValue;
//            stockName = name;
//        }

        values.put(name, stockAbsIncrease);
    }

    private Optional<StockValue> getValidStockValue(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return Optional.empty();
        }

        StockValue stockValue = new StockValue();

        String name = csvRecord.get(NAME);
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        stockValue.setName(name.trim());

        String dateString = csvRecord.get(DATE);
        if (StringUtils.isEmpty(dateString)) {
            return Optional.empty();
        }

        try {
            Date date = formatter.parse(dateString.trim());
            stockValue.setDate(date);
        } catch (ParseException exception) {
            return Optional.empty();
        }

        String priceString = csvRecord.get(VALUE);
        if (StringUtils.isEmpty(priceString) || !NumberUtils.isCreatable(priceString)) {
            return Optional.empty();
        }

        double priceValue = NumberUtils.createDouble(priceString);
        stockValue.setValue(priceValue);

        stockValue.setNotes(csvRecord.get(NOTES));
        stockValue.setChange(csvRecord.get(CHANGE));

        return Optional.of(stockValue);
    }

    private void cleanValues() {
        values.clear();
    }

    public static void main(String[] args) throws IOException {
        StockPricesAnalyser stockPricesAnalyser = new StockPricesAnalyser();
        String result = stockPricesAnalyser.calculateAbsoluteIncrease("values.csv");
        System.out.println(result);
    }
}

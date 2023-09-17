# largest-absolute-increase-stock

## Progrom Description
The program is used to load stock prices values including name, date, prices, and other information firstly, clean the invalid stock value, and then calculate the stock with the largest absolute increase from its first recording to its last recording. This is complicated by the file being unsorted, and having lots of null values, with non-standard null entries (unknown, NA, N/A, UNKOWN, etc).

The whole propram is writen in Java, and built by Maven. To run the program, you need to ensure you have JDK and maven installed in your computer. 


## Program Structure

There are three Java class. 
1. The StockValue represents the stock values including name, date, notes, value, and change.
2. The StockAbsIncrease represents the stock value in the first recording day and last recording day.
3. The StockPricesAnalyser has the most of the code to load values csv file and calculate the  stock with the largest absolute increase from its first recording to its last recording.

## How to run the program.

Download the program from github, open it in IntelliJ IDEA, go to the StockPricesAnalyser, and run the main method. 


## Performace. 
The time complexity is O(n), because we calculate the absolute increase for each stock during the data load, and never use any sort function. 

The space complexity is O(n), because we load the all valid stock data.

## How to run unit test

There are two tests to test the valid output and invalid output. 

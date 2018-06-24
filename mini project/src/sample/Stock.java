package sample;

import java.io.Serializable;

public class Stock implements Serializable{
    // instance variables - replace the example below with your own
    private double price;
    private String code;
    private String company;
    private String description;
    private double status;
    private int sentiment;
    private double[] stockHistory = {504, 563, 605, 626, 679, 704, 707, 941, 989, 1003, 1015, 1079, 1083, 1089, 1286, 1343, 1588, 1598, 1697, 1842};

    /**
     * Constructor for objects of class Stock
     */
    public Stock(double price, String code, String company, String description) {
        this.price = price;
        this.code = code;
        this.company = company;
        this.description = description;
        this.status = 0.00;
        this.sentiment = 0;
    }

    public Stock(double price, String code, String company, String description, double status, int sentiment) {
        this.price = price;
        this.code = code;
        this.company = company;
        this.description = description;
        this.status = status;
        this.sentiment = sentiment;
    }

    public void addHistory(double d) {
        //move all stocks up one
        for (int i = 0; i < stockHistory.length-1; i++) {
            stockHistory[i] = stockHistory[i+1];
        }
        //add new stock to end
        stockHistory[19] = d;
    }

    public double[] getStockHistory() {
        return stockHistory;
    }

    public void setStockHistory(double[] d) {
        this.stockHistory = d;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double p) {
        price = p;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String c) {
        code = c;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String c) {
        company = c;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }


    /*
        Update stock price, status, sentiment (coming soon), stock history
     */
    public void updateStock()
    {
        addHistory(getPrice());
        setPrice(Math.round(Math.abs(Math.random() > 0.5 ? getPrice() + Math.random() * 100 : getPrice() - Math.random() * 100)));
        addHistory(getPrice());
        setStatus(Math.round(getStockHistory()[18] - getPrice() * 100));
        setSentiment(Math.random() > 0.5 ? getSentiment() + (int) Math.random() * getSentiment() : getSentiment() - (int) Math.random() * getSentiment());
    }
}
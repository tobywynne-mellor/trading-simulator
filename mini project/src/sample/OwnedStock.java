package sample;

import java.io.Serializable;

public class OwnedStock extends Stock implements Serializable{

    // instance variables - replace the example below with your own

    private double priceBoughtAt;
    private double valueChange;
    private int quantityOwned = 0;

    /**
     * Constructor for objects of class OwnedStock
     */
    public OwnedStock(double price, String code, String company, String description, double status, int sentiment)
    {
        super(price,code,company,description, status, sentiment);
        this.priceBoughtAt = price;
        this.valueChange = 0;
        this.quantityOwned = 0;
    }

    public double getValueChange() {
        return valueChange;
    }

    public void setValueChange(double valueChange) {
        this.valueChange = valueChange;
    }

    public double getPriceBoughtAt() {
        return priceBoughtAt;
    }

    public void setPriceBoughtAt(double priceBoughtAt) {
        this.priceBoughtAt = priceBoughtAt;
    }

    public int getQuantityOwned() {
        return quantityOwned;
    }

    public void setQuantityOwned(int quantityOwned) {
        this.quantityOwned = quantityOwned;
    }

    /*
        Update stock price, status, sentiment (coming soon),
     */
    @Override
    public void updateStock()
    {
        setPriceBoughtAt(Math.round((getPriceBoughtAt() + getPrice()) / 2));
        setPrice(Math.round(Math.random() > 0.5 ? getPrice() + Math.random() * 100 : getPrice() - Math.random() * 100 ));
        addHistory(getPrice());
        setStatus((getStockHistory()[18] - getPrice())/getStockHistory()[18] * 100);
        setSentiment(Math.round(Math.random() > 0.5 ? getSentiment() + (int) Math.random() * getSentiment() : getSentiment() - (int) Math.random() * getSentiment()));
        setValueChange(Math.round(-1 * (getPriceBoughtAt() - getPrice())));
    }

    public void inheritPrice(double price)
    {
        setPrice(price);
        setPriceBoughtAt(Math.round(getPriceBoughtAt() + getPrice())/2);
        addHistory(getPrice());
        setStatus((getStockHistory()[18] - getPrice())/getStockHistory()[18] * 100);
        setSentiment(Math.round(Math.random() > 0.5 ? getSentiment() + (int) Math.random() * getSentiment() : getSentiment() - (int) Math.random() * getSentiment()));
        setValueChange(Math.round(-1 * (getPriceBoughtAt() - getPrice())));
    }


    public static double difference(double d1, double d2)
    {
        if(d1 > d2)
            return d1 - d2;
        else if(d2 > d1)
            return d2 - d1;

        return 0;
    }
}

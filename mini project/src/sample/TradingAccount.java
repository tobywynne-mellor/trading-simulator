package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class TradingAccount extends Account implements Serializable{

    private transient ObservableList<Stock> marketList = stockList();
    private transient ObservableList<OwnedStock> owned = FXCollections.observableArrayList();
    private double profit = 0;
    private transient LifeEvent lifeEvent;
    private ArrayList<OwnedStock> saveList;
    private ArrayList<Stock> saveMarket;
    private int period;
    private double[] profitHistory = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public TradingAccount(Double b)
    {
        super(b);
        this.period = 0;
    }

    public ObservableList<Stock> getMarketList() {
        return marketList;
    }

    public void setMarketList(ObservableList<Stock> marketList) {
        this.marketList = marketList;
    }

    private String status;

    public void setOwned(ObservableList<OwnedStock> owned) {
        this.owned = owned;
    }

    public void buyStock(Stock stock, int amount)
    {
        OwnedStock os = convertStock(stock);
        if(findStock(os, owned) != null)
        {
            //if stock is present in owned
            OwnedStock foundStock = findStock(os, owned);
            foundStock.setQuantityOwned(foundStock.getQuantityOwned() + amount);
            setBalance(getBalance() - (foundStock.getPrice() * amount));
            foundStock.setPriceBoughtAt((foundStock.getPriceBoughtAt() + foundStock.getPrice())/2);
            foundStock.setValueChange(-1 * (foundStock.getPriceBoughtAt() - foundStock.getPrice()));
            generateStatus(foundStock, "bought");
            setProfit(getBalance() - 10000.0);
        }
        else
        {
            //if stock isn't present in owned
            os.setQuantityOwned(amount);
            setBalance(getBalance() - (os.getPrice() * amount));
            os.setPriceBoughtAt((os.getPriceBoughtAt() + os.getPrice())/2);
            os.setValueChange(-1 * (os.getPriceBoughtAt() - os.getPrice()));
            owned.add(os);
            generateStatus(os, "Bought");
            setProfit(getBalance() - 10000.0);
        }
    }

    public void sellStock(OwnedStock stock, int amount)
    {

        OwnedStock os = findStock(stock, owned);

        if(os.getQuantityOwned() - amount > 0)
        {
            os.setQuantityOwned(os.getQuantityOwned() - amount);
            setBalance(getBalance() + (os.getPrice() * amount));
            setProfit(getBalance() - 10000.0);
            generateStatus(os, "sold");
        }
        else if(os.getQuantityOwned() - amount == 0)
        {
            os.setQuantityOwned(0);
            setBalance(getBalance() + (os.getPrice() * amount));
            setProfit(getBalance() - 10000.0);
            owned.remove(os);
            generateStatus(os, "sold");
        }
    }

    public ObservableList<OwnedStock> getOwned() {
        return owned;
    }


    public OwnedStock convertStock(Stock s){
        OwnedStock o = new OwnedStock(s.getPrice(), s.getCode(), s.getCompany(), s.getDescription(), s.getStatus(), s.getSentiment());
        return o;
    }

    public static OwnedStock findStock(OwnedStock stock, ObservableList<OwnedStock> list)
    {
        for(OwnedStock x : list)
        {
            if(x.getCode() == stock.getCode())
            {
                return x;
            }
        }
        return null;
    }

    public void printPortfolio()
    {
        for (OwnedStock x: owned) {
            System.out.println(x.getCode());
        }
    }

    public static ObservableList<Stock> stockList(){
        Stock AAPL = new Stock(173.45,"AAPL","Apple Inc.","Apple was founded by Steve Jobs.", 1.00,12);
        Stock GOOGL = new Stock(1179.38,"GOOGL","Alphabet Inc Class A","Larry Page is the co-founder of Google.",3.00,23);
        Stock AMZN = new Stock(1372.77,"AMZN","Amazon.com, Inc.","Jeff Bezos founded Amazon.",0.11,1);

        ObservableList<Stock> stocks = FXCollections.observableArrayList();
        stocks.addAll(AAPL,GOOGL,AMZN);
        return stocks;
    }

    private void generateStatus(Stock s, String action)
    {
        String direction = s.getStatus() > 0 ? "+" : "-";
        status = s.getCode() + " " + direction + s.getStatus() + "% was " + action + " for " + s.getPrice();
    }

    public String getStatus()
    {
        return status;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public LifeEvent getLifeEvent() {
        return lifeEvent;
    }

    public void setLifeEvent(LifeEvent lifeEvent) {
        this.lifeEvent = lifeEvent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LifeEvent getLifeEvents(){
        String[] naturalDisasterBusinesses = {"Technology", "Manufacturing", "Clothing"};
        String[] socialDisasterBusinesses = {"Education"};
        String[] economicDisasterBusinesses = {"Financial"};
        NaturalDisaster nd1 = new NaturalDisaster("Mount Fuji has Erupted.",false,naturalDisasterBusinesses,27000,13000, 13000000.0);
        SocialDisaster sd1 = new SocialDisaster("Lecturers Strike at QM.",false,socialDisasterBusinesses,27000);
        EconomicDisaster ed1 = new EconomicDisaster("Bitcoin falls to 0",false,economicDisasterBusinesses,-12.0);
        LifeEvent[] arr = {nd1,sd1,ed1};

        if(Math.random()>0.3){
            return arr[1];
        }else if(Math.random()>0.6){
            return arr[2];
        }
        return arr[0];
    }


    public ArrayList<OwnedStock> makeSaveList(ObservableList<OwnedStock> os) {
        ArrayList<OwnedStock> sl = new ArrayList<>();

        for (OwnedStock s:os) {
            sl.add(s);
        }

        return sl;
    }

    public ObservableList<OwnedStock> makeLoadList(ArrayList<OwnedStock> os) {
        ObservableList<OwnedStock> ll = FXCollections.observableArrayList();

        for (OwnedStock s:os) {
            ll.add(s);
        }
        return ll;
    }

    public void setSaveList(ArrayList<OwnedStock> saveList) {
        this.saveList = saveList;
    }

    public ArrayList<OwnedStock> getSaveList() {
        return saveList;
    }

    public ArrayList<Stock> getSaveMarket() {
        return saveMarket;
    }

    public void setSaveMarket(ArrayList<Stock> saveMarket) {
        this.saveMarket = saveMarket;
    }

    public ArrayList<Stock> makeMarketSaveList(ObservableList<Stock> os) {
        ArrayList<Stock> sl = new ArrayList<>();

        for (Stock s:os) {
            sl.add(s);
        }

        return sl;
    }

    public ObservableList<Stock> makeMarketLoadList(ArrayList<Stock> os) {
        ObservableList<Stock> ll = FXCollections.observableArrayList();
        ll.addAll(os);
        return ll;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setProfitHistory(double[] profitHistory) {
        this.profitHistory = profitHistory;
    }

    public double[] getProfitHistory() {
        return profitHistory;
    }

    public void addProfit() {

        for (int i = 0; i < profitHistory.length-1; i++) {
            profitHistory[i] = profitHistory[i+1];
        }

        //add new stock to end
        profitHistory[19] = profit;
    }

}
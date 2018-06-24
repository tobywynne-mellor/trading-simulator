package sample;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Controller implements Initializable, convertStocks{

    //market
    @FXML private TableView<Stock> tableView;
    @FXML private TableColumn<Stock, String> buyName;
    @FXML private TableColumn<Stock, Double> buyPrice;
    @FXML private TableColumn<Stock, Double> buyStatus;
    @FXML private TableColumn<Stock, Integer> buySentiment;

    //portfolio
    @FXML private TableView<Stock> portfolioTable;
    @FXML private TableColumn<Stock, String> sellName;
    @FXML private TableColumn<Stock, Double> sellPrice;
    @FXML private TableColumn<Stock, Double> sellStatus;
    @FXML private TableColumn<Stock, Integer> sellSentiment;
    @FXML private TableColumn<Stock, Integer> sellQuantity;
    @FXML private TableColumn<Stock, Double> priceBoughtAt;
    @FXML private TableColumn<Stock, Double> valueChange;

    //buttons
    @FXML private Button buyButton;
    @FXML private Button sellButton;
    @FXML private Button updateBut;

    //charts
    @FXML private LineChart<Integer,Double> stockChart;
    @FXML private Label graphTitle;
    @FXML private LineChart<Integer,Double> progressChart;


    //status bar
    @FXML private Label statusBar;
    @FXML private TextArea commentaryBox;
    @FXML private Text time;


    //account
    private TradingAccount tradingAccount = new TradingAccount(10000.00);
    @FXML private Text balance;
    @FXML private Text profit;

    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
    Calendar calobj = Calendar.getInstance();
    private Boolean hasBeenLoaded = false;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //market
        buyName.setCellValueFactory(new PropertyValueFactory<Stock,String>("code"));
        buyPrice.setCellValueFactory(new PropertyValueFactory<Stock,Double>("price"));
        buyStatus.setCellValueFactory(new PropertyValueFactory<Stock,Double>("status"));
        buySentiment.setCellValueFactory(new PropertyValueFactory<Stock,Integer>("sentiment"));
        tableView.getItems().addAll(tradingAccount.getMarketList());

        //portfolio
        sellName.setCellValueFactory(new PropertyValueFactory<Stock,String>("code"));
        sellPrice.setCellValueFactory(new PropertyValueFactory<Stock,Double>("price"));
        sellStatus.setCellValueFactory(new PropertyValueFactory<Stock,Double>("status"));
        sellSentiment.setCellValueFactory(new PropertyValueFactory<Stock,Integer>("sentiment"));
        sellQuantity.setCellValueFactory(new PropertyValueFactory<Stock,Integer>("quantityOwned"));
        priceBoughtAt.setCellValueFactory(new PropertyValueFactory<Stock,Double>("priceBoughtAt"));
        valueChange.setCellValueFactory(new PropertyValueFactory<Stock,Double>("valueChange"));


        //buttons
        buyButton.setOnAction((ActionEvent event) -> {
            Stock stock = tableView.getSelectionModel().getSelectedItem();
            int quantity = 1;
            //open buybox.fxml and get quantity - must be >= quantityOwned
            if (stock != null)
            {
                tradingAccount.buyStock(stock, quantity);
            }
            portfolioTable.getItems().clear();
            portfolioTable.getItems().addAll(tradingAccount.getOwned());
            statusBar.setText(tradingAccount.getStatus());
            balance.setText("$"+Math.round(tradingAccount.getBalance()*100.0)/100.0);
            profit.setText("$"+Math.round(tradingAccount.getProfit()*100.0)/100.0);
        });

        sellButton.setOnAction(event -> {
            Stock stock = portfolioTable.getSelectionModel().getSelectedItem();
            int quantity = 1;
            //open sellbox.fxml and get quantity
            if (stock != null)
            {
                tradingAccount.sellStock(convertStock(stock), quantity);
            }
            portfolioTable.getItems().clear();
            portfolioTable.getItems().addAll(tradingAccount.getOwned());
            statusBar.setText(tradingAccount.getStatus());
            balance.setText("$"+Math.round(tradingAccount.getBalance()*100.0)/100.0);
            profit.setText("$"+Math.round(tradingAccount.getProfit()*100.0)/100.0);
        });

        updateBut.setOnAction(event -> {
            update();
            setGraph(findStock(graphTitle.getText()));
            tradingAccount.addProfit();
            setProgressChart(tradingAccount.getProfitHistory());
        });

        time.setText(df.format(calobj.getTime()));
    }

    public Stock findStock(String stockName)
    {
        for (Stock s:tradingAccount.getMarketList()) {
            if(s.getCode().equals(stockName)){
                return s;
            }
        }
        return null;
    }

    public void changeGraph()
    {
        Stock s = tableView.getSelectionModel().getSelectedItem();
        Stock p = portfolioTable.getSelectionModel().getSelectedItem();
        if(s != null)
        {
            setGraph(s);
        }else if(p != null)
        {
            setGraph(p);
        }
    }

    public void setGraph(Stock s)
    {
        if(s != null)
        {
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < s.getStockHistory().length; i++) {
                series.getData().add(new XYChart.Data<>(i,s.getStockHistory()[i]));
            }
            stockChart.getData().clear();
            stockChart.getData().addAll(series);
            graphTitle.setText(s.getCode());
            graphTitle.setVisible(true);
        }
    }

    public void setProgressChart(double[] profit)
    {
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < profit.length; i++) {
                series.getData().add(new XYChart.Data<>(i,profit[i]));
            }
            progressChart.getData().clear();
            progressChart.getData().addAll(series);
    }

    public void update()
    {
        for(Stock s : tradingAccount.getMarketList())
        {
            s.updateStock();
        }

        if(tradingAccount.getMarketList() != null && hasBeenLoaded){
            tradingAccount.setMarketList(tradingAccount.makeMarketLoadList(tradingAccount.getSaveMarket()));
        }

        for (OwnedStock s : tradingAccount.getOwned()) {
            s.setPrice(findStock(s.getCode()).getPrice());
            s.setSentiment(findStock(s.getCode()).getSentiment());
            s.setStatus(findStock(s.getCode()).getStatus());
            s.setValueChange(Math.round(-1 * (s.getPriceBoughtAt() - s.getPrice())));
            s.setPriceBoughtAt(Math.round(s.getPriceBoughtAt() + s.getPrice())/2);
        }


        time.setText(df.format(calobj.getTime()));
        tableView.getItems().clear();
        tableView.getItems().addAll(tradingAccount.getMarketList());
        portfolioTable.getItems().clear();
        portfolioTable.getItems().addAll(tradingAccount.getOwned());
        String comment = Math.random()>0.5 ? tradingAccount.getLifeEvents().summarise() : "";
        commentaryBox.appendText(comment);
    }


    public OwnedStock convertStock(Stock s){
        OwnedStock o = new OwnedStock(s.getPrice(), s.getCode(), s.getCompany(), s.getDescription(), s.getStatus(), s.getSentiment());
        return o;
    }

    public ObservableList<Stock> getStocks(){
        ObservableList<Stock> stocks = FXCollections.observableArrayList();

        Stock AAPL = new Stock(173.45,"AAPL","Apple Inc.","Apple was founded by Steve Jobs.", 1.00,12);
        Stock GOOGL = new Stock(1179.38,"GOOGL","Alphabet Inc Class A","Larry Page is the co-founder of Google.",3.00,23);
        Stock AMZN = new Stock(1372.77,"AMZN","Amazon.com, Inc.","Jeff Bezos founded Amazon.",0.11,1);

        stocks.addAll(AAPL, GOOGL, AMZN);

        return stocks;
    }

    public Stock[] getStocksArray(){

        Stock[] stocks = new Stock[3];
        Stock AAPL = new Stock(173.45,"AAPL","Apple Inc.","Apple was founded by Steve Jobs.", 1.00,12);
        Stock GOOGL = new Stock(1179.38,"GOOGL","Alphabet Inc Class A","Larry Page is the co-founder of Google.",3.00,23);
        Stock AMZN = new Stock(1372.77,"AMZN","Amazon.com, Inc.","Jeff Bezos founded Amazon.",0.11,1);

        stocks[0] = AAPL;
        stocks[1] = GOOGL;
        stocks[2] = AMZN;

        return stocks;
    }

    public void loadGame() {

        TradingAccount ta;
        try// If this doesn't work throw an exception
        {
            FileInputStream fileIn = new FileInputStream("tradingSimulatorSave.ser");// Read serial file.
            ObjectInputStream in = new ObjectInputStream(fileIn);// input the read file.
            ta = (TradingAccount) in.readObject();// allocate it to the object file already instanciated.
            in.close();//closes the input stream.
            fileIn.close();//closes the file data stream.

            tradingAccount = ta;

            tradingAccount.setOwned(tradingAccount.makeLoadList(tradingAccount.getSaveList()));
            tradingAccount.setMarketList(tradingAccount.makeMarketLoadList(tradingAccount.getSaveMarket()));
            hasBeenLoaded = true;
            tableView.setItems(tradingAccount.getMarketList());
            portfolioTable.getItems().clear();
            portfolioTable.getItems().addAll(tradingAccount.getOwned());
            statusBar.setText(tradingAccount.getStatus());
            balance.setText("$"+Math.round(tradingAccount.getBalance()*100.0)/100.0);
            profit.setText("$"+Math.round(tradingAccount.getProfit()*100.0)/100.0);

        } catch (IOException i)//exception stuff
        {
            i.printStackTrace();
        } catch (ClassNotFoundException c)//more exception stuff
        {
            System.out.println("Error");
            c.printStackTrace();
        }
    }

    public void saveGame()
    {
        tradingAccount.setSaveList(tradingAccount.makeSaveList(tradingAccount.getOwned()));
        tradingAccount.setSaveMarket(tradingAccount.makeMarketSaveList(tradingAccount.getMarketList()));
        //serialize objects
        try
        {
            FileOutputStream fileOut = new FileOutputStream("tradingSimulatorSave.ser");//creates a card serial file in output stream
            ObjectOutputStream out = new ObjectOutputStream(fileOut);//routs an object into the output stream.
            out.writeObject(tradingAccount);// we designate our array of cards to be routed
            out.close();// closes the data paths
            fileOut.close();// closes the data paths
            statusBar.setText("Progress saved to tradingSimulatorSave.ser at " + df.format(calobj.getTime()));
        }catch(IOException i)//exception stuff
        {
            i.printStackTrace();
        }
    }
}

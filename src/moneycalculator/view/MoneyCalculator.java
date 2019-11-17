package moneycalculator.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Month;
import moneycalculator.model.Currency;
import moneycalculator.model.CurrencyList;
import moneycalculator.model.CurrencyLoader;
import moneycalculator.model.ExchangeRate;
import moneycalculator.model.Money;

public class MoneyCalculator {
    private Money money;
    private ExchangeRate exchangerate;
    private Currency currencyTo, currencyFrom;
    private final CurrencyLoader currencyLoader;
    private final CurrencyList currencyList;
    private final String from, to;
    private String res;
    private static String sdate;
    private final double amount;
    
    public MoneyCalculator(CurrencyList currencyList, CurrencyLoader currencyLoader,double amount,String from,String to) throws IOException{
        this.currencyList = currencyList;
        this.currencyLoader = currencyLoader;
        this.amount = amount;
        this.from = from;
        this.to = to;
        res = "";
    }
    
    public String execute() throws IOException {
        currencyFrom = currencyList.get(from);
        money = new Money(amount,currencyFrom);
        currencyTo = currencyList.get(to);
        exchangerate = getExchangeRate(currencyFrom,currencyTo);
        if (exchangerate.getRate() >= 0){
            res = " " + money.getAmount() + money.getCurrency().getSymbol() + "  =  "
                    + money.getAmount() * exchangerate.getRate() + currencyTo.getSymbol();
        }
        return res;
    }
    
    private static ExchangeRate getExchangeRate(Currency from, Currency to) throws IOException{
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + from.getIsoCode());
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader (new InputStreamReader(connection.getInputStream()))){
            String line = reader.readLine();
            JsonParser parser = new JsonParser();
            JsonObject gsonObj = parser.parse(line).getAsJsonObject();
            
            JsonPrimitive datePrimitive = gsonObj.getAsJsonPrimitive("date");
            //String date = datePrimitive.getAsString();
            
            double result = -1;
            JsonObject x = gsonObj.getAsJsonObject("rates");
            for (Object key : x.keySet()) {
                String rate = (String) key;
                if (rate.equals(to.getIsoCode())) {
                    result = x.getAsJsonPrimitive(rate).getAsDouble();
                    //System.out.println(date);
                    sdate = datePrimitive.getAsString();
                    break;
                }
            }
            // fecha del PDF
            return new ExchangeRate(from, to, 
                    LocalDate.of(2018, Month.SEPTEMBER, 26), 
                    result);
        }
    }
    
    public String getDate(){
        return sdate;
    }
}

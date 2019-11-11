package moneycalculator;

import com.google.gson.JsonArray;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoneyCalculator {
    
    private Money money;
    private ExchangeRate exchangerate;
    private Currency currencyTo;
    private final CurrencyList currencyList;
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneycalculator = new MoneyCalculator();
        moneycalculator.execute();
    }
    
    public MoneyCalculator(){
        this.currencyList = new CurrencyList();
    }
    
    private void execute() throws IOException {
        try{
            input();
            process();
            output();
        }catch(IOException e){
            System.out.println("No se encuentra su divisa. ERROR: " + e);
        }
    }

    private void input() {
        System.out.println("Introduce una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        
        System.out.println("Introduce una divisa inicial: ");
        while(true){
            Currency currency = currencyList.get(scanner.next());
            money = new Money(amount,currency);
            if (currency != null) break;
            System.out.println("Divisa no conocida");
        }
        
        while(true){
        System.out.println("Introduce una divisa final: ");
        currencyTo = currencyList.get(scanner.next());
        if (currencyTo != null) break;
        System.out.println("Divisa no conocida");
        }
    }

    private void process() throws IOException {
        exchangerate = getExchangeRate(money.getCurrency(),currencyTo);
    }

    private void output() {
        if (exchangerate.getRate() == -1){
            System.out.println("Divisa no encontrada.");
        }else{
            System.out.println(money.getAmount() + money.getCurrency().getSymbol() + " = " 
                    + money.getAmount() * exchangerate.getRate() + currencyTo.getSymbol());
        }
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
            String date = datePrimitive.getAsString();
            
            double result = -1;
            JsonObject x = gsonObj.getAsJsonObject("rates");
            for (Object key : x.keySet()) {
                String rate = (String) key;
                if (rate.equals(to.getIsoCode())) {
                    result = x.getAsJsonPrimitive(rate).getAsDouble();
                    System.out.println(date);
                    break;
                }
            }
            // fecha del PDF
            return new ExchangeRate(from, to, 
                    LocalDate.of(2018, Month.SEPTEMBER, 26), 
                    result);
        }
    }
}

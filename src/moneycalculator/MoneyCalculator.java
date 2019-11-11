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
    private final Map<String,Currency> currencies = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneycalculator = new MoneyCalculator();
        moneycalculator.execute();
    }
    
    public MoneyCalculator(){
        currencies.put("USD", new Currency("USD", "Dólar Americano", "$"));
        currencies.put("EUR", new Currency("EUR", "Euros", "€"));
        currencies.put("GBP", new Currency("GBP", "Libras Esterlinas", "£"));
        currencies.put("CAD", new Currency("CAD","Dólar Canandiense","C$"));
        currencies.put("MXN", new Currency("MXN","Peso Mexicano","$"));
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
        Currency currency = currencies.get(scanner.next());
        money = new Money(amount,currency);
        
        System.out.println("Introduce una divisa final: ");
        currencyTo = currencies.get(scanner.next());
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

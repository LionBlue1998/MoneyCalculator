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
import java.util.Scanner;

public class MoneyCalculator {
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneycalculator = new MoneyCalculator();
        moneycalculator.execute();
    }
    
    double amount;
    double exchangerate;
    String currencyFrom;
    String currencyTo;
    
    
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
        amount = scanner.nextDouble();
        
        System.out.println("Introduce una divisa inicial: ");
        currencyFrom = scanner.next();
        
        System.out.println("Introduce una divisa final: ");
        currencyTo = scanner.next();
    }

    private void process() throws IOException {
        exchangerate = getExchangeRate(currencyFrom,currencyTo);
    }

    private void output() {
        if (exchangerate == -1){
            System.out.println("Divisa no encontrada.");
        }else{
            System.out.println(amount + " " + currencyFrom + " = " +
                    amount*exchangerate + " " + currencyTo);
        }
    }
    
    private static double getExchangeRate(String from, String to) throws IOException{
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + from);
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
                if (rate.equals(to)) {
                    result = x.getAsJsonPrimitive(rate).getAsDouble();
                    System.out.println(date);
                    break;
                }
            }
            return result;
        }
    }
}

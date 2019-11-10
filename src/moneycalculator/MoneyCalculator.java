package moneycalculator;

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

    public static void main(String[] args) throws Exception {
        System.out.println("Introduzca una cantidad en dólares: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        double exchangeRate = getExchangeRate("USD","EUR");
        System.out.println(amount + " USD equivale a " +
                           amount*exchangeRate + " EUR");
    }
    
    private static double getExchangeRate(String from, String to) throws IOException{
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + from);
        URLConnection connection = url.openConnection();
        try{
            BufferedReader reader = 
                new BufferedReader (new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            JsonParser parser = new JsonParser();
            JsonObject gsonObj = parser.parse(line).getAsJsonObject();
            String linel = line.substring(line.indexOf(to)+12,line.indexOf("}"));
            JsonPrimitive moneyPrimitive = gsonObj.getAsJsonPrimitive(to);
            int money = moneyPrimitive.getAsInt();
            return Double.parseDouble(linel);
        }catch(IOException exception){
            System.out.println("IOException encontrada: " + exception);
            return -1;
        }
    }
}

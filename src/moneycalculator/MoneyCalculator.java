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
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    
    private double amount;
    private double exchangeRate;

    private void execute() throws IOException {
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduzca una cantidad de dólares");
        Scanner scanner = new Scanner(System.in);
        amount = Double.parseDouble(scanner.next());
    }

    private void process() throws IOException {
        exchangeRate = getExchangeRate("USD", "EUR");
    }

    private void output() {
        System.out.println(amount + " USD equivale a " + amount * exchangeRate + " EUR");
    }
    
    /*private static double getExchangeRate(String from, String to) throws IOException{
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + from);
        URLConnection connection = url.openConnection();
        try{
            BufferedReader reader = 
                new BufferedReader (new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            JsonParser parser = new JsonParser();
            JsonObject gsonObj = parser.parse(line).getAsJsonObject();
            //String linel = line.substring(line.indexOf(to)+12,line.indexOf("}"));
            JsonPrimitive moneyPrimitive = gsonObj.getAsJsonPrimitive(to);
            double money = moneyPrimitive.getAsDouble();
            return money;
        }catch(IOException exception){
            System.out.println("IOException encontrada: " + exception);
            return -1;
        }
    }*/
    
    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" +
                    from + "_" + to + "&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to)+12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
}

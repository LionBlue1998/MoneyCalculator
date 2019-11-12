package moneycalculator.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import moneycalculator.model.Currency;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class CurrencyList {
    private final Map<String, Currency> currencies = new HashMap<>();
    private static final String filePath = "Common-Currency.json";

    public CurrencyList() throws FileNotFoundException, IOException {
        try {
            JsonParser parser = new JsonParser();
            JsonObject gsonObj = parser.parse(new FileReader(filePath)).getAsJsonObject();
            for (String key : gsonObj.keySet()) {
                JsonObject x = gsonObj.getAsJsonObject(key);
                String symbol = x.getAsJsonPrimitive("symbol").getAsString();
                String name = x.getAsJsonPrimitive("name").getAsString();
                String code = x.getAsJsonPrimitive("code").getAsString();
                add(new Currency(code,name,symbol));
            }
        } catch (Exception ex) {
            System.err.println("Error CurrencyList: "+ex.getMessage());
        }
    }

    private void add(Currency currency) {
        currencies.put(currency.getIsoCode(), currency);
    }
    
    public Currency get(String code) {
        return currencies.get(code.toUpperCase());
    }
}

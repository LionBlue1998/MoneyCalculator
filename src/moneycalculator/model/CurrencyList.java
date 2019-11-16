package moneycalculator.model;

import java.util.HashMap;
import java.util.Map;

public class CurrencyList {
    private final Map<String, Currency> currencies = new HashMap<>();

    public CurrencyList() {}
    
    public void add(Currency currency) {
        currencies.put(currency.getIsoCode(), currency);
    }
    
    public Currency get(String code) {
        return currencies.get(code.toUpperCase());
    }
}

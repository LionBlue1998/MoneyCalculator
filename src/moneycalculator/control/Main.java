package moneycalculator.control;
import moneycalculator.view.JFrameMain;
import java.io.IOException;
import moneycalculator.model.CurrencyList;
import moneycalculator.model.FileCurrencyLoader;

public class Main {
    
    public static void main(String[] args) throws IOException {
        CurrencyList currencyList = new CurrencyList();
        FileCurrencyLoader currencyLoader = new FileCurrencyLoader(currencyList);
        currencyLoader.load();
        JFrameMain jFrameMain = new JFrameMain(currencyLoader,currencyList);
    }
}

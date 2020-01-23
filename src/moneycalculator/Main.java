package moneycalculator;

import java.io.IOException;
import moneycalculator.control.CalculateCommand;
import moneycalculator.control.CancelCommand;
import moneycalculator.model.CurrencyList;
import moneycalculator.persistance.files.FileCurrencyLoader;
import moneycalculator.view.ui.MoneyDialogAmount;
import moneycalculator.view.ui.MoneyDialogBox;
import moneycalculator.view.ui.MoneyDisplay;

public class Main {
    
    public static void main(String[] args) throws IOException {
        CurrencyList currencyList = new CurrencyList();
        FileCurrencyLoader currencyLoader = new FileCurrencyLoader(currencyList);
        currencyLoader.load();
        
        MoneyDialogBox dialog = new MoneyDialogBox(currencyLoader);
        MoneyDialogAmount amount = new MoneyDialogAmount(dialog);
        MoneyDisplay display = new MoneyDisplay(dialog);
        
        dialog.addAmount(amount);
        dialog.addDisplay(display);
        
        MainFrame frame = new MainFrame(display,dialog, amount);
        frame.addCommand("Calculate", new CalculateCommand(currencyLoader,currencyList,display,dialog,amount));
        frame.addCommand("Cancel", new CancelCommand(display,amount));
    }
}

package moneycalculator.control;

import java.io.IOException;
import moneycalculator.model.CurrencyList;
import moneycalculator.persistance.files.FileCurrencyLoader;
import moneycalculator.view.MoneyCalculator;
import moneycalculator.view.ui.MoneyDialogAmount;
import moneycalculator.view.ui.MoneyDialogBox;
import moneycalculator.view.ui.MoneyDisplay;

public class CalculateCommand implements Command{
    
    private final FileCurrencyLoader currencyLoader;
    private final CurrencyList currencyList;
    private final MoneyDisplay display;
    private final MoneyDialogBox box;
    private final MoneyDialogAmount amount;

    public CalculateCommand(FileCurrencyLoader currencyLoader, CurrencyList currencyList, MoneyDisplay display, MoneyDialogBox box, MoneyDialogAmount amount) {
        
        this.currencyLoader = currencyLoader;
        this.currencyList = currencyList;
        this.display = display;
        this.box = box;
        this.amount = amount;
    }

    @Override
    public void toExecute() {
        try{
            MoneyCalculator moneycalculator = new MoneyCalculator(currencyList,
                                                                currencyLoader,
                                                                Double.parseDouble(amount.getAmount().getText()),
                                                                box.getFrom(),
                                                                box.getTo());
            display.display(moneycalculator.getCalculate());
        }catch(NumberFormatException nfe){display.display("");}catch (IOException ex) {}
    }
    
}

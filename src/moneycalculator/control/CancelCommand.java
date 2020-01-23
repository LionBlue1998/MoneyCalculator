package moneycalculator.control;

import moneycalculator.view.ui.MoneyDialogAmount;
import moneycalculator.view.ui.MoneyDialogAmount;
import moneycalculator.view.ui.MoneyDisplay;
import moneycalculator.view.ui.MoneyDisplay;

public class CancelCommand implements Command {
    
    private final MoneyDisplay display;
    private final MoneyDialogAmount amount;
    
    public CancelCommand(MoneyDisplay display, MoneyDialogAmount amount) {
        this.display = display;
        this.amount = amount;
    }
    
    @Override
    public void toExecute() {
        amount.setAmount("");
        display.display("");
    }
    
}

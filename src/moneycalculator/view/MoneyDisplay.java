package moneycalculator.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import moneycalculator.model.CurrencyList;
import moneycalculator.model.FileCurrencyLoader;

public class MoneyDisplay extends JFrame{
    
    private final JButton calculate, cancel;
    private final JTextField amount, result;
    private double mAmount;
    private String sFrom = "USD", sTo = "USD";
    private JComboBox from, to;
    private final FileCurrencyLoader currencyLoader;
    private final CurrencyList currencyList;
    private MoneyCalculator moneycalculator;
    
    public MoneyDisplay(FileCurrencyLoader currencyLoader,CurrencyList currencyList){
        super("MoneyCalculator");
        this.currencyLoader = currencyLoader;
        this.currencyList = currencyList;
        calculate = new JButton("Calculate");
        cancel = new JButton("Cancel");
        amount = new JTextField(20);
        result = new JTextField(20);
    }
    
    public Container display(){
        // panel
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // botones
        cancel.addActionListener(cancelHandler);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(calculate);
        buttons.add(cancel);
        // datos
        JPanel data = new JPanel();
        data.setLayout(new FlowLayout());
        //data.add(new JLabel("Amount"));
        data.add(amount);
        // currencies (from & to)
        JPanel currencies = new JPanel();
        currencies.setLayout(new FlowLayout());
        from = new JComboBox();
        to = new JComboBox();
        for (int i = 0; i < currencyLoader.getList().size(); i++) {
            from.addItem(currencyLoader.getList().get(i));
            to.addItem(currencyLoader.getList().get(i));
        }
        from.addActionListener(fromHandler);
        to.addActionListener(toHandler);
        currencies.add(from);
        currencies.add(new JLabel(" to "));
        currencies.add(to);
        // resultado
        JPanel jresult = new JPanel();
        jresult.add(result);
        // colocar:
        container.add(data);
        container.add(currencies);
        container.add(jresult);
        container.add(buttons);
        
        // Calculate
        calculate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) throws NumberFormatException{
                try{
                    mAmount = Double.parseDouble(amount.getText());
                    moneycalculator = new MoneyCalculator(currencyList,currencyLoader,mAmount,sFrom,sTo);
                    String print = moneycalculator.getCalculate();
                    result.setText(print);
                }catch(NumberFormatException nfe){result.setText("");}catch (IOException ex) {}
            }
        });
        return container;
    }
    
    // Cancel
    private ActionListener cancelHandler= new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            amount.setText("");
            result.setText("");
        }
    };
    
    // Money from
    private ActionListener fromHandler = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
                sFrom = from.getSelectedItem().toString();
        }
    };
    
    // Money to
    private ActionListener toHandler = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
                sTo = to.getSelectedItem().toString();
        }
    };
}

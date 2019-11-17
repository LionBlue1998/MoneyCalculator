package moneycalculator.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import moneycalculator.model.CurrencyLoader;

public class JFrameMain extends JFrame{
    private JButton calculate, cancel;
    private JTextField amount, result;
    private double mAmount;
    private JComboBox from, to;
    private Boolean wasOk;
    private CurrencyLoader currencyLoader;
    
   public JFrameMain(CurrencyLoader currencyLoader){
        super("MoneyCalculator");
        setResizable(true);
        this.currencyLoader = currencyLoader;
        wasOk = false;
        // panel
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // botones
        calculate = new JButton("Calculate");
        calculate.addActionListener(okHandler);
        cancel = new JButton("Cancel");
        cancel.addActionListener(cancelHandler);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(calculate);
        buttons.add(cancel);
        // datos
        amount = new JTextField(20);
        JPanel data = new JPanel();
        data.setLayout(new FlowLayout());
        //data.add(new JLabel("Amount"));
        data.add(amount);
        // currencies
        JPanel currencies = new JPanel();
        currencies.setLayout(new FlowLayout());
        from = new JComboBox();
        to = new JComboBox();
        for (int i = 0; i < currencyLoader.getList().size(); i++) {
            from.addItem(currencyLoader.getList().get(i));
            to.addItem(currencyLoader.getList().get(i));
        }
        currencies.add(from);
        currencies.add(new JLabel(" to "));
        currencies.add(to);
        // resultado
        JPanel jresult = new JPanel();
        result = new JTextField(20);
        jresult.add(result);
        // colocar:
        container.add(data);
        container.add(currencies);
        container.add(jresult);
        container.add(buttons);
        
        // set
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(new Dimension(300,200));
        setMinimumSize(new Dimension(300,200));
    }
   
   // Calcular
    private ActionListener okHandler = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent arg0){
            wasOk = true;
            try{
                mAmount = Double.parseDouble(amount.getText());
                result.setText(mAmount +"");
            }catch(NumberFormatException nfe){}
        }
    };

    
    // Cancelar
    private ActionListener cancelHandler= new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            wasOk = false;
            amount.setText("");
            result.setText("");
        }
    };
}

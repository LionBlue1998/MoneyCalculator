package moneycalculator.view;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MoneyDisplay extends JPanel{
    
    private static JTextField jText;
    
    public MoneyDisplay(){
       this.setLayout(new  FlowLayout());
       this.jText = new JTextField(20);
       this.jText.setEditable(false);
       this.add(jText);
    }
    
    public void display(String s){
        jText.setText(s);
    }
}

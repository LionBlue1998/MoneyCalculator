package moneycalculator.view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import moneycalculator.model.CurrencyLoader;

public class MoneyDialog extends JPanel{
    private String sFrom = "USD", sTo = "USD";
    private final CurrencyLoader currencyLoader;
    private JTextField amount;
    
    public MoneyDialog(CurrencyLoader currencyLoader){
        // currencies (from & to)
        this.currencyLoader = currencyLoader;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new FlowLayout());
        boxPanel.add(createBox("From"));
        boxPanel.add(new JLabel(" to "));
        boxPanel.add(createBox("To"));
        
        this.add(createPanel());
        this.add(boxPanel);
    }
    
    private JPanel createPanel(){
        JPanel data = new JPanel();
        data.setLayout(new FlowLayout());
        amount = new JTextField(20);
        data.add(amount);
        //data.add(verImagenJLabel());
        //this.repaint();
        return data;
    }
    
    private JComboBox createBox(String name){
        JComboBox comboBox = new JComboBox();
        for (int i = 0; i < currencyLoader.getList().size(); i++) {
            comboBox.addItem(currencyLoader.getList().get(i));
        }
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.equals("From")){
                    sFrom = comboBox.getSelectedItem().toString();
                }
                if (name.equals("To")){
                    sTo = comboBox.getSelectedItem().toString();
                }
            }
        });
        return comboBox;
    }
    
    private JLabel verImagenJLabel() {

     // Se crea el objeto JLabel
     JLabel ejemplo = new JLabel(new ImageIcon("azul.PNG"));
     // Le damos una ubicación dentro del Frame
     ejemplo.setBounds(0,0,0,0);
     // Fijamos su tamaño
     ejemplo.setSize(300,200);

     return ejemplo;
    }
    
    /*
    @Override
    public void paint(Graphics g) {
        g.drawImage(loadBackground(), 0, 0, getWidth(), getHeight(), this);
 
        setOpaque(false);
        super.paint(g);
    }
    
    private Image loadBackground() {        
        try {
            return ImageIO.read(new File("yellow.jpg"));
        } catch (IOException ex) {
            return null;
        }
    }
    */
    public String getFrom(){
        return sFrom;
    }
    
    public String getTo(){
        return sTo;
    }
    
    public JTextField getAmount(){
        return amount;
    }
    
    public void setAmount(String s){
        amount.setText(s);
    }
    
}

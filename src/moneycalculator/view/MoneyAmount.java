package moneycalculator.view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class MoneyAmount extends JPanel{
    
    private final JTextField amount;
    private final MoneyDialog dialog;

    public MoneyAmount(MoneyDialog dialog) {
        this.dialog = dialog;
        this.setLayout(new FlowLayout());
        amount = new JTextField(20);
        this.add(amount);
        this.rePaint();
    }

    public JTextField getAmount() {
        return amount;
    }

    public void setAmount(String s) {
        this.amount.setText(s);
    }
    
    public void rePaint(){
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Image background = new ImageIcon("flags\\" + dialog.getFrom() + ".png").getImage();
        super.paintComponent(g);
        g.drawImage(background,0,0,getWidth(),getHeight(),null);
    }
}

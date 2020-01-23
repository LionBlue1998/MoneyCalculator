package moneycalculator.view.ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class MoneyDialogAmount extends JPanel implements View{
    
    private final JTextField amount;
    private final MoneyDialogBox dialog;

    public MoneyDialogAmount(MoneyDialogBox dialog) {
        super(new FlowLayout());
        this.dialog = dialog;
        amount = new JTextField(20);
        execute();
    }
    
    @Override
    public void execute(){
        this.add(amount);
        this.repaint();
    }

    public JTextField getAmount() {
        return amount;
    }

    public void setAmount(String s) {
        this.amount.setText(s);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Image background = new ImageIcon("flags\\" + dialog.getFrom() + ".png").getImage();
        super.paintComponent(g);
        g.drawImage(background,0,0,getWidth(),getHeight(),null);
    }
}

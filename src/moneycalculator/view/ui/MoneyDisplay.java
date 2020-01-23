package moneycalculator.view.ui;

import moneycalculator.view.View;
import moneycalculator.view.ui.MoneyDialogBox;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class MoneyDisplay extends JPanel implements View{
    
    private final JTextField jText;
    private final MoneyDialogBox dialog;
    
    public MoneyDisplay(MoneyDialogBox dialog){
       this.dialog = dialog;
       this.setLayout(new FlowLayout());
       this.jText = new JTextField(20);
       this.jText.setEditable(false);
       execute();
    }

    @Override
    public void execute() {
       this.add(jText);
       this.repaint();
    }
    
    public void display(String s){
        jText.setText(s);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Image background = new ImageIcon("flags\\" + dialog.getTo() + ".png").getImage();
        super.paintComponent(g);
        g.drawImage(background,0,0,getWidth(),getHeight(),null);
    }
}

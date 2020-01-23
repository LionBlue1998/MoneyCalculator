package moneycalculator.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import moneycalculator.view.Command;
import moneycalculator.view.MoneyDialog;
import moneycalculator.view.MoneyDisplay;

public class MainFrame extends JFrame{
    
    private final Map<String,Command> commands = new HashMap<>();
    private final MoneyDisplay display;
    private final MoneyDialog dialog;
    private final Image background;
    
    public MainFrame(MoneyDisplay display, MoneyDialog dialog){
        this.display = display;
        this.dialog = dialog;
        this.background = loadBackground();
        
        initFrame();
        add(main());
        //add(verImagenJLabel());
    }
    
    private JPanel main(){
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        
        buttons.add(createButton("Calculate"));
        buttons.add(createButton("Cancel"));
        
        panel.add(dialog);
        panel.add(display);
        panel.add(buttons);
        
        return panel;
    }
    
    // https://lefunes.wordpress.com/2008/05/14/incluyendo-una-imagen-de-fondo-a-un-jframe/
    // https://lefunes.wordpress.com/2008/11/22/incluyendo-una-imagen-de-fondo-en-un-jpanel/
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), rootPane);
        //g.setOpaque(false);
        paintComponents(g);
    }
    
    private JButton createButton(String name){
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) throws NumberFormatException{
                commands.get(name).toExecute();
            }
        });
        return button;
    }
    
    public void addCommand(String id, Command command){
        commands.put(id, command);
    }

    private void initFrame() {
        setTitle("MoneyCalculator");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(new Dimension(300,200));
        setMinimumSize(new Dimension(300,200));
    }
    
    private Image loadBackground() {        
        try {
            return ImageIO.read(new File("yellow.jpg"));
        } catch (IOException ex) {
            return null;
        }
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
}

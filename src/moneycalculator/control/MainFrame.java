package moneycalculator.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import moneycalculator.view.Command;
import moneycalculator.view.MoneyDialog;
import moneycalculator.view.MoneyDisplay;

public class MainFrame extends JFrame{
    
    private final Map<String,Command> commands = new HashMap<>();
    private final MoneyDisplay display;
    private final MoneyDialog dialog;
    
    public MainFrame(MoneyDisplay display, MoneyDialog dialog){
        setTitle("MoneyCalculator");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.display = display;
        this.dialog = dialog;
        
        getContentPane().add(toolBar());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(new Dimension(300,200));
        setMinimumSize(new Dimension(300,200));
    }
    
    private JPanel toolBar(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        
        buttons.add(createButton("Calculate"));
        buttons.add(createButton("Cancel"));
        
        panel.add(dialog.getPanel());
        panel.add(display.getPanel());
        panel.add(buttons);
        
        return panel;
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
}

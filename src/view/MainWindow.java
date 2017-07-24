package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.HealthMeter;


public class MainWindow extends JFrame {
    
    public static JButton quitButton;
    public static JLabel score;
    public static HealthMeter health;
    public Image launcherImage;
    public MainWindow() {
        
        Container c = getContentPane();
        score = new JLabel("Score: 0");
        health = new HealthMeter();
        c.add(Main.gamePanel, "Center");
        JPanel southPanel = new JPanel();
      
        quitButton = new JButton("Quit");
        //southPanel.add(quitButton);
        southPanel.add(score);
        c.add(southPanel, "South");

        ButtonListener buttonListener = new ButtonListener();
       
        quitButton.addActionListener(buttonListener);
    
        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
       
        quitButton.setFocusable(false);
        
        
    }

}

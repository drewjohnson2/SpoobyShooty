package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class HealthMeter 
{
    private Image launcherImage;
    private int health;
    
    public HealthMeter() 
    {
        launcherImage = null;
       
        try {
            launcherImage = ImageIO.read(getClass().getResource("shootoy.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }
    
    
   
    public void update() 
    {
        for (GameFigure x : Main.gameData.friendFigures) 
        {
            switch (x.state) 
            {
                case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_5 : 
                    health = 5;
                    break;
                    
                case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_4 :
                    health = 4;
                    break;
                    
                case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_3 : 
                    health = 3;
                    break;
                    
                case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_2 :
                    health = 2;
                    break;
                    
                case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_1 :
                    health = 1;
                    break;
                    
                case GameFigureState.STATE_DONE : 
                    health = 1;
                    break;
            }
        }
    }
    
    public void render(Graphics2D g2) {
        for(int i = 0; i < health; i++)
        {
            g2.drawImage(launcherImage, i * 45, 0, 40, 40, null);
        }
    }
}

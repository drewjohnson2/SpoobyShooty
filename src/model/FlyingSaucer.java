
package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import view.GamePanel;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import model.GameData;
import static model.GameData.shooter;
import model.GameFigure;

public class FlyingSaucer extends GameFigure {
    
    private final int WIDTH = 40;
    private final int HEIGHT = 50;
    private int count = 0;
    private final int UNIT_TRAVEL = 5; // per frame
    private static final int COUNT_TIME = 120;
    private int width = WIDTH;
    private int height = HEIGHT;
    private float collisionBoxX = 60;
    private float collisionBoxY = 70;
    private Image launcherImage;
    private Image explosionImage;
    private int direction = 1; // +1: to the right; -1 to the left
    private boolean start = true;
    private Random randomYStart = new Random();
    private Random randomXStart = new Random();
    private int startingLocation;
    private int spitCounter = 0;
    
    /**
     * 
     * @param x - x location of graphic
     * @param y - y location of graphic
     * 
     * Imports enemy and explosion image and image location.
     */
    
    public FlyingSaucer(float x, float y) {
        super(x, y);
        super.currentState = new UFOStateAdded();
        
        launcherImage = null;
        explosionImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("Spooby.png"));
            explosionImage = ImageIO.read(getClass().getResource("BOOM.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
        
    }
    
    /**
     * 
     * Renders images on screen.
     */
   
    @Override
    public void render(Graphics2D g) {
        
        if(!(currentState instanceof UFOStateDamaged))
            g.drawImage(launcherImage, (int)super.x, (int)super.y, 60, 70, null);
        
        if(currentState instanceof UFOStateDamaged)
        {
            g.drawImage(explosionImage, (int)super.x - 15, (int)super.y - 10, 60, 70, null);
            count += 10;
        }
    }
    
    /**
     * Updates location of image on screen. Periodically adds spit object to screen.
     */

    @Override
    public void update() {
        
        if(GameData.level == 1)
        {
            if(currentState instanceof UFOStateAdded)
            {
                if (direction > 0) {
                    // moving to the right
                    super.x += UNIT_TRAVEL;
                    spitCounter += 5;
                    if(spitCounter % 500 == 0)
                    {
                        Spit s = new Spit(
                        getXofMissileShoot(),
                        getYofMissileShoot(),
                        shooter.x, shooter.y, // target location where the missile explodes
                        Color.RED);

                        Main.gameData.enemyFigures.add(s);
                    }
                        
                    if (super.x + WIDTH/2 > GamePanel.width) {
                        direction = -1;
                    }
                } else {
                    // moving to the left
                    spitCounter += 5;
                    if(spitCounter % 500 == 0)
                    {
                        Spit m = new Spit(
                        getXofMissileShoot(),
                        getYofMissileShoot(),
                        shooter.x, shooter.y, // target location where the missile explodes
                        Color.RED);

                        Main.gameData.enemyFigures.add(m);
                    }
                    super.x -= UNIT_TRAVEL;
                    if (super.x - WIDTH/2 <= 0) {
                        direction = 1;
                    }
                }
            }
        
            else  
            {
                
                if(count >= COUNT_TIME)
                {
                    this.changeState();
                    count = 0;
                }
            }
        }
        
        if(GameData.level == 2)
        {
            if(currentState instanceof UFOStateAdded)
            {
                
                if(start)
                {
                    startingLocation = (randomYStart.nextInt(600 - 50 + 1) + 50);
                    super.y = startingLocation;
                    super.x = randomXStart.nextInt(850 + 50 + 1) + 50;
                    start = false;
                }
            
                if (direction > 0) {
                    
                    super.y += UNIT_TRAVEL;
                    if (super.y + HEIGHT/2 > GamePanel.height) {
                        super.y = -300;
                    }
                } 
            }
        
            else  
            {
                //updateSize();
                if(count >= COUNT_TIME)
                {
                    this.changeState();
                    count = 0;
                }
            }
            
        }
        
    }
    
    /**
     * Gets origin of spit shoot.
     * @return location.
     */
    
    public float getXofMissileShoot() {
        return super.x + 15;
    }
    
    public float getYofMissileShoot() {
        return super.y;
    }
    
    /**
     * 
     * @return location and size of collision box.
     */
    
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(x, y, collisionBoxX, collisionBoxY);
    }
}
    


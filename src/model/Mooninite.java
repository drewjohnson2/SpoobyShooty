
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Mooninite extends GameFigure {
    
    private final int WIDTH = 40;
    private final int HEIGHT = 50;
    private int count = 0; 
    private static final int COUNT_TIME = 160;
    private int width = WIDTH;
    private int height = HEIGHT;
    private float collisionBoxX = 80;
    private float collisionBoxY = 80;
    private Image launcherImage;
    private Image explosionImage;
    public Random randomX = new Random();
    public Random randomY = new Random();
    public final int MOVE_TIME = 500;
    private int moveCount = 0;
    /**
     * 
     * @param x - x location of graphic
     * @param y - y location of graphic
     * 
     * Imports enemy and explosion image and image location.
     */
    
    public Mooninite(float x, float y) {
        super(x, y);
        super.currentState = new MooniniteStateAdded();
        
        launcherImage = null;
        explosionImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("Mooninite.png"));
            explosionImage = ImageIO.read(getClass().getResource("dead.png"));
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
         
        if(currentState instanceof MooniniteStateAdded)
            g.drawImage(launcherImage, (int)super.x, (int)super.y, 80, 90, null);
        
        if(currentState instanceof MooniniteStateDamaged)
        {
            g.drawImage(explosionImage, (int)super.x - 15, (int)super.y - 10, 140, 140, null);
            count += 10;
        }
    }
    
    /**
     * Updates location on screen. If max time for the explosion image
     * to be on screen has been reached the state of the object is changed.
     */

    @Override
    public void update() {
        
            
            if(currentState instanceof MooniniteStateAdded)
            {
                if (moveCount < MOVE_TIME) {
                   moveCount += 10;
                } else {     
                    super.x = randomX.nextInt(850 - 50 + 1) + 50;
                    super.y = randomY.nextInt(600 - 50 + 1) + 50;
                    moveCount = 0;
                }
            }
        
            else  
            {
                updateSize();
                if(count >= COUNT_TIME)
                {
                    this.changeState();
                    count = 0;
                }
            }
        
        
    }
    
    public void updateSize()
    {
        height += 2;
        width += 2;
    }
    
    /**
     * 
     * @return - returns size of the collision box for detecting collisions.
     */
    
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(x, y, collisionBoxX, collisionBoxY);
    }
}
    


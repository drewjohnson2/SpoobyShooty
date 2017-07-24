package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class Ignignokt extends GameFigure {

    private final int WIDTH = 40;
    private final int HEIGHT = 50;
    private int count = 0;
    private final int UNIT_TRAVEL = 10; 
    private static final int COUNT_TIME = 160;
    private int width = WIDTH;
    private int height = HEIGHT;
    private float collisionBoxX = 100;
    private float collisionBoxY = 100;
    private Image launcherImage;
    private Image explosionImage;
    private int direction = 1; 
    public Random randomX = new Random();
    public Random randomY = new Random();
    public final int MOVE_TIME = 500;
    
    /**
     * 
     * @param x - x location of graphic
     * @param y - y location of graphic
     * 
     * Imports enemy and explosion image.
     */
    
    public Ignignokt(float x, float y) {
        super(x, y);
        super.currentState = new IgnignoktStateAdded();
        
        launcherImage = null;
        explosionImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("Ignignokt.png"));
            explosionImage = ImageIO.read(getClass().getResource("idead.png"));
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
         
        if(currentState instanceof IgnignoktStateAdded)
            g.drawImage(launcherImage, (int)super.x, (int)super.y, 100, 110, null);
        
        if(currentState instanceof IgnignoktStateDamaged)
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
        
            
            if(currentState instanceof IgnignoktStateAdded)
            {
                if (direction > 0) {
                    
                    super.x += UNIT_TRAVEL;
                    if (super.x + WIDTH/2 + 80 > GamePanel.width) {
                        direction = -1;
                        
                        if(super.y < 650)
                            super.y += 20;
                    }
                } else {
                    
                    super.x -= UNIT_TRAVEL;
                    if (super.x - WIDTH/2 <= 0) {
                        direction = 1;
                        
                        if(super.y < 650)
                            super.y += 20;
                    }
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

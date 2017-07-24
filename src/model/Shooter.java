package model;

import controller.Animator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Shooter extends GameFigure {

    private Image launcherImage;
    public static boolean isHit = false;
    public static boolean destroyed = false;
    private float collisionBoxX = 40;
    private float collisionBoxY = 50;
    private int size = 5;
   
    public Shooter(int x, int y) {
        super(x, y);
        super.state = GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_5;
        
        launcherImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("shootoy.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        
        
            g.drawImage(launcherImage, (int)super.x, (int)super.y, 55, 70, null);
        
        
    }
    

    @Override
    public void update() 
    {
          
        
    }
    
    public void updateSize()
    {
        size += 2;
    }
    
    public void updateState()
    {
       
    }

    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }
    
    // Missile shoot location: adjut x and y to the image
    public float getXofMissileShoot() {
        return super.x + 15;
    }
    
    public float getYofMissileShoot() {
        return super.y;
    }

    public java.awt.geom.Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(x, y, collisionBoxX, collisionBoxY);
    }
}


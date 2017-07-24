package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Torpedo extends GameFigure {

    // missile size
    private static final int SIZE = 5;
    private Image launcherImage;

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;

    /**
     * 
     * @param x - x location of graphic
     * @param y - y location of graphic
     * 
     * Imports enemy and explosion image and image location.
     */
    
    public Torpedo(float x, float y) {
        super(x, y);
        super.state = GameFigureState.TORPEDO_LAUNCHED;
        
        launcherImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("Torpedo.png"));
            
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
        
        g.drawImage(launcherImage, (int)super.x, (int)super.y, 20, 30, null);
    }

    @Override
    public void update() {
        if (state == GameFigureState.TORPEDO_LAUNCHED) {
            updateLocation();
        } else if (state == GameFigureState.TORPEDO_EXPLODED) {
            state = GameFigureState.STATE_DONE;
        }
    }
    
    /**
     * Updates location of torpedo.
     */

    public void updateLocation() {
        
        super.y -= UNIT_TRAVEL_DISTANCE;
        
        if(super.y < 0)
            state = GameFigureState.STATE_DONE;
        
    }

    public void updateSize() {
        size += 2;
    }

    /**
     * 
     * @return - returns size of the collision box for detecting collisions.
     */
    
    @Override
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(this.x - this.size / 2, this.y - this.size / 2, 20, 30);
    }
}

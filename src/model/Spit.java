package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Spit extends GameFigure
{
    private static final int SIZE = 5;
    public static int MAX_EXPLOSION_SIZE = 100;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;
    private Image launcherImage, explosionImage;
    private boolean hit = true;
    private boolean exploded = false;
    

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;
    
    public Spit(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.currentState = new SpitStateAdded();
        this.target = new Point2D.Float(tx, ty);
        this.color = color;
        double angle = Math.atan2(ty - sy, tx - sx);
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
        launcherImage = null;
        explosionImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("EvilCat.png"));
            explosionImage = ImageIO.read(getClass().getResource("spat.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(!(currentState instanceof SpitStateDamaged))
            g.drawImage(launcherImage, (int)super.x, (int)super.y, 50, 50, null);
        
        if(currentState instanceof SpitStateDamaged || exploded)
        {
            g.drawImage(explosionImage, (int)super.x - 15, (int)super.y - 10, 50, 50, null);
           
        }
    }

    @Override
    public void update() {
        
        updateState();
        if (super.currentState instanceof SpitStateAdded) {
            updateLocation();
        } else if (currentState instanceof SpitStateDamaged) {
            updateSize();
        }
    }
    
    public void updateLocation() {
        
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        size += 10;
    }

    public void updateState() {
            
        if (currentState instanceof SpitStateAdded) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            hit = targetReached;
            if (targetReached) {
                currentState = new SpitStateDamaged();
            }
        } else if (currentState instanceof SpitStateDamaged) {
            if (size >= MAX_EXPLOSION_SIZE) {
                currentState = new StateDone();
            }
        } else if(!(hit))
             {
                exploded = true;
                this.changeState();
             }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(this.x - this.size / 2, this.y - this.size / 2, 50, 50);
    }
    
    
}

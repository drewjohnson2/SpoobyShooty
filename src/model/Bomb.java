package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Bomb extends GameFigure {
 
    private int radius;
    private final Color color;
    private final int MAX_EXPLOSION_SIZE = 50;
    private int dx = 3;
    private int dy = 3;
    private Image explosionImage;
   

    public Bomb(float x, float y, int radius, Color color) {
       
        super(x, y);
        super.currentState = new BombStateAdded();
        this.radius = radius;
        this.color = color;
        
        explosionImage = null;
        
        try {
            explosionImage = ImageIO.read(getClass().getResource("Bang.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
       
    }


    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        
        
        if(currentState instanceof BombStateAdded) {
        
            g.fillOval((int)(x - radius), (int)(y - radius), 
                radius * 2, radius * 2);
        }
        
        else
        {
            g.drawImage(explosionImage, (int)super.x - 15, (int)super.y - 10, 60, 70, null);
        }
        
        
    }
    
    public void updateSize()
    {
        radius += 2;
    }

    @Override
    public void update() {
        
        if(currentState instanceof BombStateAdded)
        {
             super.x += dx;
             super.y += dy;
        } 
        
        else
        {
            updateSize();
            if(MAX_EXPLOSION_SIZE <= radius)
            {
                this.changeState();
            }
        }
        

        if (super.x + radius > GamePanel.width) {
            dx = -dx;
            super.x = GamePanel.width - radius;
        } else if (super.x - radius < 0) {
            dx = -dx;
            super.x = radius;
        }

        if (super.y + radius > GamePanel.height) {
            dy = -dy;
            super.y = GamePanel.height - radius;
        } else if (super.y - radius < 0) {
            dy = -dy;
            super.y = radius;
        }
    }
    
    @Override 
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(x - radius, y - radius, radius * 2, radius * 2);
    }
}

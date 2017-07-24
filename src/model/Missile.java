package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
public class Missile extends GameFigure {

    // missile size
    private static final int SIZE = 5;
    public static int MAX_EXPLOSION_SIZE;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;
    public WeaponComponent weapon;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public Missile(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.state = GameFigureState.MISSILE_STATE_LAUNCHED;
        weapon = new NormalMissile();
        this.target = new Point2D.Float(tx, ty);
        this.color = color;
        double angle = Math.atan2(ty - sy, tx - sx);
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
    }
    
    /**
     * 
     * Renders missile on the screen.
     */
    
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) (super.x - size / 2),
                (int) (super.y - size / 2),
                size, size);
    }

        /**
         * Calls update methods for location, size and calls decorator.
         */
    
    @Override
    public void update() {
       
         if(GameData.score > 100 &&(GameData.score % 500) == 0)
        {
            weapon = new LargeMissile(weapon);
            
        } else if(GameData.score > 500 && (GameData.score % 500) != 0)
        {
            weapon = new NormalMissile();
        }
        weapon.shoot();
        updateState();
        if (state == GameFigureState.MISSILE_STATE_LAUNCHED) {
            updateLocation();
        } else if (state == GameFigureState.MISSILE_STATE_EXPLODED) {
            updateSize();
        }
    }

    public void updateLocation() {
        
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        size += 2;
    }
    
    /**
     * Updates state.
     */

    public void updateState() {
            
        if (state == GameFigureState.MISSILE_STATE_LAUNCHED) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            if (targetReached) {
                state = GameFigureState.MISSILE_STATE_EXPLODED;
            }
        } else if (state == GameFigureState.MISSILE_STATE_EXPLODED) {
            if (size >= MAX_EXPLOSION_SIZE) {
                state = GameFigureState.STATE_DONE;
            }
        }
    }
    
    /**
     * 
     * @return - returns size of the collision box for detecting collisions.
     */

    @Override
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(this.x - this.size / 2, this.y - this.size / 2, this.size, this.size);
    }
}

package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JOptionPane;


public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public static Shooter shooter;
    public static int level = 1;
    public static int score = 0;
    private boolean bossAdd = true;
    private boolean bossTwoAdd = true;
    

    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        

        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 130);

        friendFigures.add(shooter);
        
        JOptionPane.showMessageDialog(null, 
                        "Use arrow keys to move and mouse click to shoot. "
                                + "\nPress 'OK' to begin game!", 
                        "Spooby Shooty", JOptionPane.PLAIN_MESSAGE);
        
        
        addEnemies();
        
    }

    public void add(int n) {
        for (int i = 0; i < n; i++) {
            float red = (float) Math.random();
            float green = (float) Math.random();
            float blue = (float) Math.random();
            // adjust if too dark since the background is black
            if (red < 0.5) {
                red += 0.2;
            }
            if (green < 0.5) {
                green += 0.2;
            }
            if (blue < 0.5) {
                blue += 0.2;
            }
            
            enemyFigures.add(new Bomb(
                    (int) (Math.random() * GamePanel.width),
                    (int) (Math.random() * GamePanel.height), 
                    RADIUS, new Color(red, green, blue)));
        }
        
    }
    

    public void addEnemies()
    {
        for(int i = 0; i < 5; i++)
        {
            addFlyingSaucer(); 
        }
        
    }
    
    public void update() {

        // no enemy is removed in the program
        // since collision detection is not implemented yet.
        // However, if collision detected, simply set
        // f.state = GameFigure.STATE_DONE
        Main.healthMeter.update();
        
        ArrayList<GameFigure> removeEnemies = new ArrayList<>();
        GameFigure f;
        for (int i = 0; i < enemyFigures.size(); i++) {
            f = enemyFigures.get(i);
            if (f.currentState instanceof StateDone) {
                removeEnemies.add(f);
               
            }
        }
        
        enemyFigures.removeAll(removeEnemies);

        for (GameFigure g : enemyFigures) {
            g.update();
        }

        // missiles are removed if explosion is done
        ArrayList<GameFigure> removeFriends = new ArrayList<>();
        for (int i = 0; i < friendFigures.size(); i++) {
            f = friendFigures.get(i);
            if (f.state == GameFigureState.STATE_DONE) {
                removeFriends.add(f);
            }
        }
        friendFigures.removeAll(removeFriends);

        for (GameFigure g : friendFigures) {
            g.update();
        }
        
        if(enemyFigures.isEmpty() && bossAdd)
        {
            enemyFigures.add(new Ignignokt((int)((Math.random() * 600) + 5), (int)(Math.random() * 250) + 1));
            bossAdd = false;
        }
        
        if(enemyFigures.isEmpty() && bossTwoAdd && level == 2)
        {
            enemyFigures.add(new Mooninite((int)((Math.random() * 600) + 5), (int)(Math.random() * 600) + 1));
            bossTwoAdd = false;
        }
        
        else if(enemyFigures.isEmpty() && !bossAdd)
        {
            level++;
            
            
            if(level <= 2)
            {
                JOptionPane.showMessageDialog(null, 
                        "You beat level one! \nClick 'OK' to continue to level two!", 
                        "Level One Complete", JOptionPane.PLAIN_MESSAGE);
                
                add(5);
                enemyFigures.add(new Ignignokt((int)((Math.random() * 600) + 5), (int)(Math.random() * 250) + 1));
                enemyFigures.add(new Ignignokt((int)((Math.random() * 600) + 5), (int)(Math.random() * 250) + 1));
                
                for(int i = 0; i < level; i++)
                {
                    addEnemies();
                }
            }
            
            else
            {
                int reply = JOptionPane.showConfirmDialog(null, "You beat the game! "
                        + "Keep playing?", "Winner", JOptionPane.YES_NO_OPTION, 
                        JOptionPane.PLAIN_MESSAGE);
                
                if(reply == JOptionPane.YES_OPTION)
                {
                    level = 1;
                    score = 0;
                    bossAdd = true;
                    addEnemies();
                }
                
                else
                {
                    JOptionPane.showMessageDialog(null, "Score: " + score, 
                            "Score", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            }
        }
        
         view.MainWindow.score.setText("Score: " + score);
        
    }
    
    public void addFlyingSaucer() 
    {
        Random random = new Random();
        int x = random.nextInt(900 - 80 + 1) + 80;
        int y = random.nextInt(450 - 40 + 1) + 40;
        if(level == 1)
            enemyFigures.add(new FlyingSaucer(x, y));
        
        else
            enemyFigures.add(new FlyingSaucer((int)((Math.random() * 600) + 5), (int)(Math.random() * 2000) + 1));
    }
    
}

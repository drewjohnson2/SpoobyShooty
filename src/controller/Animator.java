package controller;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import model.Bomb;
import model.BombStateAdded;
import model.FlyingSaucer;
import model.GameData;
import static model.GameData.score;
import model.GameFigure;
import model.GameFigureState;
import model.Ignignokt;
import model.IgnignoktStateAdded;
import model.Mooninite;
import model.MooniniteStateAdded;
import model.Shooter;
import model.Spit;
import model.SpitStateAdded;
import model.SpitStateDamaged;


public class Animator implements Runnable {

    public boolean running = true;
    public FlyingSaucer ufo;
    private final int FRAMES_PER_SECOND = 50;
    public static boolean hit = false;

    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();
            
            processCollisions();

            Main.gameData.update();
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);
            
            if (sleepTime > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {

                }
            }
            System.out.println(sleepTime);
        }
        System.exit(0);
    }
    
    private void updateHealth(GameFigure shooter) 
    {
        switch(shooter.state) 
        {
            case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_5:
                shooter.state = GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_4;
                GameData.score -= 100;
                break;
                
            case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_4:
                shooter.state = GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_3;
                GameData.score -= 100;
                break;
                
            case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_3:
                shooter.state = GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_2;
                GameData.score -= 100;
                break;
                
            case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_2:
                shooter.state = GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_1;
                GameData.score -= 100;
                break;
                
            case GameFigureState.SHOOTER_STATE_HEALTH_LEVEL_1:
                shooter.state = GameFigureState.STATE_DONE;
                GameData.score -= 100;
                gameOver();
                break;
        }
    }
    
   
    
    private void gameOver()
    {
        for(GameFigure enemies : Main.gameData.enemyFigures)
        {
            enemies.state = GameFigureState.STATE_DONE;
        }
        
        for(GameFigure friends : Main.gameData.friendFigures)
        {
            friends.state = GameFigureState.STATE_DONE;
        }
        
        JOptionPane.showMessageDialog(null, 
                        "You Lose! See you, space cowboy.\n Score: " + GameData.score, 
                        "Game Over", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
                
    }
    
    private void processCollisions() 
    {
        Main.gameData.enemyFigures.forEach((enemies) -> {
            Main.gameData.friendFigures.stream().map((friends) -> {
                if ((enemies).getCollisionBox().intersects((friends).getCollisionBox()))
                { 
                    if(enemies instanceof FlyingSaucer)
                    {
                        if(enemies.currentState instanceof model.UFOStateAdded)
                        {
                            enemies.changeState();
                            updateHealth(friends);
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                        }
                    }
                    
                    else if(enemies instanceof Bomb)
                    {
                        if(enemies.currentState instanceof BombStateAdded)
                        {
                            enemies.changeState();
                            updateHealth(friends);
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                        }
                    }
                    
                    else if(enemies instanceof Mooninite)
                    {
                        if(enemies.currentState instanceof MooniniteStateAdded  && !(friends instanceof Shooter))
                        {
                            enemies.changeState();
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                            
                        }
                        updateHealth(friends);
                    }
                    
                    else if(enemies instanceof Ignignokt)
                    {
                        if(enemies.currentState instanceof IgnignoktStateAdded  && !(friends instanceof Shooter))
                        {
                            enemies.changeState();
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                            
                        }
                        updateHealth(friends);
                    }
                    
                    else if(enemies instanceof Spit)
                    {
                        if(enemies.currentState instanceof SpitStateAdded)
                        {
                            enemies.changeState();
                            updateHealth(friends);
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                            
                        } else if(enemies.currentState instanceof SpitStateDamaged)
                        {
                            enemies.changeState();
                            updateHealth(friends);
                            
                            if(!(friends instanceof Shooter))
                                score += 100;
                        }
                        
                    }
                    
                    
                    
            
                }
                return friends;
            }).filter((friends) -> ((friends).state == GameFigureState.TORPEDO_LAUNCHED)).filter((friends) -> ((enemies).getCollisionBox().intersects((friends).getCollisionBox()))).forEachOrdered((friends) -> {
                (friends).state = GameFigureState.TORPEDO_EXPLODED;
            });
        });
        
    }

}

package model;

public class StateDone implements States
{
    public void hit(GameFigure enemyFigures)
    {
        enemyFigures.setState(null);
    }
}

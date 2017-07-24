package model;

public class BombStateDamaged implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new StateDone());
    }
}

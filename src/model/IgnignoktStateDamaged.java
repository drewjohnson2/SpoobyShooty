package model;

public class IgnignoktStateDamaged implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new StateDone());
    }
    
}
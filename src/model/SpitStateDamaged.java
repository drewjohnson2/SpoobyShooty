package model;

public class SpitStateDamaged implements States
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new StateDone());
    }
}
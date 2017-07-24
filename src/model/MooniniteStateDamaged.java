package model;

public class MooniniteStateDamaged implements States
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new StateDone());
    }
}

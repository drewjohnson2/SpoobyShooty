package model;

public class SpitStateAdded implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new MooniniteStateDamaged());
    }
}
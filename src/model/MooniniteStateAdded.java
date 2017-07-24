package model;

public class MooniniteStateAdded implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new MooniniteStateDamaged());
    }
}

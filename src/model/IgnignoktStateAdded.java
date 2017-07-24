package model;

public class IgnignoktStateAdded implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new IgnignoktStateDamaged());
    }
    
}

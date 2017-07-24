package model;

public class BombStateAdded implements States 
{ 
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new BombStateDamaged());
    }
}

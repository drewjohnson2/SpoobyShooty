package model;

import model.GameFigure;
import model.States;

public class UFOStateAdded implements States 
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new UFOStateDamaged());
    }
}

package model;

import model.GameFigure;
import model.StateDone;
import model.States;

public class UFOStateDamaged implements States
{
    @Override
    public void hit(GameFigure context)
    {
        context.setState(new StateDone());
    }
}

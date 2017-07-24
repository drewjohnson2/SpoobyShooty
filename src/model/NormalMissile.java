package model;

public class NormalMissile implements WeaponComponent
{
      @Override
    public void shoot()
    {
        Missile.MAX_EXPLOSION_SIZE = 50;
    }
    
}

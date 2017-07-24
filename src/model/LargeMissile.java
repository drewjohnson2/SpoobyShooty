package model;

public class LargeMissile extends MissileDecorator
{
    protected WeaponComponent weapon;
    
    public LargeMissile(WeaponComponent weapon)
    {
        super(weapon);
    }
    
    @Override
    public void shoot()
    {
        addedPower();
    }
    
    public void addedPower()
    {
        Missile.MAX_EXPLOSION_SIZE = 150;
    }
}

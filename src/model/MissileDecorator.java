package model;

public abstract class MissileDecorator implements WeaponComponent
{
    protected WeaponComponent weapon;
    
    public MissileDecorator(WeaponComponent weapon)
    {
        this.weapon = weapon;
    }
    
    @Override
    public void shoot()
    {
        weapon.shoot();
    }
}

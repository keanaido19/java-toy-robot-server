package za.co.wethinkcode.robotworlds.world.interfaces.health;

public interface Damageable extends Health {
    public boolean isDamageable();

    public void damage(int damage);
}

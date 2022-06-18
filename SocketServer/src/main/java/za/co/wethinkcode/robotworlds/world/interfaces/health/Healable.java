package za.co.wethinkcode.robotworlds.world.interfaces.health;

public interface Healable extends Health {
    public boolean isHealable();

    public void heal(int health);
}

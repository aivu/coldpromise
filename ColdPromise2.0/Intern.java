import greenfoot.*;

/**
 * Intern class penguins.
 * @author Alina Vuong
 * 2.23.13
 */
public class Intern extends Mob {
    public Intern() {
        super();
        health = 30;
        speed = 2;
        touchDmg = 10;
    }

    @Override
    public void act() {
        super.act();
    }    

    @Override
    public void checkHealth() {
        if (health <= 0) {
            Daumscape daum = (Daumscape) getWorld();
            daum.removeObject(this);
            daum.internCount--;
        }
    }
}

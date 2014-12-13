import greenfoot.*;
import java.util.List;
/**
 * Specialized mob class, with implementation for dialogue.
 * 
 * @author Alina Vuong
 * 2.24.13
 */
public class Collaborator extends Mob {
    public Collaborator(int speed, int health, int touchDmg, int atkDelay) {
        super();
        this.health = health;
        this.speed = speed;
        this.touchDmg = touchDmg;
        this.atkDelay = atkDelay;
    }

    @Override
    public void act() {
        List<DialogueBox> boxes = getWorld().getObjects(DialogueBox.class);
        if (boxes.size() < 1) {
            aggro();
        }
        super.act();
    }    

    @Override
    public void checkHealth() {
        if (health <= 0) {
            Daumscape daum = (Daumscape) getWorld();
            daum.addBossNPCs(getX(), getY());
            daum.removeObject(this);
            daum.bossCount--;
        }
    }

    @Override
    public void aggro() {
        super.aggro();
        fire();
    }
}
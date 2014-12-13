import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Upon ingestion, will cause Jim to recover some HP.
 * 
 * @author Alina Vuong
 */
public class Flan extends Actor {
    /** Flan's HP restorative value to Jim. */
    private int hpValue = 20;
    
    @Override
    public void act() {
        checkIfEaten();
    }
    
    /** If eaten, adds to Jim's HP. */
    private void checkIfEaten() {
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if (jim != null) {
            jim.add(hpValue);
            getWorld().removeObject(this);
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Upon ingestion, will cause Jim to recover some HP.
 * 
 * Alina Vuong
 */
public class Flan extends Actor {
    private int hpValue = 20;
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

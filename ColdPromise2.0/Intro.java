import greenfoot.*;

/**
 * Intro scene sequence.
 *
 * @author Alina Vuong
 */
public class Intro extends Actor {
    /** First scene. */
    private GreenfootImage intro1 = new GreenfootImage("intro1.png");
    /** Second scene. */
    private GreenfootImage intro2 = new GreenfootImage("intro2.png");
    /** Third scene. */
    private GreenfootImage intro3 = new GreenfootImage("intro3.png");
    /** Fourth scene.*/
    private GreenfootImage intro4 = new GreenfootImage("intro4.png");
    /** Counts time since current scene is loaded. */
    private int proceedDelCount = 0;
    /** Minimum time required before proceeding to next scene is allowed. */
    private int proceedDelay = 15;
    
    public Intro() {
        setImage(intro1);
    }

    @Override
    public void act() {
        proceedDelCount++;
        checkKeys();
    }
    
    /** Proceeds to next scene if certain keys are pressed. */
    private void checkKeys() {
        if (Greenfoot.isKeyDown("enter") && proceedDelCount > proceedDelay) {
            proceedDelCount = 0;
            if (getImage() == intro1) {
                setImage(intro2);
            } else if (getImage() == intro2) {
                setImage(intro3);
            } else if (getImage() == intro3) {
                setImage(intro4);
            } else if (getImage() == intro4) {
                getWorld().removeObject(this);
            }
        }
    }
}
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spaceship.
 * 
 * @Alina Vuong
 */
public class Spaceship extends Actor {
    /** Counts time elapsed. */
    private int animDelCount = 0;
    /** Minimum time required before animation can change. */
    private int animateDelay = 30;
    /** Images for the ship. */
    private GreenfootImage ship1, ship2;
    public Spaceship() {
        ship1 = new GreenfootImage("spaceship1.png");
        ship2 = new GreenfootImage("spaceship2.png");
        setImage(ship1);
    }
    /** Act. */
    public void act() {
        animDelCount++;
        animate();
    }    
    /** Animates the image. */
    private void animate() {
        if (animDelCount > animateDelay) {
            animDelCount = 0;
            if (getImage() == ship1) {
                setImage(ship2);
            } else {
                setImage(ship1);
            }
        }
    }
}
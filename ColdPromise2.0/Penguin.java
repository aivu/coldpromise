import greenfoot.*;

/**
 * Penguin species.
 * 
 * @author Alina Vuong
 * 2.23.13
 */
public class Penguin extends Mover
{
    public Penguin() {
        super();
        imageR1 = new GreenfootImage("penguinR1.png");
        imageR2 = new GreenfootImage("penguinR2.png");
        imageRWalk = new GreenfootImage("penguinRWalk.png");
        imageL1 = new GreenfootImage("penguinL1.png");
        imageL2 = new GreenfootImage("penguinL2.png");
        imageLWalk = new GreenfootImage("penguinLWalk.png");
        setImage(imageL2);
        setDirection("right");
    }

    @Override
    public void act() {
        super.act();
    }
}

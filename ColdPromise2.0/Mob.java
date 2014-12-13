import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * 
 * @author Alina Vuong
 * 2.23.13
 */
abstract class Mob extends Penguin
{  
    /** Damage inflicted on player upon contact with the mob. */
    public int touchDmg;
    /** Determined delay time between movements of the mob toward the player. */
    public int aggroDelay = 30;
    /** Count of time elapsed since last movement toward player. */
    public int aggroDelCount = 0;

    public Mob() {
        super();
        name = "mob";
        health = 100;
        setDirection("left");
        speed = 2;
        knockbackDelay = 20;
        kbDistance = 10;
    }

    @Override
    public void act()  {
        super.act();
        aggroDelCount++;

        List<DialogueBox> boxes = getWorld().getObjects(DialogueBox.class);
        if (boxes.size() < 1) {
            aggro();
            checkHit();
            checkHealth();
        }
    }    

    public void aggro() {
        if (aggroDelCount > aggroDelay) {
            aggroDelCount = 0;
            Daumscape daum = (Daumscape) getWorld();
            if (daum.jim.getX()>getX()) {
                setLocation(getX() + speed, getY());
                setDirection("right");
                super.walk();
            }
            if (daum.jim.getX() < getX()) {
                setLocation(getX() - speed, getY());
                setDirection("left");
                super.walk();
            }
            if (daum.jim.getY() > getY()) {
                setLocation(getX(), getY() + speed);
            }
            if (daum.jim.getY() < getY()) {
                setLocation(getX(), getY() - speed);
            }
        }
    }

    /** Deducts hp from the mob and knockback effect occurs. */
    public void hit(int damage, int x, int y) {
        if (x > getX()) {
            setDirection("right");
        } else {
            setDirection("left");
        }
        super.hit(damage, x, y);
    }

    /** Checks if the player has touched the mob. If so, damages the player. */
    public void checkHit() {
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if (jim!=null)
        {
            jim.hit(touchDmg, getX(), getY());
        }
    }

    /** Checks health of mob. */
    abstract void checkHealth();
}


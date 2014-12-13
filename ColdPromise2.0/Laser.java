import greenfoot.*;

/**
 * A laser.
 * 
 * @author Alina Vuong
 */
public class Laser extends Actor {
    /** Direction for the laser's path. */
    private String direction;
    /** The distance covered per act-method. */
    private int speed = 6;
    /** The amount of damage to be dealt on mobs/Jim (depending on who fired it). */
    private int damage = 20;
    /** The duration of the laser's lifespan. */
    private int life = 50;
    /** Holds the name of the object that fired the laser. */
    private String className;

    public Laser(String direction, String theShooter) {
        this.direction = direction;
        className = theShooter;
    }

    @Override
    public void act() {
        life--;
        checkDuration();
    }    

    /** Checks if laser has reached end of lifespan. */
    private void checkDuration() {
        if (life <= 0 || atWorldEdge()) {
            getWorld().removeObject(this);
        } else {
            move();
            checkHit();
        }
    }

    /** Moves the laser according to its direction. */
    public void move() {
        if (direction.equals("right")) {
            setLocation(getX()+speed, getY());
        } else if (direction.equals("left")) {
            setLocation(getX()-speed, getY());
        }
    }
    
    /** Returns true if the laser has reached the end of the world. */
    private boolean atWorldEdge() {
        Wall wall = (Wall) getOneIntersectingObject(Wall.class);
        if (wall != null) {
            return true;
        } else if (getX() >= getWorld().getWidth() - 1 || getX() == 0) {
            return true;
        }
        return false;
    }

    /** If the laser encounters its className's opponent mob, deducts the mob's HP. */
    private void checkHit() {
        if (className.equals("Jim")) {
            Mob mob = (Mob) getOneIntersectingObject(Mob.class);
            if (mob != null) {
                mob.hit(damage, getX(), getY());
                getWorld().removeObject(this);
            }
        } else if (className.equals("mob")) {
            Jim jim = (Jim) getOneIntersectingObject(Jim.class);
            if (jim != null) {
                jim.hit(damage, getX(), getY());
                getWorld().removeObject(this);
            }
        }
    }
}

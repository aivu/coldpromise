import greenfoot.*;

/**
 * All the moving characters.
 * 
 * @author Alina Vuong
 */
public class Mover extends Actor {
    /** Mob name of the class. */
    public String name;
    /** Direction of object's movement. */
    public String direction;
    /** Speed of object's movement. */
    public int speed;

    /** Animation image fields. */
    public GreenfootImage imageR1, imageR2, imageRWalk, imageL1, imageL2, imageLWalk;
    /** Time since the last standing-bounce. */
    public int bounceDelCount = 0;
    /** Minimum required time between animation changes while standing still. */
    public int bounceDelay = 30;
    /** Time since the last walking-bounce. */
    public int walkDelCount = 0;
    /** Minimum required time between animation changes while walking. */
    public int walkDelay = 15;

    /** Health and damage fields for Mob subclasses and Jim. */
    public int health, atkDelay, dmgDelay, knockbackDelay, kbDistance;
    /** Minimum required time between instance of HP recovery. */
    public int hpDelay;
    /** Counts time since last attack. */
    public int atkDelCount = 0;
    /** Counts time since last damage taken. */
    public int dmgDelCount = 0;
    /** Counts time since last HP recovery. */
    public int hpDelCount = 0;
    /** Counts time since last knockback. */
    public int kbDelCount = 0;

    @Override
    public void act() {
        animate();
        bounceDelCount++;
        walkDelCount++;
        atkDelCount++;
        dmgDelCount++;
        hpDelCount++;
        kbDelCount++;
    }

    /** Animates the object. */
    public void animate() {
        if (bounceDelCount > bounceDelay) {
            bounceDelCount = 0;
            if ((getImage() == imageR1 || getImage() == imageR2 || getImage() == imageRWalk)) {
                if (getImage() == imageR1) {
                    setImage(imageR2);
                } else {
                    setImage(imageR1);
                }
            } else if (getImage() == imageL1 || getImage() == imageL2 || getImage() == imageLWalk) {
                if (getImage() == imageL1) {
                    setImage(imageL2);
                } else {
                    setImage(imageL1);
                }
            }
        }
    }

    /** Sets direction for this object's movement. */
    public void setDirection(String direction) {
        this.direction = direction;
        if (this.direction.equals("right")) {
            if (getImage() == imageL1 || getImage() == imageL2 || getImage() == imageLWalk) {
                setImage(imageR1);
            }
        }
        if (this.direction.equals("left")) {
            if (getImage() == imageR1 || getImage() == imageR2 || getImage() == imageRWalk) {
                setImage(imageL1);
            }
        }
    }

    /** Deducts damage from this object if detects a hit. Reference: newAsteroids */
    public void hit(int damage, int x, int y) {
        if (dmgDelCount > dmgDelay) {
            dmgDelCount = 0;
            health -= damage;
        }
        if (kbDelCount > knockbackDelay) {
            kbDelCount = 0;
            if (x > getX()) {
                this.setLocation(getX() - kbDistance, getY());
            } else if (x < getX()) {
                this.setLocation(getX() + kbDistance, getY());
            }
        }
    }

    /** Recovers HP for this object. */
    public void add(int recovery) {
        if (hpDelCount > hpDelay) {
            hpDelCount = 0;
            health += recovery;
        }
    }

    /** Fires a laser. Reference and inspiration: newAsteroids. */
    public void fire() {
        Daumscape daum = (Daumscape) getWorld();        
        if (atkDelCount > atkDelay) {
            atkDelCount = 0;
            Laser laser = new Laser(direction, name);
            daum.addObject(laser, getX(), getY());
            if (daum.soundOn) {
                Greenfoot.playSound("laser.mp3");
            }
        }
    }

    /** Moves the object. */
    public void walk() {
        if (direction.equals("right")) {
            if (walkDelCount > walkDelay) {
                walkDelCount = 0;
                if (getImage() == imageR1 || getImage() == imageR2) {
                    setImage(imageRWalk);
                } else {
                    setImage(imageR1);
                }
            }
        }
        if (direction.equals("left")) {
            if (walkDelCount > walkDelay) {
                walkDelCount = 0;
                if (getImage() == imageL1 || getImage() == imageL2) {
                    setImage(imageLWalk);
                } else {
                    setImage(imageL1);
                }
            }
        }
    }

    /** Checks if the player's health has reached 0. */
    public boolean jimOK() {
        Daumscape daum = (Daumscape) getWorld();
        if (daum.jim.health > 0) {
            return true;
        }
        return false;
    }
}

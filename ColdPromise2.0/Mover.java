import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * All the moving characters.
 * 
 * Alina Vuong
 */
public class Mover extends Actor {
    //animation fields
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
    /** Deducts damage from this object if detects a hit. */
    public void hit(int damage, int x, int y) {
        //will be invoked by the Laser and Mob classes. ref: newAsteroids
        if (dmgDelCount > dmgDelay) {
            dmgDelCount = 0;
            health -= damage;
        }
        if (kbDelCount > knockbackDelay) {
            kbDelCount = 0;
            if (x > getX()) {
                this.setLocation(getX()-kbDistance, getY());
            } else if (x < getX()) {
                this.setLocation(getX()+kbDistance, getY());
            }
        }
    }
    /** Recovers HP for this object. */
    public void add(int recovery) { //will be invoked by the Flan class. This method is really just for Jim
        if (hpDelCount > hpDelay) {
            hpDelCount = 0;
            health += recovery;
        }
    }

    public void fire() { //some parts of this and related methods were inspired by the newAsteroids project
        Daumscape daum = (Daumscape) getWorld();        
        //note: you have to declare "daum" separately from whatever statements you reference it in
        //e.g. it wouldn't work if you tried to declare it while trying to reference ".soundOn"
        if (atkDelCount > atkDelay) {
            atkDelCount = 0;
            Laser laser = new Laser(direction, name);
            daum.addObject(laser, getX(), getY());
            //the laser sound effect is included in Mover.fire() so as to employ the delay counts already being
            //used for the actual laser function
            if (daum.soundOn) { //can't put this in the Laser constructor because you can't call other objects in an object's constructor
                //I suppose because the object hasn't actually been constructed yet and nonexisting objects can't make other
                //objects do things
                Greenfoot.playSound("laser.mp3");
            }
        }
    }
    /** Moves the object. */
    public void walk() { //don't put act()
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
        if (direction.equals("left"))
        {
            if (walkDelCount>walkDelay)
            {
                walkDelCount = 0;
                if (getImage()==imageL1 || getImage()==imageL2)
                {
                    setImage(imageLWalk);
                }
                else
                {
                    setImage(imageL1);
                }
            }
        }
    }

    public boolean jimOK() 
    //this would've been the method to use in some classes' act()s if the game ended upon Jim's defeat
    {
        Daumscape daum = (Daumscape) getWorld();
        if (daum.jim.health>0) //prevents an IllegalStateException when Jim is defeated
        {
            return true;
        }
        return false;
    }
}

import greenfoot.*;
import java.util.List;

/**
 * 
 * Alina Vuong
 * 5.9.13
 */
public class Jim extends Mover {
    /** Max HP. */
    private int maxHP = 100;
    /** Whether Jim has been revived after loss of health. */
    private boolean revived;
    /** Reset after every map change; for certain bits of plot dialogue. */
    public boolean realized; 
    /** Delay count for dialogue box to appear for certain plot points. */
    public int realizeDelCount = 0; //same as above
    /** Assigned delay parameter to reach for dialogue box to appear for certain plot points. */
    private int realizeDelay = 30;
    public Jim() {
        super();
        imageR1 = new GreenfootImage("jimR1.png");
        imageR2 = new GreenfootImage("jimR2.png");
        imageRWalk = new GreenfootImage("jimRWalk.png");
        imageL1 = new GreenfootImage("jimL1.png");
        imageL2 = new GreenfootImage("jimL2.png");
        imageLWalk = new GreenfootImage("jimLWalk.png");
        setImage(imageR1);
        name = "Jim";   
        setDirection("right");
        speed = 2;
        health = maxHP;
        atkDelay = 25;
        dmgDelay = 50;
        hpDelay = 50;
        knockbackDelay = 50;
        kbDistance = 10;
    }

    @Override
    public void act() {
        super.act();
        checkKeys();
        firstRealization();
        hpLimit();
        checkHealth();
    }

    @Override
    public void animate() {
        if (!Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("left") 
            && !Greenfoot.isKeyDown("up") && !Greenfoot.isKeyDown("down")) {
            super.animate();
        }
    }

    /** Determines whether player has been blocked by a wall and cannot move. */
    private boolean okToMove() {
        Wall wall = (Wall) getOneIntersectingObject(Wall.class);
        List<DialogueBox> boxes = getWorld().getObjects(DialogueBox.class);
        if (wall != null || boxes.size() > 0) {
            return false;
        }
        return true;
    }

    /** Checks player input for Jim's movement/action. */
    private void checkKeys() {
        if (okToMove()) {
            if (Greenfoot.isKeyDown("right")) {
                setDirection("right");
                setLocation(getX() + speed, getY());
                super.walk();
            }
            if (Greenfoot.isKeyDown("left")) {
                setDirection("left");
                setLocation(getX() - speed, getY());
                super.walk();
            }
            if (Greenfoot.isKeyDown("up")) {
                setLocation(getX(), getY() - speed);
                if (walkDelCount > walkDelay) {
                    walkDelCount = 0;
                    if (getImage() == imageR1 || getImage() == imageR2) {
                        setImage(imageRWalk);
                    } else if (getImage() == imageRWalk) {
                        setImage(imageR1);
                    } else if (getImage() == imageL1 || getImage() == imageL2) {
                        setImage(imageLWalk);
                    } else if (getImage() == imageLWalk) {
                        setImage(imageL1);
                    }
                }
            }        
            if (Greenfoot.isKeyDown("down")) {
                setLocation(getX(), getY() + speed);
                if (walkDelCount > walkDelay) {
                    walkDelCount = 0;
                    if (getImage() == imageR1 || getImage() == imageR2) {
                        setImage(imageRWalk);
                    } else if (getImage() == imageRWalk) {
                        setImage(imageR1);
                    } else if (getImage() == imageL1 || getImage() == imageL2) {
                        setImage(imageLWalk);
                    } else if (getImage() == imageLWalk) {
                        setImage(imageL1);
                    }
                }
            }
            if (Greenfoot.isKeyDown("space")) {
                fire();
            }
        }
    }

    /** Adjusts HP so that it never goes below the min/max defined HP in the HP counter.
      * By default, the min is 0.
      */
    private void hpLimit() {
        if (health > maxHP) {
            health = maxHP;
        }
        if (health < 0) {
            health = 0;
        }
    }

    /** Checks health to see if it goes below the min. If so, it calls savePoint. */
    public void checkHealth() {
        if (health <= 0) {
            savePoint();
        }
    }

    /** Revives the player, resetting their HP and sending them to a previous point in the story. */
    private void savePoint() {
        if (!revived) {
            revived = true;
            Daumscape daum = (Daumscape) getWorld();    
            List<Mob >  mobs = getWorld().getObjects(Mob.class);
            getWorld().removeObjects(mobs);
            if (daum.mapNum > 1  && daum.mapNum < 4) {
                daum.mapNum = 1;
                daum.changeMap();
                health = 100;
            }        
            if (daum.mapNum > 3) {
                List<Sox >  soxes = getWorld().getObjects(Sox.class);
                getWorld().removeObjects(soxes);
                daum.mapNum = 4;
                daum.changeMap();
                health = 100;
            }
            revived = false;
        }
    }

    /** Initializes dialogue after certain map changes. */
    private void firstRealization() {        
        Daumscape daum = (Daumscape) getWorld();
        List<DialogueBox> boxes = daum.getObjects(DialogueBox.class);
        if (daum.mapNum == 0 && daum.npcCount == 1 && boxes.size() < 1) {
            realizeDelCount++;
            if (realizeDelCount > realizeDelay && !realized) {
                realized = true;
                DialogueBox box = new DialogueBox(name, 0);
                daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
                daum.npcCount--;
            }
        }
        if (daum.mapNum == 2 && !realized) {
            realized = true;
            DialogueBox box = new DialogueBox("Interns", 0);
            daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
        }
        if (daum.mapNum == 3 && !realized) {
            realized = true;
            DialogueBox box = new DialogueBox("Larimen", 3);
            daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
        }
        if (daum.mapNum == 13 && !realized) {
            realized = true;
            DialogueBox box = new DialogueBox("Sox", 10);
            daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
        }
        if (daum.mapNum == 15 && !realized)
        {
            realized = true;
            DialogueBox box = new DialogueBox("Gunther", 3);
            daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
        }
    }    
}

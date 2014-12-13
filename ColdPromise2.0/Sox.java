import greenfoot.*;
import java.util.List;

/**
 * Sox--a woollie that got trapped in the facilities.
 * Sox is a special NPC.
 * 
 * @author Alina Vuong
 * 2.23.13
 */
public class Sox extends Mover implements Speaker {
    /** Maximum distance between Jim and Sox. */
    private int distance = 80;
    /** Whether Jim has talked to Sox. */
    public boolean talkedTo = false;
    public Sox() {
        super();
        imageR1 = new GreenfootImage("soxR1.png");
        imageR2 = new GreenfootImage("soxR2.png");
        imageRWalk = new GreenfootImage("soxRWalk.png");
        imageL1 = new GreenfootImage("soxL1.png");
        imageL2 = new GreenfootImage("soxL2.png");
        imageLWalk = new GreenfootImage("soxLWalk.png");
        setImage(imageR1);
        name = "Sox";
        setDirection("right");
        speed = 1;
    }

    @Override
    public void act() {
        super.act();

        checkDirection();
        followJim();
        checkTalk();
    }    

    /** Checks the direction of the player for adjustment of Sox's orientation. */
    private void checkDirection() {
        Daumscape daum = (Daumscape) getWorld();
        if (bounceDelCount > bounceDelay) {
            if (daum.jim.getX() > getX()) {
                setDirection("right");
            } else {
                setDirection("left");
            }
        }
    }

    /** Prompts Sox to move according to the player's location. */
    private void followJim() {
        Daumscape daum = (Daumscape) getWorld();
        if ((daum.internCount+daum.bossCount) == 0) {
            if (Math.abs(daum.jim.getX() - getX()) > distance) {
                if (daum.jim.getX() > getX()) {
                    setLocation(getX() + speed, getY());
                    super.walk();
                } else if (daum.jim.getX() < getX()) {
                    setLocation(getX() - speed, getY());
                    super.walk();
                }
            }
            if (Math.abs(daum.jim.getY() - getY()) > distance)
            {
                if (daum.jim.getY() > getY()) {
                    setLocation(getX(), getY() + speed);
                    super.walk();
                }
                if (daum.jim.getY() < getY()) {
                    setLocation(getX(), getY() - speed);
                    super.walk();
                }
            }
        }
    }

    /** Checks whether the player has interacted with Sox. */
    public void checkTalk() {
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if (jim != null && !talkedTo) {
            talk();
        }
    }

    /** Initializes DialogueBox for storyline. */
    public void talk() {               
        Daumscape daum = (Daumscape) getWorld();
        if (daum.mapNum == 10 && daum.internCount == 0) {
            DialogueBox box = new DialogueBox(name, 0);
            daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);

            talkedTo = true;
            daum.npcCount--;
        }
        if (daum.mapNum == 11) {
            DialogueBox box = new DialogueBox(name, 4);
            daum.addObject(box, daum.getWidth()/2, daum.getHeight() - box.height + 50);

            talkedTo = true;
            daum.npcCount--;
        }
    }
}
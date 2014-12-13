import greenfoot.*;
import java.util.ArrayList;

/**
 * Non player character. The player interacts with them and initiates dialogue.
 * Alina Vuong
 * 2.24.13
 */
public class NPC extends Penguin implements Speaker {
    /** Tracks whether Jim has already talked to the NPC; prevents memory overflow. */
    private boolean talkedTo = false;
    /** Name of this NPC. */
    private String name;
    public NPC() {
        super();
        Daumscape daum = (Daumscape) getWorld();
        this.name = "Researcher";
        setDirection("right");
        walkDelay = 250;
    }

    public NPC(String name) {
        super();
        Daumscape daum = (Daumscape) getWorld();
        this.name = name;
        setDirection("right");
        walkDelay = 250;
    }

    @Override
    public void act() {
        super.act();
        changeDirection();
        checkTalk();
    }    

    /** Changes direction of NPC after some time. */
    private void changeDirection() {
        if (walkDelCount > walkDelay) {
            walkDelCount = 0;
            if (direction.equals("left")) {
                setDirection("right");
            } else {
                setDirection("left");
            }
        }
    }

    /** Checks whether the player has initiated dialogue. */
    public void checkTalk() {
        Daumscape daum = (Daumscape) getWorld();
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if (jim != null && !talkedTo && daum.internCount + daum.bossCount == 0) {
            talkedTo = true;
            talk();
        }
    }

    /** Initializes dialogue box for respective scenario. */
    public void talk() {
        Daumscape daum = (Daumscape) getWorld();
        daum.npcCount--;
        DialogueBox box = new DialogueBox(name, 0);
        daum.addObject(box, daum.getWidth() / 2, daum.getHeight() - box.height + 50);
    }
}

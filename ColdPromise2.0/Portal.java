import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Transports the player between maps.
 * There is one portal throughout the game.
 * 
 * Alina Vuong
 */
public class Portal extends Actor
{
    /** Whether player has attempted to access the portal. For preventing memory overflow. */
    private boolean alreadyTried = false;
    /** Image indicating the portal is open. */
    private GreenfootImage open = new GreenfootImage("lift-open.png");
    /** Image indicating the portal is closed. */
    public GreenfootImage closed = new GreenfootImage("lift-closed.png");
    public void act() {
        open();
        checkStep();
    }    

    /** Checks whether the player is trying to access the portal. */
    private void checkStep() {
        Daumscape daum = (Daumscape) getWorld();        
        Jim jim = (Jim) getOneIntersectingObject(Jim.class); 

        if (jim == null && alreadyTried == true) {
            alreadyTried = false;
        } else if (jim != null && alreadyTried == false) {
            alreadyTried = true;
            daum.changeNext();
        }
    }
    
    /** Changes the portal image to open. */
    private void open() {
        Daumscape daum = (Daumscape) getWorld();
        List<DialogueBox> boxes = daum.getObjects(DialogueBox.class);
        if (daum.bossCount == 0 && daum.internCount == 0 && daum.npcCount == 0 && boxes.size() < 1) {
            //boxes.size() parameter is so that the portal doesn't open until the conversations are over.
            this.setImage(open);
        }
    }
}

 import greenfoot.*;

/**
 * This is the class Toggle. It regulates the sound settings of the game.
 * @author Alina Vuong
 */

public class Toggle extends Actor {
    /** Setting for toggle. */
    private int tog = 0;
    /** Modes for sound settings. */
    private GreenfootImage noMusic, noSound, allSound;
    public Toggle() {
        noMusic = new GreenfootImage("noMusic.png");
        noSound = new GreenfootImage("noSound.png");
        allSound = new GreenfootImage("allSound.png");
        setImage(allSound);
    }

    @Override
    public void act() {
        checkToggle();
    }

    /** Changes sound setting variable if clicked. */
    private void checkToggle() {
        if(Greenfoot.mouseClicked(this)) {
            tog++;
            tog %= 3;
            setSound();
        }
    }

    /** Changes sound according to setting. */
    private void setSound() {
        Daumscape daum = (Daumscape) getWorld();
        if (tog == 0) {
            daum.bgmOn = true;
            daum.soundOn = true;
            setImage(allSound);
        } else if (tog == 1) {
            daum.bgmOn = false;
            setImage(noMusic);
        } else if (tog == 2) {
            daum.soundOn = false;
            setImage(noSound);
        }
    }
}
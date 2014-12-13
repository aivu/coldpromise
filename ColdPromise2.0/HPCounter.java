import greenfoot.*;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Object displaying Jim's remaining HP.
 * The Counter class from newAsteroids was a significant reference.
 * 
 * Alina Vuong
 * 2.23.13
 */
public class HPCounter extends Actor {
    /** Text color for display. */
    private static final Color textColor = new Color(70, 100, 90);
    /** Current HP to be displayed. */
    private int hp = 0;
    /** Number for HP to be updated to. Retrieved from Jim class. */
    private int newHP = 0;
    /** Text that will be displayed on the counter. */
    private String text;
    /** Length of text to be displayed. */
    private int stringLength;

    public HPCounter() {
        text = "HP: ";
        stringLength = (text.length() + 2) * 10;
        setImage(new GreenfootImage(stringLength, 16));
        GreenfootImage image = getImage();
        image.setColor(textColor);
        updateImage();
    }

    @Override
    public void act() {
        Daumscape daum = (Daumscape) getWorld();
        newHP = daum.jim.health;
        if (hp < newHP) {
            hp++;
            updateImage();
        } else if (hp > newHP) {
            hp--;
            updateImage();
        }
    }

    /** Adds to current HP count. */
    public void add(int recovery)
    {
        hp += recovery;
    }

    /** Subtracts from current HP count. */
    public void subtract(int damage)
    {
        hp -= damage;
    }

    /** Updates the display for the HP counter. */
    private void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text + hp, 1, 12);
    }
}


import greenfoot.*;
import java.util.ArrayList;
import java.util.List;
/** Daumscape -- the world class of the game. Holds all the pieces of the game.
 * @author Alina Vuong 
 * 
 */
public class Daumscape extends World {
    /** The main character. */
    public Jim jim;
    /** The portal by which to move onto the next map. */
    private Portal port;
    /** Counter displaying Jim's current HP. */
    private HPCounter counter;
    /** Sox, the other main character. */
    public Sox sox;
    /** The bosses. */
    private Collaborator larimen, calico, gunther;
    /** Toggle for music settings. */
    private Toggle toggle;
    /** Checks whether there are any remaining mobs in the map. */
    private boolean mapIsClear;
    /** The current map, indicated by number. */
    public int mapNum;
    /** Total number of maps in the game. For endgame sequence and invocation in Portal class. */
    public int totalMaps;
    /** Tracks number of undefeated bosses in the map. */
    public int bossCount;
    /** Tracks number of undefeated interns remaining in the map. */
    public int internCount;
    /** Tracks number of NPCs not yet spoken to by the player in order to move on. */
    public int npcCount;
    /** Bosses, interns, and NPCs for the assigned map, respectively. */
    private String[] counts = new String[18];
    /** Final image for endgame background. */
    private GreenfootImage space = new GreenfootImage("space.jpg");
    /** Set time for delay for final dialogue sequence. */
    private int lastBoxDelay = 250;
    /** Tracks delay for final dialogue sequence. */
    private int lastBoxDelCount = 0;
    /** For tracking beginning and end boxes from being added strangely. */
    private boolean boxAdded, lastAdded, creditsAdded;
    /** Default background music. */
    public GreenfootSound bgm = new GreenfootSound("Liquid Mountains.mp3");
    /** Default setting for background music, which is true. */
    public boolean bgmOn = true;
    /** Default setting for noise (e.g. lasers), which is true. */
    public boolean soundOn = true;
    public Daumscape() {    
        super(750, 550, 1); 
        setPaintOrder(Intro.class, DialogueBox.class, HPCounter.class, Sox.class, Jim.class, Penguin.class,
            Laser.class, Flan.class, Portal.class, Toggle.class, Wall.class);
        addObject(new Intro(), getWidth() / 2, getHeight() / 2);
        addJim();
        addCounter();
        addToggle();
        addPortal();
        addWalls();
        mapNum = 0;      
        totalMaps = counts.length - 1;
        counts[0] = "005"; //Ask Around
        counts[1] = "001"; //Regimund's Warning--save point
        counts[2] = "040"; //First Foray
        counts[3] = "141"; //2nd Wave With Larimen
        counts[4] = "001"; //Galea's Word--save point
        counts[5] = "040"; //Cell on the 6th Floor
        counts[6] = "050";
        counts[7] = "050";
        counts[8] = "060";
        counts[9] = "060";
        counts[10] = "071"; //Sox
        counts[11] = "001"; 
        counts[12] = "040"; 
        counts[13] = "151";//Calico's Obstruction
        counts[14] = "040"; //To the Basement
        counts[15] = "101"; //Gunther
        counts[16] = "000";
        counts[17] = "000"; //Escape on a Starship
        bossCount = Integer.parseInt(counts[mapNum].substring(0,1)); //makes for a lot of hardcoding but I wanted to use this method
        internCount = Integer.parseInt(counts[mapNum].substring(1,2)); //honestly you could just use List<type> objects = getObjects(type) to retrieve the mob counts
        npcCount = Integer.parseInt(counts[mapNum].substring(2,3)); //not the NPC conversation counts though.
        setCounts(); //this method must be called after mapNum and the counts array are set
        changeMap(); //this method must be called AFTER the mapNum and counts are set.
        bgm.play();
    }

    public void act() {        
        if (mapNum == 0 && !boxAdded) {
            List<Intro> intros = getObjects(Intro.class);
            if (intros.size() < 1) {
                boxAdded = true;
                addBox();
            }
        }
        if (mapNum != 17) {
            if (bgmOn) {
                if (!bgm.isPlaying()) {
                    bgm.play();
                }
            } else if (!bgmOn) {
                if (bgm.isPlaying()) {
                    bgm.stop();
                }
            }
        }
        if (mapNum == 17) {
            if (!lastAdded) {
                lastBoxDelCount++;    
                if (lastBoxDelCount > lastBoxDelay) {
                    lastBoxDelCount = 0;
                    lastAdded = true;
                    DialogueBox box = new DialogueBox("",0);
                    addObject(box, getWidth() / 2, getHeight() - box.height + 50);
                }
            }
            if (lastAdded && !creditsAdded) {
                List<DialogueBox> boxes = getObjects(DialogueBox.class);
                if (boxes.size()<1) {
                    lastBoxDelCount++;
                }
                if (lastBoxDelCount > lastBoxDelay) {
                    creditsAdded = true;
                    DialogueBox box = new DialogueBox("Credits",0);
                    addObject(box, getWidth() / 2, getHeight() - box.height + 50);
                }
            }
        }
    }

    /** Adds the player. Is called only once at the beginning of the game. */
    private void addJim() {
        jim = new Jim();
        addObject(jim, jim.getImage().getWidth(), getHeight() / 2); 
    }

    /** Adds the HP counter. Is called only once at the beginning of the game,
     *  after Jim is initialized.
     */
    private void addCounter() {
        counter = new HPCounter();
        addObject(counter, 70, getHeight()-30);
    }

    /** Adds the sound toggle. Is called only once at the beginning of the game. */
    private void addToggle() {
        toggle = new Toggle();
        addObject(toggle, getWidth() - 15, 20);
    }

    /** Adds the portal for navigating between maps. Is called only once at the beginning
     * of the game.
     */
    private void addPortal() {
        port = new Portal();
        addObject(port, getWidth()-port.getImage().getWidth() / 2 - 5, getHeight() / 2);
    }

    /** Invoked after every map change. Sets the counts for the current map's
     *  number of bosses, interns, and NPCs.
     */
    private void setCounts() {
        bossCount = Integer.parseInt(counts[mapNum].substring(0,1));
        internCount = Integer.parseInt(counts[mapNum].substring(1,2));
        npcCount = Integer.parseInt(counts[mapNum].substring(2,3)); 
    }

    /** Adds the initial dialogue box sequence only once at the beginning of the game.
     *  Explains keyboard controls.
     */
    private void addBox() {
        DialogueBox box = new DialogueBox("Instructions:", 0);
        addObject(box, getWidth() / 2, getHeight() - box.height + 50);
    }    

    /** Called after every map change. Adds intern mobs to the map. */
    private void addInterns() {
        for (int i = 0; i < internCount; i++)
        {
            Intern intern = new Intern();
            addObject(intern, Greenfoot.getRandomNumber(getWidth() - 300) + 200,
                Greenfoot.getRandomNumber(getHeight() - 100) + 50);
        }     
    }

    /** Called after every map change. Adds NPCs to the map. */
    private void addNPCs() {
        if (mapNum == 0) {
            for (int i = 0; i < npcCount - 1; i++) {
                NPC npc = new NPC();
                addObject(npc, Greenfoot.getRandomNumber(getWidth() - 200) + 100,
                    Greenfoot.getRandomNumber(getHeight() - 200) + 100);
            }
        } else if (mapNum == 1) {
            NPC regimund = new NPC("Regimund");
            addObject(regimund, Greenfoot.getRandomNumber(getWidth() - 200) + 100,
                Greenfoot.getRandomNumber(getHeight() - 200) + 100);
        } else if (mapNum == 4) {
            NPC galea = new NPC("Galea");
            addObject(galea, Greenfoot.getRandomNumber(getWidth() - 200) + 100,
                Greenfoot.getRandomNumber(getHeight() - 200) + 100);
        } else if(mapNum==10) {
            sox = new Sox();
            addObject(sox, 600, 150);
        }
    }

    /** Called after every map change. Adds boss mobs to the map. */
    private void addBosses() {
        if (mapNum == 3) {
            larimen = new Collaborator(2, 100, 20, 150);
            addObject(larimen, 500, getHeight() / 2);
        } else if (mapNum == 13) {
            calico = new Collaborator(3, 150, 30, 100);
            addObject(calico, 500, getHeight()/2);   
        } else if (mapNum == 15) {
            gunther = new Collaborator(4, 200, 35, 75);
            addObject(gunther, 500, getHeight() / 2);    
        }
    }

    /** Adds boss NPCs to the map. Called by Collaborator class. */
    public void addBossNPCs(int x, int y) {
        if(mapNum == 3) {
            NPC larimen = new NPC("Larimen");
            addObject(larimen, x, y);
        } else if(mapNum == 13) {
            NPC calico = new NPC("Calico");
            addObject(calico, x, y);
        } else if(mapNum == 15) {
            NPC gunther = new NPC("Gunther");
            addObject(gunther, x, y);
        }
    }

    /** Adds flan to certain maps. */
    private void addFlan() {
        if (mapNum == 3 || mapNum == 11 || mapNum == 14) {
            Flan flan = new Flan();
            addObject(flan, getWidth() / 2, getHeight() / 2);
        }
    }
    
    /** Creates initial wall formation for game setting. */
    private void addWalls() {
        Wall one = new Wall();
        addObject(one, 0, 0);
        one.addBase();
    }

    /** Called after every map change. Removes all objects from the NPC class from the world. */
    private void removeNPCs() {
        List<NPC> npcs = getObjects(NPC.class);
        removeObjects(npcs);
    }

    /** Removes uneaten flan from the map. */
    private void removeFlan() {
        List<Flan> flans = getObjects(Flan.class);
        removeObjects(flans);
    }

    /** Removes walls from the map. */
    private void removeWalls() {
        List<Wall> walls = getObjects(Wall.class);
        removeObjects(walls);
    }

    /** To be used for changeNext() in this class and will also be invoked in class Sox. */
    public void checkClear() {
        if ((internCount + bossCount) == 0 && npcCount <= 0) {
            mapIsClear = true;
        } else {
            mapIsClear = false;
        }
    }

    /** Invoked by the Portal class. Calls change for next map if the map has been cleared. */
    public void changeNext() {
        checkClear();
        if (mapIsClear && mapNum < totalMaps) {
            mapNum++;
            changeMap();
        }
    }    

    /** Changes the map; resets map settings, including NPCs, flan, bosses, interns, and/or walls. */
    public void changeMap() {
        if (mapNum < totalMaps) {
            removeNPCs();
            removeFlan();
            mapIsClear = false;
            jim.realized = false;
            jim.realizeDelCount = 0;
            setCounts();
            jim.setLocation(jim.getImage().getWidth(), getHeight() / 2);
            port.setImage(port.closed);

            //Make sure all mapNum-sensitive methods come AFTER the mapNum change!!
            if (mapNum > 10) {
                List<Sox> soxes = getObjects(Sox.class);
                if (soxes.size() < 1) { //for testing purposes
                    sox = new Sox(); //prevents NullPointerException if Sox isn't in the world when testing
                    addObject(sox, sox.getImage().getWidth()/2, getHeight()/2);
                } else {
                    sox.setLocation(sox.getImage().getWidth()/2, getHeight()/2);
                }
                sox.talkedTo = false;
            }
            if (jim.health < 0) {
                jim.health = 0;
            }        
            addInterns();
            addNPCs();
            addBosses();
            addFlan();
        } else if (mapNum == totalMaps) {
            setBackground(space);
            bgm.stop();

            List<Sox> soxes = getObjects(Sox.class); //can't just removeObject(sox) for some reason
            removeObjects(soxes);
            removeObject(jim);

            removeNPCs();
            removeWalls();
            removeObject(port);
            removeObject(counter);

            mapIsClear = false;
            setCounts();
            jim.setLocation(jim.getImage().getWidth(), getHeight()/2);

            Greenfoot.playSound("Always With Me.mp3");

            Spaceship ship = new Spaceship();
            addObject(ship, getWidth()/2, getHeight()/2);
        }
    }
}


import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Walls mark the boundary from the outer edge to the area where
 * the player and characters can traverse.
 * 
 * Alina Vuong
 */
public class Wall extends Actor {
    public void act() {
        checkImpact();
    }    

    /** Adds walls to the sides of the world. Make static. */
    public void addBase() {
        Daumscape daum = (Daumscape) getWorld();
        if (daum.mapNum < 17) { //doesn't apply to endgame sequence
            for (int i = 0; i < 26; i++) { //top and bottom
                Wall botWall = new Wall();
                daum.addObject(botWall, i*botWall.getImage().getWidth(), daum.getHeight()-botWall.getImage().getHeight()/2);

                Wall topWall = new Wall();
                daum.addObject(topWall, i*topWall.getImage().getWidth(), topWall.getImage().getHeight()/2);
            }
            for (int i = 0; i < 16; i++) { //upper sides
                Wall rightWall = new Wall();
                daum.addObject(rightWall, daum.getWidth()-rightWall.getImage().getWidth()/2, i*rightWall.getImage().getHeight());    

                Wall leftWall = new Wall();
                daum.addObject(leftWall, leftWall.getImage().getWidth()/2, i*leftWall.getImage().getHeight());            
            }
            for (int i = 0; i < 16; i++) { //lower sides
                Wall rightWall = new Wall();
                daum.addObject(rightWall, daum.getWidth()-rightWall.getImage().getWidth()/2, daum.getHeight()-i*rightWall.getImage().getHeight());    

                Wall leftWall = new Wall();
                daum.addObject(leftWall, leftWall.getImage().getWidth()/2, daum.getHeight()-i*leftWall.getImage().getHeight());            
            }
        }
    }

    /** Unimplemented. Intended use was to create different arrangements for maps. */
    public void addOthers() {
        Daumscape daum = (Daumscape) getWorld();
        //         if (daum.mapNum==3)
        //         {
        //             for(int i=0; i<5; i++)
        //             {
        //                 Wall wall = new Wall();
        //                 daum.addObject(wall, i*getImage().getWidth()+getImage().getWidth(), 200);
        //             }

    }

    /** Prevents the player from passing through the wall. Move to Jim class?
       */
    private void checkImpact() {
        Daumscape daum = (Daumscape) getWorld();
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if (jim != null) {
            if (Math.abs(getX() - daum.jim.getX()) > Math.abs(getY() - daum.jim.getY())) {
                if (getX() > daum.jim.getX()) {
                    daum.jim.setLocation(daum.jim.getX() - 1, daum.jim.getY());
                } else if (getX() < daum.jim.getX()) {
                    daum.jim.setLocation(daum.jim.getX() + 1, daum.jim.getY());
                }
            } else {
                if (getY() > daum.jim.getY()) {
                    daum.jim.setLocation(daum.jim.getX(), daum.jim.getY() - 1);
                } else if (getY() < daum.jim.getY()) {
                    daum.jim.setLocation(daum.jim.getX(), daum.jim.getY() + 1);
                }
            }
        }
    }
}

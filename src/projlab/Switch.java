package projlab;

import java.util.ArrayList;

public class Switch extends Tile {
    /**
        Megadja, hogy melyik Hole példányt irányítja ez a Switch.
        
        @see Hole
    */
    private Hole controlling = null;
    
    
    /**
        Kiolvassa térképfájlból a switch adatait.
        
        @param tokens   a térképfájl tokenekre bontva
        @return         swtich instance
        
        @see Map
    */
    static Switch fromString(ArrayList<String> tokens){
        return new Switch();
    }

    /**
        Kinyitja az irányított lyukat amikor egy
        doboz belép erre a mezőre.
        
        @param box          a belépő doboz
        @param direction    a mozgás iránya
        
        @see Box
    */
    @Override
    public void enter(Box box, Direction direction) {
        super.enter(box, direction);
        controlling.setClosed(!controlling.isClosed());
    }
    
    /**
        Beállítja, hogy melyik Hole példányt iránytsa a switch.
        @param controlling  az irányított Hole
        @see Hole
    */
    public void setControlling(Hole controlling) {
        this.controlling = controlling;
    }

    /**
        Bezárja az irányított lyukat amikor egy
        doboz belép erre a mezőre.
        
        @param box a mezőt elhagyó doboz
        
        @see Box
    */
    @Override
    public void leave(Box go) {
        super.leave(go);
        controlling.setClosed(!controlling.isClosed());
    }

    /**
        Visszatér a mező jelkódjával, ha üres, vagy a rajta lévő anyag vagy GameObject
        jelkódjával
        
        @return     A jelkód
    */
    @Override
    public String toString() {
        return occupiedBy == null ? "S " : occupiedBy.toString();
    }
}

package projlab;

import projlab.SyntaxError;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    /**
        A mező szomszédai az elérési irány szerint indexelve.
    */
    private HashMap<Direction, Tile> neighbors = new HashMap<>();
    
    /**
        Megadja, hogy melyik GameObject (ha egyáltalán akármelyik) foglalja el
        ezt a mezőt.
        null-ra (üres) inicializálva.
    */
    protected GameObject occupiedBy = null;
    
    /**
        Megadja, hogy milyen anyag (ha egyáltalán akármelyik) borítja
        ezt a mezőt.
        null-ra (tiszta) inicializálva.
    */
    protected Material coveredBy = null;

    /**
        Egy adott Tile-t olvas be a térképfájlból.
        
        @param m            Ehhez a térképhez tartozik a beolvasott tile
        @param tokens       a térképfile tokenekre szétszedve
        @return             a tile megfelelően inicializált példányával.
        @throws SyntaxError ha nem a térképleíró nyelv szabályainak megfelelő struktúrát
                            talál
    */
    static Tile fromString(ArrayList<String> tokens, Map m) throws SyntaxError {
        Tile t = new Tile();

        while (! tokens.get(0).equals("}")){
            String next = tokens.remove(0);
            if (next.equals(":occupiedBy")) {
                String opening = tokens.remove(0);
                String workerType = tokens.remove(0);
                if (opening.equals("{")) {
                    if (workerType.equals("Worker"))
                        t.occupiedBy = m.parse_worker(tokens, t);
                    else if (workerType.equals("Box"))
                        t.occupiedBy = m.parse_box(tokens, t);
                }
                else
                    throw new SyntaxError();
            }

            if (next.equals(":coveredBy")){
                tokens.remove(0);
                t.coveredBy = Material.fromString(Map.parse_string(tokens));
            }
        }
        tokens.remove(0);
        return t;
    }

    /**
        Definiálja az alapértelemzett viselkedést, ami akkor történik, amikor egy
        Worker elhagyja ezt a mezőt.
        
        @param worker a mezőt elhagyó worker
        @see Worker
    */
    public void leave(Worker worker) {
        this.occupiedBy = null;
    }
    
    /**
        Definiálja az alapértelemzett viselkedést, ami akkor történik, amikor egy
        Box elhagyja ezt a mezőt.
        
        @param box a mezőt elhagyó doboz
        
        @see Box
    */
    public void leave(Box box) {
        this.occupiedBy = null;
    }

    /**
        Definiálja az alapértelemzett viselkedést, ami akkor történik, amikor egy
        Worker belép erre a mezőre.
        
        @param worker       a belépő munkás
        @param direction    a mozgás iránya
        
        @see Worker
    */
    public void enter(Worker worker, Direction direction) {
        if (occupiedBy != null) {
            occupiedBy.push(direction);
        }
        worker.setTile(this);
        setOccupiedBy(worker);
    }

    /**
        Definiálja az alapértelemzett viselkedést, ami akkor történik, amikor egy
        Box belép erre a mezőre.
        
        @param box          a belépő doboz
        @param direction    a mozgás iránya
        
        @see Box
    */
    public void enter(Box box, Direction direction) {
        if (occupiedBy != null) {
            occupiedBy.push(direction);
        }
        setOccupiedBy(box);
    }
    
    /**
        Megadja, hogy egy adott munkás be tud-e lépni erre a mezőre.
        
        @param worker   A belépő munkás
        @param goingIn  A munkás mozgásának iránya
        @param force    A munkás által kifejtett nyomóerő
        
        @return         igaz, ha beléphet, hamis, ha nem léphet be
    */
    public boolean canBeEnteredBy(Worker worker, Direction goingIn, double force) {
        if (occupiedBy != null) {
            double newForce = coveredBy == null ? force : force * coveredBy.getCoefficient();
            if (occupiedBy.canBeOverPoweredBy(worker, goingIn, newForce))
                return occupiedBy.canEnter(getNeighborInDirection(goingIn), goingIn, newForce);
            return false;
        }
        return true;
    }

    /**
        Megadja, hogy egy adott doboz be tud-e lépni erre a mezőre.
        
        @param box      A belépő doboz
        @param goingIn  A munkás mozgásának iránya
        @param force    A doboz által kifejtett nyomóerő
        
        @return         igaz, ha beléphet, hamis, ha nem léphet be
    */
    public boolean canBeEnteredBy(Box box, Direction goingIn, double force){
        double newForce = coveredBy == null ? force : force * coveredBy.getCoefficient();
        if (occupiedBy != null)
            return occupiedBy.canBeOverPoweredBy(box, goingIn, newForce);
        return true;
    }

    /**
        Beállítja a coveredBy értékét
        @param coveredBy    az új anyag, ami a mezőt borítja
    */
    public void setCoveredBy(Material coveredBy) {
        this.coveredBy = coveredBy;
    }

    /**
        Beállítja a szomszédot egy adott irányban
        
        @param direction    a kívánt irány
        @param tile         a megadott irányban lévő szomszéd
    */

    public void setNeighborInDirection(Direction direction, Tile tile) {
        this.neighbors.put(direction, tile);
    }

    /**
        Megadja, hogy egy adott irányban melyik mező szomszédos ezzel a mezővel.
        
        @param direction    a kívánt irány
        
        @return             null ha nincs szomszáda az adott irányban, Tile instance
                            ha létezik
    */
    public Tile getNeighborInDirection(Direction direction){
        return neighbors.get(direction);
    }
    
    /**
        Beállítja az occupiedBy értékét
        
        @param newComer Az új GameObject ami a mezőn van
        
        @see GameObject
    */
    public void setOccupiedBy(GameObject newComer) {
        if (occupiedBy != null) {
            occupiedBy.setTile(null);
            occupiedBy.removeFromGame();
            occupiedBy = newComer;
        }
        else {
            this.occupiedBy = newComer;
            newComer.tile = this;
        }
    }

    /**
        Visszatér a mező jelkódjával, ha üres, vagy a rajta lévő anyag vagy GameObject
        jelkódjával
        
        @return     A jelkód
    */
    public String toString() {
        if (occupiedBy != null)
            return occupiedBy.toString();

        if (coveredBy != null)
            return coveredBy.toString();

        return "T ";
    }
}

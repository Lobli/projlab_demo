package projlab;

import projlab.SyntaxError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
    A Worker osztály valósítja meg a játékos által irányított munkásra vonatkozó viselkedést.
*/
public class Worker extends GameObject {
    /**
        A munkás neve.
    */
    private String name;
    
    /**
        A munkás által kifejtett erő.
        Alapúl egy random értékre van beállítva.
    */
    private int force = new Random().nextInt(50) + 50;
    
    /**
        A munkást irányító játékos által megszerzett pontszám.
        0-ra inicializálva.
    */
    private int points = 0;
    
    /**
        A munkások irányítására használt billentyűket tárolja string-ben.
        Statikus adattag.
    */
    public static HashMap<String, String> CONTROLS = setControls();

    /**
        Beállítja CONTROLS értéket a megadott irányításokra.
        
        @see CONTROLS
    */
    private static HashMap<String, String> setControls(){
        HashMap<String, String> controls = new HashMap<>();

        //player 1
        controls.put("w", "UP");
        controls.put("a", "LEFT");
        controls.put("d", "RIGHT");
        controls.put("s", "DOWN");
        controls.put("q", "PUT_HONEY");
        controls.put("e", "PUT_OIL");
        controls.put("x", "CLEAN");
        controls.put("p", "PAUSE");
        controls.put("g", "PASS");

        //player 2
        controls.put("i", "UP");
        controls.put("j", "LEFT");
        controls.put("k", "RIGHT");
        controls.put("l", "DOWN");
        controls.put("u", "PUT_HONEY");
        controls.put("o", "PUT_OIL");
        controls.put("m", "CLEAN");
        //pause ugyanaz

        return controls;
    }
    
    /**
        Konstruktor
        
        @param t A munkás által elfoglalt Tile
        @param name a munkás neve
    */
    public Worker(Tile t, String name) {
        this.tile = t;
        this.name = name;
    }
    
    /**
        Konstrukor, ahol ez erőt is meg lehet adni
        
        @param t A munkás által elfoglalt Tile
        @param name a munkás neve
        @param force a munkás ereje
    */
    public Worker(Tile t, String name, int force) {
        this.tile = t;
        this.name = name;
        this.force = force;
    }

    /**
        Egy adott munkást olvas be a térképfájlból, és
        visszatér a munkás megfelelően inicializált példányával.
        
        @param tokens       a térképfile tokenek-re szétszedve
        @param t            a munkás által elfoglalt mező
        @returns            a munkás megfelelően inicializált példányával.
        @throws SyntaxError ha nem a térképleíró nyelv szabályainak megfelelő struktúrát
                            talál
    */
    public static Worker fromString(ArrayList<String> tokens, Tile t) throws SyntaxError {
        String name = "";
        int force = -1;
        while (! tokens.get(0).equals("}")){
            String next = tokens.remove(0);
            if (next.equals(":name")) {
                tokens.remove(0);
                name = Map.parse_string(tokens);
            }
            if (next.equals(":force")){
                force = Map.parse_number(tokens);
                if (tokens.get(0).equals("RANDOM"))
                    tokens.remove(0);
            }
        }

        tokens.remove(0);

        if (name.equals(""))
            throw new SyntaxError();

        if (force > 0)
            return new Worker(t, name, force);
        else
            return new Worker(t, name);
    }
    
    /**
        A bemenetként kapott inputot az irányitásoknak megfelelően feldolgozza és
        a megfelelő akciót végrehajta.
        Nem értelmezett input esetén semmit se csinál.
        
        @param input az input String-ben
    */
    public void getInput(String input){
        input = input.toLowerCase();
        if (input.equals(""))
            return;
        switch (CONTROLS.get(input)){
            case "UP":
                move(Direction.UP);
                break;
            case  "DOWN":
                move(Direction.DOWN);
                break;
            case "LEFT":
                move(Direction.LEFT);
                break;
            case "RIGHT":
                move(Direction.RIGHT);
                break;
            case "PUT_HONEY":
                depositHoney();
                break;
            case "PUT_OIL":
                depositOil();
                break;
            case "CLEAN":
                cleanTile();
                break;
            case "PAUSE":
                controller.pauseGame();
                break;
            case "PASS":
                break;
                default:
                    break;
        }
    }
    
    /**
         A munkásra vonatkozó viselkedést megvalósító metódus.
    */
    @Override
    public void move(Direction d) {
        Tile target = tile.getNeighborInDirection(d);
         if (canEnter(target, d, getForce())) {
             tile.leave(this);
             target.enter(this, d);
         }
     }
    /**
         A munkásra vonatkozó viselkedést megvalósító metódus.
    */
    @Override
    public void push(Direction d) {
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
    }
    
    /**
        
    */
    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d, double force) {
        return true;
    }
    
    /**
    
    */
    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d, double force) {
        return false;
    }

    /**
    
    */
    @Override
    public boolean canEnter(Tile t, Direction d, double force) {
        if (t == null){
            return false;
        }
        return t.canBeEnteredBy(this, d, force);
    }

    /**
        Visszatér a munkás nevével.
    */
    public String getName() {
        return name;
    }
    
    /**
        A munkás által elfoglalt mezőt mézzel vonja be.
    */
    private void depositHoney(){
        tile.setCoveredBy(new Honey());
    }
    
    /**
        A munkás által elfoglalt mezőt olajjal vonja be.
    */
    private void depositOil(){
        tile.setCoveredBy(new Oil());
    }
    
    /**
        A munkás által elfoglalt mezőt mentesíti az azt bevonó anyagtól.
    */
    private void cleanTile() { tile.setCoveredBy(null); }

    /**
        Megadja, hogy a munkás irányító játékos hány pontot szerzett eddig.
    */
    public int getPoints() {
        return points;
    }

    /**
        Megadja, hogy a munkás mekkora erőt fejt ki.
    */
    public double getForce() {
        return force;
    }
    
    /**
        Beállítja, hogy a munkás irányító játékos hány pontot szerzett eddig.
    */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
    */
    @Override
    public void removeFromGame() {
        controller.removeWorker(this);
    }
    
    /**
        Visszatér a munkás jelkódjával, ami a nevének az első betűjének felel meg.
        @returns a jelkód
    */
    @Override
    public String toString() {
        return name.substring(0,1).toLowerCase()+" ";
    }
}

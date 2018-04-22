package projlab;

import projlab.SyntaxError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Worker extends GameObject {
    private String name;
    private int force = new Random().nextInt(50) + 50;
    private int points = 0;

    public static HashMap<String, String> CONTROLS = setControls();

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

    public Worker(Tile t, String name) {
        this.tile = t;
        this.name = name;
    }

    public Worker(Tile t, String name, int force) {
        this.tile = t;
        this.name = name;
        this.force = force;
    }

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

    @Override
    public void move(Direction d) {
        Tile target = tile.getNeighborInDirection(d);
         if (canEnter(target, d, getForce())) {
             tile.leave(this);
             target.enter(this, d);
         }
     }

    @Override
    public void push(Direction d) {
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d, double force) {
        return true;
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d, double force) {
        return false;
    }

    @Override
    public boolean canEnter(Tile t, Direction d, double force) {
        if (t == null){
            return false;
        }
        return t.canBeEnteredBy(this, d, force);
    }

    public String getName() {
        return name;
    }

    private void depositHoney(){
        tile.setCoveredBy(new Honey());
    }

    private void depositOil(){
        tile.setCoveredBy(new Oil());
    }

    private void cleanTile() { tile.setCoveredBy(null); }

    public int getPoints() {
        return points;
    }

    public double getForce() {
        return force;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void removeFromGame() {
        controller.removeWorker(this);
    }

    @Override
    public String toString() {
        return name.substring(0,1).toLowerCase()+" ";
    }
}

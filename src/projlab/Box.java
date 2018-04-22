package projlab;

import projlab.SyntaxError;
import projlab.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;

public class Box extends GameObject {
    private boolean locked = false;
    int weight = new Random().nextInt(30) + 20;

    public Box(Tile t) { this.tile = t; }

    public Box(Tile t, int weight) {
        this.tile = t;
        this.weight = weight;
    }

    public static Box fromString(ArrayList<String> tokens, Tile t) throws SyntaxError {
        int weight = -1;
        while (! tokens.get(0).equals("}")){
            if (tokens.remove(0).equals(":weight"))
                weight = Map.parse_number(tokens);
        }

        tokens.remove(0);

        if (weight > 0)
            return new Box(t, weight);

        return new Box(t);
    }

    @Override
    public void move(Direction d)  { }

    @Override
    public boolean canEnter(Tile t, Direction d, double force) {
        if (t == null){
            return false;
        }
        return t.canBeEnteredBy(this, d, force);
    }

    @Override
    public void push(Direction d) {
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
        checkLocked();
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d, double force) {
        if (force < weight)
            return false;
        return canEnter(tile.getNeighborInDirection(d), d, force - weight);
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d, double force) {
        if (force < weight)
            return false;
        return canEnter(tile.getNeighborInDirection(d), d, force - weight);
    }

    @Override
    public void removeFromGame() {
        controller.removeBox(this);
    }

    /*
    akkor locked egy láda, ha bármelyik két szomszédos oldalról be van határolva. Ennnél sokkal egyszerűbb ellenőrizni,
    hogy legalább két ellentétes oldal szabad-e (a komplementer eset).

    Teztesetek:

        FAL                 FAL
        +-+                 +-+
     FAL| | MEZŐ      MEZŐ  | | MEZŐ
        +-+                 +-+
         MEZŐ               FAL


        !OK                 OK

    */
    private void checkLocked() {
        if (tile == null)
            return;
        boolean northIsOpen = canEnter(tile.getNeighborInDirection(Direction.UP), Direction.UP, 10000);
        boolean southIsOpen = canEnter(tile.getNeighborInDirection(Direction.DOWN), Direction.DOWN, 10000);
        boolean eastIsOpen = canEnter(tile.getNeighborInDirection(Direction.RIGHT), Direction.RIGHT, 10000);
        boolean westIsOpen = canEnter(tile.getNeighborInDirection(Direction.LEFT), Direction.LEFT, 10000);
        locked = ! ( (northIsOpen && southIsOpen) || (westIsOpen && eastIsOpen) );


    }

    public boolean isLocked() {
        return locked;
    }


    @Override
    public String toString() {
        return "B ";
    }
}

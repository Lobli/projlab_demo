package projlab;

import projlab.SyntaxError;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    private HashMap<Direction, Tile> neighbors = new HashMap<>();
    protected GameObject occupiedBy = null;
    private Material coveredBy = null;

    public Tile() {
    }

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

    public void leave(GameObject go) {
        this.occupiedBy = null;
    }

    public void enter(Worker worker, Direction direction) {
        if (occupiedBy != null) {
            occupiedBy.push(direction);
        }
        worker.setTile(this);
        setOccupiedBy(worker);
    }

    public void enter(Box box, Direction direction) {
        if (occupiedBy != null) {
            occupiedBy.push(direction);
        }
        setOccupiedBy(box);
    }

    public boolean canBeEnteredBy(Worker worker, Direction goingIn, double force) {
        if (occupiedBy != null) {
            double newForce = coveredBy == null ? force : force * coveredBy.getCoefficient();
            if (occupiedBy.canBeOverPoweredBy(worker, goingIn, newForce))
                return occupiedBy.canEnter(getNeighborInDirection(goingIn), goingIn, newForce);
            return false;
        }
        return true;
    }

    public boolean canBeEnteredBy(Box box, Direction goingIn, double force){
        double newForce = coveredBy == null ? force : force * coveredBy.getCoefficient();
        if (occupiedBy != null)
            return occupiedBy.canBeOverPoweredBy(box, goingIn, newForce);
        return true;
    }

    public void setCoveredBy(Material coveredBy) {
        this.coveredBy = coveredBy;
    }

    public void setNeighborInDirection(Direction direction, Tile tile) {
        this.neighbors.put(direction, tile);
    }

    public Tile getNeighborInDirection(Direction direction){
        return neighbors.get(direction);
    }

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

    public String toString() {
        if (occupiedBy != null)
            return occupiedBy.toString();

        if (coveredBy != null)
            return coveredBy.toString();

        return "T ";
    }
}

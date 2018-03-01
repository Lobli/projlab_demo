package projlab;

import java.util.HashMap;

public class Tile {
    private boolean locked;
    protected HashMap<Direction, Tile> neighbors;
    protected GameObject occupiedBy;

    public Tile(){
        locked = false;
        neighbors = new HashMap<>();
        occupiedBy = null;
    }

    public void leave(GameObject go){
        this.occupiedBy = null;
    }

    public void enter(Worker worker, Direction direction){
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        worker.setTile(this);
        setOccupiedBy(worker);
    }

    public void enter(Box box, Direction direction){
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        box.tile = this;
        this.occupiedBy = box;
        locked = checkLocked();
    }

    public boolean canBeEnteredBy(Worker worker, Direction goingIn){
        if(occupiedBy != null){
            if (occupiedBy.canBeOverPoweredBy(worker, goingIn)) {
                return occupiedBy.canEnter(getNeighborInDirection(goingIn), goingIn);
            }
            return false;
        }
        return true;
    }

    public boolean canBeEnteredBy(Box box, Direction goingIn){
        if(occupiedBy != null){
            if (occupiedBy.canBeOverPoweredBy(box, goingIn)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public void setNeighborInDirection(Direction direction, Tile tile) {
        this.neighbors.put(direction, tile);
    }

    public Tile getNeighborInDirection(Direction direction){
        return neighbors.get(direction);
    }

    public void setOccupiedBy(GameObject occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    /*
    akkor locked egy láda, ha bármelyik két szomszédos oldalról be van határolva. Ennnél sokkal egyszerűbb ellenőrizni,
    hogy legalább két ellentétes oldal szabad-e (a komplementer eset).

    Teztesetek:

        FAL                 CSEMPE
        +-+                 +-+
     FAL| | CSEMPE   CSEMPE | | FAL
        +-+                 +-+
         CSEMPE             FAL


        TRUE                FALSE

    */
    private boolean checkLocked() {
        boolean northIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.UP), Direction.UP);
        boolean southIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        boolean eastIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.RIGHT), Direction.RIGHT);
        boolean westIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.LEFT), Direction.LEFT);

        return ! ( (northIsOpen && southIsOpen) || (westIsOpen && eastIsOpen) );

    }

    public boolean isLocked() {
        return locked;
    }

    public String toString() {
        return occupiedBy == null ? "T" : occupiedBy.toString();
    }
}

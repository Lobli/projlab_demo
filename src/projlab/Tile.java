package projlab;

import java.util.HashMap;

public class Tile {
    private boolean locked;
    protected HashMap<Direction, Tile> neighbors;
    private GameObject occupiedBy;

    public Tile(){
        locked = false;
        neighbors = null;
        occupiedBy = null;
    }

    public void leave(GameObject go){
        this.occupiedBy = null;
    }

    public void enter(Worker worker, Direction direction){
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        worker.tile = this;
        this.occupiedBy = worker;
    }

    public void enter(Box box, Direction direction){
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        box.tile = this;
        this.occupiedBy = box;
        checkLocked();
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
                //return occupiedBy.canEnter(getNeighborInDirection(goingIn), goingIn);
                return true;
            }
            return false;
        }
        return true;
    }


    public void setNeighbors(HashMap<Direction, Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public Tile getNeighborInDirection(Direction d){
        return neighbors.get(d);
    }

    public void setOccupiedBy(GameObject occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    /*   W
        +-+
       W| | T   akkor locked egy láda, ha bármelyik két szomszédos oldalról be van határolva. Ennnél sokkal egyszerűbb
        +-+     ellenőrizni, hogy legalább két ellentétes oldal szabad-e (a komplementer eset).
         T
         T
        +-+
      W | | W
        +-+
         T
    */
    public void checkLocked() {
        boolean northIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.UP), Direction.UP);
        boolean southIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        boolean eastIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        boolean westIsOpen = occupiedBy.canEnter(getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        locked = (northIsOpen && southIsOpen) || (westIsOpen && eastIsOpen);

    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return occupiedBy == null ? "T" : occupiedBy.toString();
    }
}

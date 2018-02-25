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

    public void setOccupiedBy(GameObject occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public void setNeighbors(HashMap<Direction, Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean canEnter(Worker worker, Direction goingIn){
        if(occupiedBy == null){
            checkLocked();
            return true;
        }
        else {
            return occupiedBy.push(goingIn);
        }
    }

    public boolean canEnter(Box box, Direction goingIn){
        if(occupiedBy == null){
            return true;
        }
        else {
            return occupiedBy.push(goingIn);
        }
    }

    public void enter(Worker worker, Direction direction){
        worker.tile = this;
    }

    public void enter(Box box, Direction direction){
        box.tile = this;
    }

    public Tile getNeigborInDirection(Direction d){
        return neighbors.get(d);
    }

    public void checkLocked() {
        if (occupiedBy != null) {
            for (Tile t : neighbors.values()) {

            }
        }
    }

    public boolean isLocked() {
        return locked;
    }
}

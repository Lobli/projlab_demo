import java.util.HashMap;

public class Tile {
    private boolean locked;
    protected HashMap<Direction, Tile> neighbors;
    private GameObject occupiedBy;

    public Tile(HashMap<Direction, Tile> n){
        locked = false;
        neighbors = n;
        occupiedBy = null;
    }

    public Tile(HashMap<Direction, Tile> n, GameObject g){
        locked = false;
        neighbors = n;
        occupiedBy = g;
    }

    public boolean canEnter(Worker worker, Direction goingIn){
        if(occupiedBy == null){
            return true;
        }
        else {
            return getNeigborInDirection(goingIn).canEnter(worker, goingIn);
        }
    }

    public boolean canEnter(Box box, Direction goingIn){
        if(occupiedBy == null){
            return true;
        }
        else {
            return getNeigborInDirection(goingIn).canEnter(box, goingIn);
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

package projlab;

import java.util.HashMap;

public class Tile {
    String name;
    protected HashMap<Direction, Tile> neighbors = new HashMap<>();
    protected GameObject occupiedBy = null;

    public Tile(String name){
        this.name = name;
    }

    public void leave(GameObject go){
        SkeletonHelper.call(name, go.name, "leave");
        this.occupiedBy = null;
        SkeletonHelper.returnF();
    }

    public void enter(Worker worker, Direction direction){
        SkeletonHelper.call(name, new String[]{worker.name, direction.toString()}, "enter");
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        worker.setTile(this);
        setOccupiedBy(worker);
        SkeletonHelper.returnF();
    }

    public void enter(Box box, Direction direction){
        SkeletonHelper.call(name, new String[]{box.name, direction.toString()}, "enter");
        if (occupiedBy != null){
            occupiedBy.push(direction);
        }
        box.tile = this;
        this.occupiedBy = box;
        SkeletonHelper.returnF();
    }

    public boolean canBeEnteredBy(Worker worker, Direction direction){
        SkeletonHelper.call(name, new String[]{worker.name, direction.toString()}, "canBeEnteredBy");
        if(occupiedBy != null){
            if (occupiedBy.canBeOverPoweredBy(worker, direction)) {
                boolean ret = occupiedBy.canEnter(getNeighborInDirection(direction), direction);
                SkeletonHelper.returnF();
                return ret;
            }
            SkeletonHelper.returnF();
            return false;
        }
        SkeletonHelper.returnF();
        return true;
    }

    public boolean canBeEnteredBy(Box box, Direction direction){
        SkeletonHelper.call(name, new String[]{box.name, direction.toString()}, "canBeEnteredBy");
        if(occupiedBy != null){
            if (occupiedBy.canBeOverPoweredBy(box, direction)) {
                SkeletonHelper.returnF();
                return true;
            }
            SkeletonHelper.returnF();
            return false;
        }
        SkeletonHelper.returnF();
        return true;
    }

    public void setNeighborInDirection(Direction direction, Tile tile) {
        if (tile == null)
            SkeletonHelper.call(name, new String[]{direction.toString(), "null"}, "setNeighborInDirection");
        else
            SkeletonHelper.call(name, new String[]{direction.toString(), tile.name}, "setNeighborInDirection");
        this.neighbors.put(direction, tile);
        SkeletonHelper.returnF();
    }

    public Tile getNeighborInDirection(Direction direction){
        SkeletonHelper.call(name, direction.toString(), "getNeighborInDirection");
        Tile t = neighbors.get(direction);
        SkeletonHelper.returnF();
        return t;
    }

    public void setOccupiedBy(GameObject occupiedBy) {
        SkeletonHelper.call(name, occupiedBy.name, "setOccupiedBy");
        this.occupiedBy = occupiedBy;
        SkeletonHelper.returnF();
    }

    public String toString() {
        return occupiedBy == null ? "T" : occupiedBy.toString();
    }
}

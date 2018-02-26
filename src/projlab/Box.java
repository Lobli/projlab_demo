package projlab;

public class Box extends GameObject {
    @Override
    public void move(Direction d) {
        tile.leave(this);
    }

    @Override
    public boolean canEnter(Tile t, Direction d) {
        System.out.println("Can a box enter this tile?");
        if (tile.getNeighborInDirection(d) == null){
            return false;
        }
        return t.canBeEnteredBy(this, d);
    }

    @Override
    public void push(Direction d) {
        System.out.println("Box pushed");
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d) {
        return canEnter(tile.getNeighborInDirection(d), d);
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d) {
        return canEnter(tile.getNeighborInDirection(d), d);
    }

    @Override
    public String toString() {
        return "B";
    }
}
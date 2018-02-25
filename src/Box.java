public class Box extends GameObject {
    @Override
    public void move(Direction d) {
        tile.getNeigborInDirection(d).enter(this, d);
    }

    @Override
    public boolean canTakePlace(Worker w, Direction d) {
        //if can silde over in direction: go
        //else
        return false;
    }

    @Override
    public boolean canTakePlace(Box b, Direction d) {
        //if can slide over: go
        //else:
        return false;
    }
}
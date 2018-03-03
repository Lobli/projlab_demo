package projlab;

public class Switch extends Tile {
    private Hole controlling;

    public Switch() {
        controlling = null;
    }

    @Override
    public void enter(Box box, Direction direction) {
        super.enter(box, direction);
        controlling.setClosed(!controlling.isClosed());
    }

    public void setControlling(Hole controlling) {
        this.controlling = controlling;
    }


    @Override
    public void leave(GameObject go) {
        super.leave(go);
        controlling.setClosed(!controlling.isClosed());
    }

    @Override
    public String toString() {
        return occupiedBy == null ? "S" : occupiedBy.toString();
    }
}

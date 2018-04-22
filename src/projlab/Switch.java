package projlab;

import java.util.ArrayList;

public class Switch extends Tile {
    private Hole controlling = null;

    public Switch() { }

    static Switch fromString(ArrayList<String> tokens){
        return new Switch();
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
    public void leave(Box go) {
        super.leave(go);
        controlling.setClosed(!controlling.isClosed());
    }


    @Override
    public String toString() {
        return occupiedBy == null ? "S " : occupiedBy.toString();
    }
}

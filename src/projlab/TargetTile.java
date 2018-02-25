package projlab;

import java.util.HashMap;

public class TargetTile extends Tile {
    Worker w;

    public TargetTile(Worker w) {
        this.w = w;
    }

    @Override
    public void enter(Box b, Direction d) {
        w.setPoints(w.getPoints() + 500);
    }
}

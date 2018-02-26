package projlab;

import java.util.HashMap;

public class Hole extends Tile {
    @Override
    public void enter(Box b, Direction d) {
        b = null;
    }

    @Override
    public void enter(Worker w, Direction d) {
        super.enter(w, d);
        w.kill();
    }

    @Override
    public String toString() {
        return "H";
    }
}

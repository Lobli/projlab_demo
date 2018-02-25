package projlab;

import java.util.HashMap;

public class Hole extends Tile {
    @Override
    public void enter(Box b, Direction d) {
        super.enter(b, d);
        b = null;
    }

    @Override
    public void enter(Worker w, Direction d) {
        super.enter(w, d);
        w.kill();
    }
}

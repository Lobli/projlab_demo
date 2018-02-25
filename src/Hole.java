import java.util.HashMap;

public class Hole extends Tile {
    public Hole(HashMap<Direction, Tile> n) {
        super(n);
    }

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

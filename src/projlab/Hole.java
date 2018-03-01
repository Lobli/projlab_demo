package projlab;

public class Hole extends Tile {
    @Override
    public void enter(Box b, Direction d) {
        b.removeFromGame();
    }


    @Override
    public void enter(Worker w, Direction d) {
        super.enter(w, d);
        w.removeFromGame();
    }

    @Override
    public String toString() {
        return "H";
    }
}

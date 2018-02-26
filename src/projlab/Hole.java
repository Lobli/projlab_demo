package projlab;

public class Hole extends Tile {
    @Override
    public void enter(Box b, Direction d) {
        removeBox(b);
    }

    void removeBox(Box b){
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

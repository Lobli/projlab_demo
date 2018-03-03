package projlab;

public class Hole extends Tile {

    boolean closed;

    public Hole() {
        closed = false;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void enter(Box b, Direction d) {
        if (isClosed()) {
            b.removeFromGame();
        }
    }


    @Override
    public void enter(Worker w, Direction d) {
        super.enter(w, d);
        if(isClosed()) {
            w.removeFromGame();
        }
    }

    @Override
    public String toString() {
        return "H";
    }
}

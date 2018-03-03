package projlab;

public class Hole extends Tile {

    private boolean closed;

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
        if (closed) {
            b.removeFromGame();
        }
    }


    @Override
    public void enter(Worker w, Direction d) {
        super.enter(w, d);
        if(closed) {
            w.removeFromGame();
        }
    }

    @Override
    public String toString() {
        return "H";
    }
}

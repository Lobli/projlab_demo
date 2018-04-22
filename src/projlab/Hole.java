package projlab;

public class Hole extends Tile {

    private boolean closed = true;

    public Hole() { }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void enter(Box b, Direction d) {
        if (closed)
            super.enter(b, d);
        else
            b.removeFromGame();
    }


    @Override
    public void enter(Worker w, Direction d) {
        if(closed)
            super.enter(w,d);
        else
            w.removeFromGame();

    }

    @Override
    public String toString() {
        return closed ? "T " : "H ";
    }
}

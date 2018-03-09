package projlab;

public class Hole extends Tile {

    private boolean closed;

    public Hole(String name) {
        super(name);
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
        SkeletonHelper.call(name, new String[]{b.name, d.toString()}, "enter");
        if (SkeletonHelper.decide("Be van zárva a lyuk?")) {
            b.removeFromGame();
        }
        SkeletonHelper.returnF();
    }


    @Override
    public void enter(Worker w, Direction d) {
        SkeletonHelper.call(name, new String[]{w.name, d.toString()}, "enter");
        super.enter(w, d);
        if(SkeletonHelper.decide("Be van zárva a lyuk?")) {
            w.removeFromGame();
        }
        SkeletonHelper.returnF();
    }

    @Override
    public String toString() {
        return "H";
    }
}

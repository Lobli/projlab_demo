package projlab;

public class Switch extends Tile {
    private boolean closed;
    private Tile controlling;

    public Switch() {
        closed = true;
        controlling = null;
    }

    public void toggle(){
        closed = !closed;
        Tile t;
        if (closed){
            t = new Tile();
        }
        else {
            t = new Hole();
        }
        t.setNeighbors(controlling.neighbors);
        setControlling(t);
    }

    @Override
    public void enter(Box box, Direction direction) {
        super.enter(box, direction);
        toggle();
    }

    public void setControlling(Tile controlling) {
        this.controlling = controlling;
    }

    @Override
    public void leave(GameObject go) {
        super.leave(go);
        toggle();
    }

    @Override
    public String toString() {
        return "S";
    }
}

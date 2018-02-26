package projlab;

public class Switch extends Tile {
    private boolean closed;
    private Tile controlling;

    public Switch() {
        closed = true;
        controlling = null;
    }

    public void toggle(){
        if (closed){
            controlling = new Hole();
            controlling.setNeighbors(controlling.neighbors);
        }
        else {
            controlling = new Hole();
            controlling.setNeighbors(controlling.neighbors);
        }
        closed = !closed;
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
    public String toString() {
        return "S";
    }
}

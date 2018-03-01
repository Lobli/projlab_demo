package projlab;

public class Switch extends Tile {
    private boolean closed;
    private Tile controlling;

    public Switch() {
        closed = true;
        controlling = null;
    }

    public void toggle(){
        flipSwitch();
        Tile t;
        if (closed){
            t = new Tile();
        }
        else {
            t = new Hole();
        }
        t.neighbors = controlling.neighbors;
        setControlling(t);
        System.out.println("Switch toggled!");
    }

    @Override
    public void enter(Box box, Direction direction) {
        super.enter(box, direction);
        toggle();
    }

    public void setControlling(Tile controlling) {
        this.controlling = controlling;
    }

    private void flipSwitch(){
        closed = !closed;
    }

    @Override
    public void leave(GameObject go) {
        super.leave(go);
        toggle();
    }

    @Override
    public String toString() {
        return occupiedBy == null ? "S" : occupiedBy.toString();
    }
}

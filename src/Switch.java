import java.util.HashMap;

public class Switch extends Tile {
    private boolean closed;
    private Tile controlling;

    public Switch(HashMap<Direction, Tile> n, Tile controlling) {
        super(n);
        this.controlling = controlling;
    }

    public Switch(HashMap<Direction, Tile> n, GameObject g, Tile controlling) {
        super(n, g);
        this.controlling = controlling;
    }

    public void toggle(){
        if (closed){
            controlling = new Hole(controlling.neighbors);
        }
        else {
            controlling = new Tile(controlling.neighbors);
        }
        closed = !closed;
    }

    @Override
    public void enter(Box box, Direction direction) {
        super.enter(box, direction);
        toggle();
    }
}

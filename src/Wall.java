import java.util.HashMap;

public class Wall extends Tile {
    public Wall(HashMap<Direction, Tile> n) {
        super(n);
    }

    public Wall(HashMap<Direction, Tile> n, GameObject g) {
        super(n, g);
    }

    @Override
    public void enter(Worker worker, Direction direction) {
        //cannot into wall
    }

    @Override
    public void enter(Box b, Direction d) {
        //cannot into wall
    }
}

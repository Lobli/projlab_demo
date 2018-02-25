package projlab;

import java.util.HashMap;

public class TargetTile extends Tile {

    TargetTile(HashMap<Direction, Tile> n){
        super(n);
    }

    TargetTile(HashMap<Direction, Tile> n, GameObject g){
        super(n, g);
    }

    @Override
    public void enter(Worker w, Direction d) {
        w.setPoints(w.getPoints() + 500);
    }
}

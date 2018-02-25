package projlab;

import java.util.HashMap;

public class Wall extends Tile {
    @Override
    public boolean canEnter(Box box, Direction goingIn) {
        return false;
    }

    @Override
    public boolean canEnter(Worker worker, Direction goingIn) {
        return false;
    }
}

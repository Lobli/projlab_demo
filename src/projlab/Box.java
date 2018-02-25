package projlab;

public class Box extends GameObject {
    @Override
    public void move(Direction d) {

    }

    @Override
    public boolean push(Direction d) {
        if (tile.getNeigborInDirection(d).canEnter(this,d)){
            tile.getNeigborInDirection(d).enter(this, d);
            return true;
        }
        return false;
    }
}
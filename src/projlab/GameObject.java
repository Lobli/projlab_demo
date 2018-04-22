package projlab;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import projlab.SyntaxError;

import java.util.ArrayList;

public abstract class GameObject {
    protected Tile tile;
    protected GameEngine controller = null;

    public abstract void move(Direction d);

    public abstract void push(Direction d);

    public static GameObject fromString(ArrayList<String> tokens, Tile t) throws SyntaxError, NotImplementedException {
        throw new NotImplementedException();
    };

    public abstract boolean canBeOverPoweredBy(Worker w, Direction d, double force);

    public abstract boolean canBeOverPoweredBy(Box b, Direction d, double force);

    public abstract boolean canEnter(Tile t, Direction d, double force);

    public void setController(GameEngine g) {
        controller = g;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public abstract void removeFromGame();
}
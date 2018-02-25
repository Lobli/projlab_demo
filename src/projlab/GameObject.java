package projlab;

public abstract class GameObject {
    protected Tile tile;

    public abstract void move(Direction d);
    public abstract boolean push(Direction d);
}

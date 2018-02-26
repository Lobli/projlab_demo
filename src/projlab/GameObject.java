package projlab;

public abstract class GameObject {
    protected Tile tile;

    public abstract void move(Direction d);

    public abstract void push(Direction d);

    public abstract boolean canBeOverPoweredBy(Worker w, Direction d);

    public abstract boolean canBeOverPoweredBy(Box b, Direction d);

    public abstract boolean canEnter(Tile t, Direction d);
}
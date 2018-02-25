public abstract class GameObject {
    protected Tile tile;

    public abstract void move(Direction d);
    public abstract boolean canTakePlace(Worker w, Direction d);
    public abstract boolean canTakePlace(Box b, Direction d);

}

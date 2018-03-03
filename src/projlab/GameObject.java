package projlab;

public abstract class GameObject {
    protected Tile tile;
    protected GameEngine controller;

    public abstract void move(Direction d);

    public abstract void push(Direction d);

    public abstract boolean canBeOverPoweredBy(Worker w, Direction d);

    public abstract boolean canBeOverPoweredBy(Box b, Direction d);

    public abstract boolean canEnter(Tile t, Direction d);

    public void setController(GameEngine g) {
        controller = g;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public abstract void removeFromGame();
}
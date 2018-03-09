package projlab;

public abstract class GameObject {
    protected Tile tile;
    protected GameEngine controller;
    String name;

    public GameObject(String name) {
        this.name = name;
    }

    public abstract void move(Direction d);

    public abstract void push(Direction d);

    public abstract boolean canBeOverPoweredBy(Worker w, Direction d);

    public abstract boolean canBeOverPoweredBy(Box b, Direction d);

    public abstract boolean canEnter(Tile t, Direction d);

    public void setController(GameEngine controller) {
        SkeletonHelper.call(name, "controller", "setController");
        this.controller = controller;
        SkeletonHelper.returnF();
    }

    public void setTile(Tile tile) {
        SkeletonHelper.call(name, tile.name, "setTile");
        this.tile = tile;
        SkeletonHelper.returnF();
    }

    public abstract void removeFromGame();
}
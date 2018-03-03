package projlab;

public class Worker extends GameObject {
    private int points;
    private GameEngine controller;
    private String name;

    public Worker(String name){
        this.name = name;
        this.points = 0;
        this.controller = null;
    }

    @Override
    public void move(Direction d) {
        Tile target = tile.getNeighborInDirection(d);
         if (canEnter(target, d)) {
             System.out.println("Move out!");
             tile.leave(this);
             target.enter(this, d);
         }
     }

    @Override
    public void push(Direction d) {
        System.out.println("Worker pushed");
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d) {
        return true;
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d) {
        return false;
    }

    @Override
    public boolean canEnter(Tile t, Direction d) {
        System.out.println("Can a worker enter this tile?");
        if (tile.getNeighborInDirection(d) == null){
            return false;
        }
        return t.canBeEnteredBy(this, d);
    }

    public void setController(GameEngine controller) {
        this.controller = controller;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void removeFromGame() {
        controller.removeWorker(this);
    }

    @Override
    public String toString() {
        return "P";
    }
}

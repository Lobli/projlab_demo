package projlab;

public class Worker extends GameObject {
    private int points;
    private GameEngine controller;

    public Worker(){
        points = 0;
        this.controller = controller;
    }

    @Override
    public void move(Direction d) {
         if (canEnter(tile.getNeighborInDirection(d), d)) {
             System.out.println("Move out!");
             tile.leave(this);
             tile.getNeighborInDirection(d).enter(this, d);
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

    public void kill(){
        System.out.print("Worker dead!");
        controller.killWorker(this);
    }

    public void setController(GameEngine controller) {
        this.controller = controller;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "P";
    }
}

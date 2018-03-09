package projlab;

public class Worker extends GameObject {
    private int points = 0;
    private GameEngine controller = null;

    public Worker(String name){
        super(name);
    }

    @Override
    public void move(Direction d) {
        SkeletonHelper.call(name, d.toString(), "move");
        Tile target = tile.getNeighborInDirection(d);
         if (canEnter(target, d)) {
             tile.leave(this);
             target.enter(this, d);
         }
         SkeletonHelper.returnF();
     }

    @Override
    public void push(Direction d) {
        SkeletonHelper.call(name, d.toString(), "push");
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
        SkeletonHelper.returnF();
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d) {
        SkeletonHelper.call(name, new String[]{b.name, d.toString()}, "canBeOverPoweredBy");
        SkeletonHelper.returnF();
        return true;
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d) {
        SkeletonHelper.call(name, new String[]{w.name, d.toString()}, "canBeOverPoweredBy");
        SkeletonHelper.returnF();
        return false;
    }

    @Override
    public boolean canEnter(Tile t, Direction d) {
        SkeletonHelper.call(name, new String[]{t.name, d.toString()}, "canEnter");
        if (tile.getNeighborInDirection(d) == null){
            return false;
        }
        SkeletonHelper.returnF();
        return t.canBeEnteredBy(this, d);
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        SkeletonHelper.call(name, "", "getPoints");
        SkeletonHelper.returnF();
        return points;
    }

    public void setPoints(int points) {
        SkeletonHelper.call(name, "", "setPoints");
        SkeletonHelper.returnF();
        this.points = points;
    }

    @Override
    public void removeFromGame() {
        SkeletonHelper.call(name, "", "removeFromGame");
        SkeletonHelper.returnF();
        controller.removeWorker(this);
    }

    @Override
    public String toString() {
        return "P";
    }
}

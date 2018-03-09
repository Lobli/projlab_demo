package projlab;

public class Wall extends Tile {
    public Wall(String name) {
        super(name);
    }

    @Override
    public void enter(Worker worker, Direction direction) {
        SkeletonHelper.call(name, new String[]{worker.name, direction.toString()}, "enter");
        worker.removeFromGame();
        SkeletonHelper.returnF();
    }

    @Override
    public boolean canBeEnteredBy(Box box, Direction direction) {
        SkeletonHelper.call(name, new String[]{box.name, direction.toString()}, "canBeEnteredBy");
        SkeletonHelper.returnF();
        return false;
    }

    @Override
    public boolean canBeEnteredBy(Worker worker, Direction direction) {
        SkeletonHelper.call(name, new String[]{worker.name, direction.toString()}, "canBeEnteredBy");
        SkeletonHelper.returnF();
        return false;
    }

    @Override
    public String toString() {
        return "W";
    }
}

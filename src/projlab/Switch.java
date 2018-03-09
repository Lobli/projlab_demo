package projlab;

public class Switch extends Tile {
    private Hole controlling;

    public Switch(String name) {
        super(name);
        controlling = null;
    }

    @Override
    public void enter(Box box, Direction direction) {
        SkeletonHelper.call(name, new String[]{box.name, direction.toString()}, "enter");
        super.enter(box, direction);
        controlling.setClosed(!controlling.isClosed());
        SkeletonHelper.returnF();
    }

    public void setControlling(Hole controlling) {
        SkeletonHelper.call(name, controlling.name, "setControlling");
        this.controlling = controlling;
        SkeletonHelper.returnF();
    }


    @Override
    public void leave(GameObject go) {
        SkeletonHelper.call(name, go.name, "leave");
        super.leave(go);
        controlling.setClosed(true);
        SkeletonHelper.returnF();
    }

    @Override
    public String toString() {
        return occupiedBy == null ? "S" : occupiedBy.toString();
    }
}

package projlab;

public class TargetTile extends Tile {
    Worker belongsTo;

    public TargetTile(String name, Worker w) {
        super(name);
        this.belongsTo = w;
    }


    @Override
    public void enter(Box b, Direction d) {
        SkeletonHelper.call(name, new String[]{b.name, d.toString()}, "enter");
        belongsTo.setPoints(belongsTo.getPoints() + 500);
        b.removeFromGame();
        SkeletonHelper.returnF();
    }

    @Override
    public String toString() {
        return "O";
    }
}

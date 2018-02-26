package projlab;

public class TargetTile extends Tile {
    Worker belongsTo;

    public TargetTile(Worker w) {
        this.belongsTo = w;
    }

    @Override
    public void enter(Box b, Direction d) {
        belongsTo.setPoints(belongsTo.getPoints() + 500);
        b = null;
    }

    @Override
    public String toString() {
        return "O";
    }
}

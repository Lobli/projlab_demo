package projlab;

public class TargetTile extends Tile {
    Worker belongsTo;

    public TargetTile() { }

    @Override
    public void enter(Box b, Direction d) {
        belongsTo.setPoints(belongsTo.getPoints() + 500);
        b.setTile(null);
        b.removeFromGame();
    }

    @Override
    public String toString() {
        return occupiedBy != null ? occupiedBy.toString() : "t ";
    }
}

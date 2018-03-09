package projlab;

public class Box extends GameObject {
    boolean locked = false;

    public Box(String name) {
        super(name);
    }

    @Override
    public void move(Direction d) {
        SkeletonHelper.call(name, d.toString(), "move");
        tile.leave(this);
        SkeletonHelper.returnF();
    }

    @Override
    public boolean canEnter(Tile t, Direction d) {
        if (tile.getNeighborInDirection(d) == null){
            SkeletonHelper.call(name, new String[]{"null", d.toString()}, "canEnter");
            SkeletonHelper.returnF();
            return false;
        }
        SkeletonHelper.call(name, new String[]{t.name, d.toString()}, "canEnter");
        boolean canbeEntered = t.canBeEnteredBy(this, d);

        SkeletonHelper.returnF();
        return canbeEntered;
    }

    @Override
    public void push(Direction d) {
        SkeletonHelper.call(name, d.toString(), "push");
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
        locked = checkLocked();
        SkeletonHelper.returnF();
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d) {
        SkeletonHelper.call(name, new String[]{b.name, d.toString()}, "canBeOverPoweredBy");
        boolean ret = canEnter(tile.getNeighborInDirection(d), d);
        SkeletonHelper.returnF();
        return ret;
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d) {
        SkeletonHelper.call(name, new String[]{w.name, d.toString()}, "canBeOverPoweredBy");
        boolean ret = canEnter(tile.getNeighborInDirection(d), d);
        SkeletonHelper.returnF();
        return ret;
    }

    @Override
    public void removeFromGame() {
        SkeletonHelper.call(name, "", "removeFromGame");
        controller.removeBox(this);
        SkeletonHelper.returnF();
    }

    /*
    akkor locked egy láda, ha bármelyik két szomszédos oldalról be van határolva. Ennnél sokkal egyszerűbb ellenőrizni,
    hogy legalább két ellentétes oldal szabad-e (a komplementer eset).

    Teztesetek:

        FAL                 FAL
        +-+                 +-+
     FAL| | MEZŐ      MEZŐ  | | MEZŐ
        +-+                 +-+
         MEZŐ               FAL


        !OK                 OK

    */
    private boolean checkLocked() {
        SkeletonHelper.call(name, "", "checkLocked");
        boolean northIsOpen = canEnter(tile.getNeighborInDirection(Direction.UP), Direction.UP);
        boolean southIsOpen = canEnter(tile.getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        boolean eastIsOpen = canEnter(tile.getNeighborInDirection(Direction.RIGHT), Direction.RIGHT);
        boolean westIsOpen = canEnter(tile.getNeighborInDirection(Direction.LEFT), Direction.LEFT);
        boolean ret = ! ( (northIsOpen && southIsOpen) || (westIsOpen && eastIsOpen) );
        SkeletonHelper.returnF();
        return ret;
    }

    public boolean isLocked() {
        SkeletonHelper.call(name, "", "isLocked");
        SkeletonHelper.returnF();
        return locked;
    }


    @Override
    public String toString() {
        return "B";
    }
}

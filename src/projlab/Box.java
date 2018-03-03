package projlab;

public class Box extends GameObject {
    boolean locked;

    public Box() {
        this.locked = false;
    }

    @Override
    public void move(Direction d) {
        tile.leave(this);
    }

    @Override
    public boolean canEnter(Tile t, Direction d) {
        System.out.println("Can a box enter this tile?");
        if (tile.getNeighborInDirection(d) == null){
            return false;
        }
        return t.canBeEnteredBy(this, d);
    }

    @Override
    public void push(Direction d) {
        System.out.println("Box pushed");
        tile.leave(this);
        tile.getNeighborInDirection(d).enter(this, d);
        locked = checkLocked();
    }

    @Override
    public boolean canBeOverPoweredBy(Box b, Direction d) {
        return canEnter(tile.getNeighborInDirection(d), d);
    }

    @Override
    public boolean canBeOverPoweredBy(Worker w, Direction d) {
        return canEnter(tile.getNeighborInDirection(d), d);
    }

    @Override
    public void removeFromGame() {
        controller.removeBox(this);
    }

    /*
    akkor locked egy láda, ha bármelyik két szomszédos oldalról be van határolva. Ennnél sokkal egyszerűbb ellenőrizni,
    hogy legalább két ellentétes oldal szabad-e (a komplementer eset).

    Teztesetek:

        FAL                 CSEMPE
        +-+                 +-+
     FAL| | CSEMPE   CSEMPE | | FAL
        +-+                 +-+
         CSEMPE             FAL


        TRUE                FALSE

    */
    private boolean checkLocked() {
        boolean northIsOpen = canEnter(tile.getNeighborInDirection(Direction.UP), Direction.UP);
        boolean southIsOpen = canEnter(tile.getNeighborInDirection(Direction.DOWN), Direction.DOWN);
        boolean eastIsOpen = canEnter(tile.getNeighborInDirection(Direction.RIGHT), Direction.RIGHT);
        boolean westIsOpen = canEnter(tile.getNeighborInDirection(Direction.LEFT), Direction.LEFT);

        return ! ( (northIsOpen && southIsOpen) || (westIsOpen && eastIsOpen) );


    }

    public boolean isLocked() {
        return locked;
    }


    @Override
    public String toString() {
        return "B";
    }
}
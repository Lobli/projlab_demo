package projlab;

public class Wall extends Tile {
    @Override
    public void enter(Worker worker, Direction direction) {
        worker.kill();
    }

    @Override
    public boolean canBeEnteredBy(Box box, Direction goingIn) {
        System.out.println("A box cannot enter wall");
        return false;
    }

    @Override
    public boolean canBeEnteredBy(Worker worker, Direction goingIn) {
        System.out.println("A worker cannot enter wall");
        return false;
    }

    @Override
    public String toString() {
        return "W";
    }
}

package projlab;

public class Wall extends Tile {

    static Wall fromString(String[] args){
        return new Wall();
    }

    @Override
    public void enter(Worker worker, Direction direction) {
        worker.removeFromGame();
    }

    @Override
    public boolean canBeEnteredBy(Box box, Direction goingIn, double force) {
        return false;
    }

    @Override
    public boolean canBeEnteredBy(Worker worker, Direction goingIn, double force) {
        return false;
    }

    @Override
    public String toString() {
        return "W ";
    }
}

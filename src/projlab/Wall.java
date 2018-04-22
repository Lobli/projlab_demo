package projlab;

public class Wall extends Tile {
    
    /**
        A belépő munkást eltávolítja a játékból.
        Ez szimulálja a munkás összepréselését.
    */
    @Override
    public void enter(Worker worker, Direction direction) {
        worker.removeFromGame();
    }
    
    /**
    */
    @Override
    public boolean canBeEnteredBy(Box box, Direction goingIn, double force) {
        return false;
    }

    /**
    */
    @Override
    public boolean canBeEnteredBy(Worker worker, Direction goingIn, double force) {
        return false;
    }

    /**
        Visszatér a fal jelkódjával ("W").
    */
    @Override
    public String toString() {
        return "W ";
    }
}

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
        Megadja, hogy egy doboz beléphet-e erre a mezőre
        @param box      a belépó doboz
        @param goingIn  a mozgás iránya
        @param force    a nyomás erőssége
        @return         false
    */
    @Override
    public boolean canBeEnteredBy(Box box, Direction goingIn, double force) {
        return false;
    }

    /**
        Megadja, hogy egy munkás beléphet-e erre a mezőre
        @param worker      a belépó munkás
        @param goingIn      a mozgás iránya
        @param force        a nyomás erőssége
        @return             false
    */
    @Override
    public boolean canBeEnteredBy(Worker worker, Direction goingIn, double force) {
        return false;
    }

    /**
        Visszatér a fal jelkódjával ("W").
        @return a jelkód
    */
    @Override
    public String toString() {
        return "W ";
    }
}

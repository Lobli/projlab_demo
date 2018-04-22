package projlab;

/**
*A lyuk viselkedését megvalósító Tile alosztály. 
*/
public class Hole extends Tile {
    //Megadja, hogy nyitva vagy csukva van-e a lyuk.
    private boolean closed = true;

    /**
    * Az osztály konstruktora
    */
    public Hole() { }

    /**
    * A closed setter-e.
    */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
    * A closed getter-e.
    * @return Igazat vagy hamisat ad vissza annak megfelelően, hogy nyitva van e a lyuk
    */
    public boolean isClosed() {
        return closed;
    }

    /**
    * Az alap Tile enter-ét kiegészítő függvény, ami a Hole állapotának függvényében (nyitott v. csukott) eldönti, 
    * hogy “megölilje”-e  a rálépő dobozt.
    */
    @Override
    public void enter(Box b, Direction d) {
        if (closed)
            super.enter(b, d);
        else
            b.removeFromGame();
    }

    /**
    * Az alap Tile enter-ét kiegészítő függvény, ami a Hole állapotának függvényében (nyitott v. csukott) eldönti, 
    * hogy “megölilje”-e  a rálépő munkást.
    * @param w  A belépni akaró munkás
    * @param d  Az irány ahonnan be akar lépni
    */
    @Override
    public void enter(Worker w, Direction d) {
        if(closed)
            super.enter(w,d);
        else
            w.removeFromGame();

    }

    /**
    * A Hole jelkódjával tér vissza, ami a függ az állapottól (nyitott v. csukott).
    * @return A Hole jelkódja
    */
    @Override
    public String toString() {
        if (closed){
            if (occupiedBy == null){
                return "T ";}
            else
                return occupiedBy.toString();}
        else return "H ";
    }
}

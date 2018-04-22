package projlab;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import projlab.SyntaxError;

import java.util.ArrayList;

/**
* A Worker és a Box ősosztálya, meghatározza a közös viselkedésüket absztrakt függvényeken keresztül, 
* és a közös field-ek accessor-ait implementálja is.
*/
public abstract class GameObject {
    // Az a Tile, amin az adott GameObject éppen áll.
    protected Tile tile;
    //  Az adott GameObject-et irányító GameEngine.
    protected GameEngine controller = null;

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    */
    public abstract void move(Direction d);

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    */
    public abstract void push(Direction d);

    /**
    * A szöveges leírásból beolvasó függvény definíciója, a leszármazott osztályok a saját 
    * viselkedésüket implementálják.
    * @param tokens         A térképfile tokenek-re szétszedve
    * @param t              Az a csempe, amelyiken a doboz a játék kezdetekor tartozkodik
    * @throws SyntaxError   Ha nem a térképleíró nyelv szabályainak megfelelő struktúrát
    *                       talál
    */
    public static GameObject fromString(ArrayList<String> tokens, Tile t) throws SyntaxError, NotImplementedException {
        throw new NotImplementedException();
    };

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    * @return Igazat vagy hamisat ad vissza, attól függően, hogy egy munkás el tudja-e tolni
    */
    public abstract boolean canBeOverPoweredBy(Worker w, Direction d, double force);

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    * @return Igazat vagy hamisat ad vissza, attól függően, hogy egy doboz el tudja-e tolni
    */
    public abstract boolean canBeOverPoweredBy(Box b, Direction d, double force);

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    * @return Igazat vagy hamisat ad vissza, attól függően, hogy a Gameobject az adott mezőre léphet-e
    */
    public abstract boolean canEnter(Tile t, Direction d, double force);

    /**
    * A controller setter-e.
    */
    public void setController(GameEngine g) {
        controller = g;
    }

    /**
    * A tile setter-e.
    */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
    * Absztrakt függvény, ahol a GameObject leszármazott osztálya a saját viselkedését implementálhatja.
    */
    public abstract void removeFromGame();
}

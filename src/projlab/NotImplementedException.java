package projlab;

/**
    Exception, ami akkor dobódik, ha a program egy adott objektumban szereplő, de nem értlmezett
    függvényt akar hívni. Ez az öröklés miatt fodulhat elő (pl Box::move).
*/
public class NotImplementedException extends Exception {
    /**
        Konstruktor
    */
    public NotImplementedException() {
        super();
    }
    
    /**
        Konstruktor
        @param message  a hibaüzenet
    */
    public NotImplementedException(String message) {
        super(message);
    }
}

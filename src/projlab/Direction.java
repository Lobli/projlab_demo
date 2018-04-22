package projlab;

/**
* Az irányokat meghatározó enum. A enum tagokon kívüli változók és függvények azért vannak, 
* hogy a különböző irányoknak String reprezentációja is legyen.
*/
public enum Direction {
    //Felfele irány ( a képernyőt nézve).
    UP("UP"),
    //Jobbra irány
    RIGHT("RIGHT"),
    //Lefele irány
    DOWN("DOWN"),
    //Balra irány
    LEFT("LEFT");

    /**
    * Az irányokat meghatározó enum. A enum tagokon kívüli változók és függvények azért vannak, 
    */
    private final String textRepresentation;

    /**
    * Az osztály konstruktora
    */
    Direction(String textRepresentation){
        this.textRepresentation = textRepresentation;
    }
    
    /**
    * Visszatér az adott enum tag szöveges reprezentációjával.
    * @return az enum szöveges reprezentációja
    */
    @Override
    public String toString() {
        return textRepresentation;
    }
}

package projlab;

/**
* A méz együtthatójával működő osztály.
*/
public class Honey extends Material {
    //Az osztály konstruktora
    public Honey(){
        coefficient = 1.5;
    }

    /**
    * Visszatér az anyag jelkódjával.
    * @return A jelkód
    */
    @Override
    public String toString() {
        return "h ";
    }
}

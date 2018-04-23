package projlab;

public class Oil extends Material {
    /**
        Konstruktor, beállítja az anyag együtthatóját
    */
    public Oil(){
        coefficient = 1.5;
    }
    
    /**
        Visszatér az anyag jelkódjával.
        
        @return     A jelkód
    */
    @Override
    public String toString() {
        return "o ";
    }
}

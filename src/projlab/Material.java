package projlab;

import projlab.SyntaxError;

public abstract class Material {
    /**
        Az anyag tapadási súrlódási együtthatója
    */
    double coefficient;

    /**
        @return Az anyag tapadási súrlódási együtthatója
    */
    public double getCoefficient() {
        return coefficient;
    }

    /**
        Beolvassa az anyagot a térképfájlból
        
        @throws SyntaxError ha nem értekmezett inputot kap
    */
    static Material fromString(String type) throws SyntaxError {
        if (type.equals("Oil"))
            return new Oil();
        if (type.equals("Honey"))
            return new Honey();
        throw new SyntaxError();
    }
}

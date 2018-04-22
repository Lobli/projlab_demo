package projlab;

import projlab.SyntaxError;

public abstract class Material {
    double coefficient;

    public double getCoefficient() {
        return coefficient;
    }

    static Material fromString(String type) throws SyntaxError {
        if (type.equals("Oil"))
            return new Oil();
        if (type.equals("Honey"))
            return new Honey();
        throw new SyntaxError();
    }
}

package projlab;

/**
    Saját SyntaxError osztály, ami a térképleírónyelven megírt térkép beolvasásakor az
    esetleges hibák jelzésére használatos
*/
public class SyntaxError extends Exception {
    /**
        Konstruktor
    */
    public SyntaxError() {
        super();
    }

    /**
        Konstruktor
        @param message  a hibaüzenet
    */
    public SyntaxError(String message) {
        super(message);
    }
}

package projlab;

public enum Direction {
    UP("UP"),
    RIGHT("RIGHT"),
    DOWN("DOWN"),
    LEFT("LEFT");

    private final String textRepresentation;

    Direction(String textRepresentation){
        this.textRepresentation = textRepresentation;
    }

    @Override
    public String toString() {
        return textRepresentation;
    }
}
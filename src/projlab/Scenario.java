package projlab;

public class Scenario {
    protected String name;
    private String input;
    Map map;

    public Scenario(String name, String input, String[] map) {
        this.name = name;
        this.input = input;
        this.map = new Map(map);
    }

    public void run(){
            map.gameEngine().startGame(input);
    }
}

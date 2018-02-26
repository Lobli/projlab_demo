package projlab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GameEngine {
    private List<Worker> workers;
    private Timer timer;
    private List<Tile> tiles;

    public GameEngine(int gameTimeSeconds, List<Tile> tiles, List<Worker> workers) {
        this.workers = workers;
        this.timer = new Timer(gameTimeSeconds);
        this.tiles = tiles;
    }

    private void startGame(){
        for(Worker w: workers){
            w.setController(this);
        }
        playGame();
    }

    private void pauseGame(){
        timer.togglePaused();
    }

    private void playGame(){
        System.out.println("Game running!");
        while(timer.getTime() != 0) {
            Direction chosen = Direction.DOWN;
            char c = 'W';
            if (c == 'W') {
                chosen = Direction.UP;
            } else if (c == 'D') {
                chosen = Direction.RIGHT;
            } else if (c == 'S') {
                chosen = Direction.DOWN;
            } else if (c == 'A') {
                chosen = Direction.LEFT;
            }
            workers.get(0).move(chosen);
            checkAllBoxesLocked();
            timer.tick();
        }
        endGame();
    }

    private void printMap(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(tiles.get((i*5 + j)));
            }
            System.out.println();
        }
    }

    private void endGame(){
        System.out.println("Game over!");
        System.out.print("Winner: Player ");
        System.out.print(getWinner());
        System.out.println();
    }

    private boolean checkAllBoxesLocked(){
        boolean allLocked = false;
        for(Tile tile: tiles){
            allLocked = allLocked | tile.isLocked();
        }
        if (allLocked)
            endGame();
    }

    private int getWinner(){
        int max = 0;
        for(Worker worker: workers){
            if(worker.getPoints() > max){
                max = worker.getPoints();
            }
        }
        return max;
    }

    public void killWorker(Worker worker){
        System.out.print("Worker dead!");
        worker.setPoints(0);
        endGame();
    }

    public static String[] readLines(String filename) throws IOException {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
    }

    public static GameEngine loadGame(String path) {
        //String [] lines = readLines(path);
        String[] lines = {
                "WWWWW",
                "WTTTW",
                "WTTBW",
                "WTTPW",
                "WTTTW",
                "WWWWW"
        };

        ArrayList<Tile> tiles  = new ArrayList<Tile>();
        ArrayList<Worker> workers= new ArrayList<Worker>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (lines[i].charAt(j) == 'W') {
                    tiles.add(new Wall());
                } else if (lines[i].charAt(j) == 'B') {
                    Tile t = new Tile();
                    Box b = new Box();
                    b.tile = t;
                    t.setOccupiedBy(b);
                    tiles.add(t);
                } else if (lines[i].charAt(j) == 'P') {
                    Tile t = new Tile();
                    Worker w = new Worker();
                    t.setOccupiedBy(w);
                    w.tile = t;
                    workers.add(w);
                    tiles.add(t);
                }
                else {
                    tiles.add(new Tile());
                }
            }
        }

        int mapLength = 5;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0) { // első sor
                    if (j == 0) { // első oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, null);
                        n.put(Direction.RIGHT, tiles.get(mapLength * i + j + 1));
                        n.put(Direction.DOWN, tiles.get(mapLength * (i + 1) + j));
                        n.put(Direction.LEFT, null);
                        tiles.get(mapLength * i + j).setNeighbors(n);
                    } else if (j == mapLength - 1) { //utolsó oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, null);
                        n.put(Direction.RIGHT, null);
                        n.put(Direction.DOWN, tiles.get(mapLength * (i + 1) + j));
                        n.put(Direction.LEFT, tiles.get(mapLength * i + j - 1));
                        tiles.get(mapLength * i + j).setNeighbors(n);
                    } else { // középső oszlopok
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, null);
                        n.put(Direction.RIGHT, tiles.get(mapLength*(i) + j+1));
                        n.put(Direction.DOWN, tiles.get(mapLength*(i+1) + j));
                        n.put(Direction.LEFT, tiles.get(mapLength*i + j-1));
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                }

                else if (i == 5){ // utolsó sor
                    if (j == 0) { // első oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, tiles.get(mapLength*(i-1) + j));
                        n.put(Direction.RIGHT, tiles.get(mapLength*i + j+1));
                        n.put(Direction.DOWN, null);
                        n.put(Direction.LEFT, null);
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                    else if (j == mapLength - 1) { // utolsó oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, tiles.get(mapLength*(i-1) + j));
                        n.put(Direction.RIGHT, null);
                        n.put(Direction.DOWN, null);
                        n.put(Direction.LEFT, tiles.get(mapLength*i + j-1));
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                    else { // középső oszlopok
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP,  tiles.get(mapLength*(i-1) + j));
                        n.put(Direction.RIGHT, tiles.get(mapLength*(i) + j+1));
                        n.put(Direction.DOWN, null);
                        n.put(Direction.LEFT, tiles.get(mapLength*i + j-1));
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                }

                else { // középső sorok
                    if (j == 0){ // első oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP,  tiles.get(mapLength*(i-1) + j));
                        n.put(Direction.RIGHT, tiles.get(mapLength*(i) + j+1));
                        n.put(Direction.DOWN, tiles.get(mapLength*(i+1) + j));
                        n.put(Direction.LEFT, null);
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                    else if (j == mapLength - 1){ // utolsó oszlop
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP,  tiles.get(mapLength*(i-1) + j));
                        n.put(Direction.RIGHT, null);
                        n.put(Direction.DOWN, tiles.get(mapLength*(i+1) + j));
                        n.put(Direction.LEFT, tiles.get(mapLength*i + j-1));
                        tiles.get(mapLength*i+j).setNeighbors(n);
                    }
                    else {
                        HashMap<Direction, Tile> n = new HashMap<Direction, Tile>();
                        n.put(Direction.UP, tiles.get(mapLength * (i - 1) + j));
                        n.put(Direction.RIGHT, tiles.get(mapLength * (i) + j + 1));
                        n.put(Direction.DOWN, tiles.get(mapLength * (i + 1) + j));
                        n.put(Direction.LEFT, tiles.get(mapLength * i + j - 1));
                        tiles.get(mapLength * i + j).setNeighbors(n);
                    }
                }
            }
        }

        return new GameEngine(3, tiles, workers);
    }

    public static void main(String[] args){
        GameEngine gameEngine = loadGame("/Users/its_behind_you/IdeaProjects/untitled1/out/production/untitled1/com/company/map.txt");
        gameEngine.startGame();

    }

}

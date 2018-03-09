package projlab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class GameEngine {
    private List<Worker> workers;
    private List<Tile> tiles;
    private List<Box> boxes;
    private Timer timer;
    private int gameTime;

    public GameEngine(int gameTimeSeconds, List<Tile> tiles, List<Worker> workers, List<Box> boxes) {
        this.workers = workers;
        this.tiles = tiles;
        this.boxes = boxes;
        this.timer = null;
        gameTime = gameTimeSeconds;
    }

    public void startGame(String input){
        timer = new Timer(gameTime);

        for(Worker w: workers){
            w.setController(this);
        }

        for (Box b : boxes){
            b.setController(this);
        }

        playGame(input);
    }

    private void pauseGame(){
        timer.togglePaused();
    }

    private void playGame(String input) {
        System.out.println();
        if (input == "P") {
            pauseGame();
        }
        else if (input == "W" || input == "A" || input == "S" || input == "D") {
            Direction chosen = Direction.UP;
            if (input == "W") {
                chosen = Direction.UP;
            } else if (input == "D") {
                chosen = Direction.RIGHT;
            } else if (input == "S") {
                chosen = Direction.DOWN;
            } else if (input == "A") {
                chosen = Direction.LEFT;
            }
            workers.get(0).move(chosen);
        }
    }

    private void endGame(){
        timer.stop();
        Worker winner = getWinner();
        System.out.println("Game over!");
        System.out.print("Winner: ");
        System.out.print(winner.getName());
        System.out.println();
    }

    private boolean allBoxesLocked(){
        boolean allLocked = false;
        for(Box box : boxes) {
            allLocked = allLocked | box.isLocked();
        }
        return allLocked;
    }

    private Worker getWinner(){
        Worker winner = workers.get(0);
        for(Worker worker: workers){
            if(worker.getPoints() > winner.getPoints()){
                winner = worker;
            }
        }
        return winner;
    }

    public void removeWorker(Worker worker){
        workers.remove(worker);
    }

    public void removeBox(Box box) {
        boxes.remove(box);
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getGameTime() {
        return gameTime;
    }

    public static void main(String[] args){
        String[] simpleMap = { "t1+W1|t2+B2|t3" };
        String[] complexMap = {
                "t1|s1+h1|w1",
                "t2|h1|w2",
                "t3+W1|t4|t5"
        };

        SkeletonHelper.addScenario(new Scenario("simple", "D", simpleMap));
        SkeletonHelper.addScenario(new Scenario("complex", "W", complexMap));
        SkeletonHelper.chooseScenario();
    }
}

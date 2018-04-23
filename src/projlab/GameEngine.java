package projlab;

import projlab.SyntaxError;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
*  A játékhoz tartozó adatokat kezeli és a játékot vezeti le.
*/
public class GameEngine {
    //A raktár térképe, amin a játékosok éppen játszanak.
    Map map;
    //A játékidőt mérő Timer példány
    private Timer timer = null;

    /**
    * Az osztály konstruktora
    * @throws SyntaxError   Ha probléma lenne a szintakszissal
    * @throws IOException   Ha az input nem megfelelő
    */
    public GameEngine() throws SyntaxError, IOException {
        timer = setTimer();
        map = new Map(new File("maps/Tests.bkml").getAbsolutePath());
        startGame();
    }

    /**
    * Beállítja a timert
    * @return Visszaadja a timert
    */
    private Timer setTimer(){
        Scanner s = new Scanner(System.in);

        System.out.print("TIMER OFF / ON> ");
        String response = s.nextLine();
        if (response.equals("off") || response.equals("OFF") ||  response.equals(""))
            return new Timer(10000, true);

        if (response.equals("on") || response.equals("ON")){
            System.out.print("Number of rounds?> ");
            response = s.nextLine();
            while (!response.matches("\\d+")) {
                System.out.print("ENTER A NUMBER> ");
                response = s.nextLine();
            }
            return new Timer(Integer.parseInt(response), false);
        }
        else {
            return this.setTimer();
        }

    }

    /**
    * Kiválasztja az adott pályát
    * @throws SyntaxError   Ha probléma lenne a szintakszissal
    * @throws IOException   Ha az input nem megfelelő
    * @return               Visszaadja a kiválasztott pályát
    */
    private Map chooseMap() throws SyntaxError, IOException {
        File[] maps = new File("maps/").listFiles();

        for (int i = 0; i < maps.length; i++){
            String filename = maps[i].getName().split(Pattern.quote("."))[0];
            //System.out.println(String.format("[%d] %s", i, filename));           //csak debuggoláshoz kell
        }
        System.out.print("CHOOSE MAP> ");

        String chosen = new Scanner(System.in).nextLine();
        int chosenNum = Integer.parseInt(chosen);

        return new Map(maps[chosenNum].getAbsolutePath());
    }

    /**
    * Beállítja önmagát controllernek a térképen elhelyezkedő dobozoknak és munkásoknak, 
    * majd elindítja a játékot.
    */
    private void startGame(){

        for(Worker w: map.workers){
            w.setController(this);
        }

        for (Box b : map.boxes){
            b.setController(this);
        }

        playGame();
    }

    /**
    * Átmenetileg szünetelteti a játékot.
    */
    public void pauseGame(){
        timer.togglePaused();
    }

    /**
    * A játék “main loop”-ját vezeti le: ha még van idő a játékból és nincs szünetelve a játék, 
    * mindegyik Workerre bekéri a neki szánt inputot majd kiadja a Workereknek a kapott parancsot. 
    * Miután mindegyik akció lefutott, ellenőrzni, hogy valamelyik játék végét okozó feltétel 
    * fennáll-e (nincs már doboz a pályán, meghalt egy Worker vagy mindegyik doboz olyan helyzetbe 
    * jutott, ahonnan már nem lehet eltolni). Ha igen, megszakítja a játékot, viszont ha még mehet 
    * tovább, akkor kinyomtatja a térképet és újrakezdi a kört.
    */
    private void playGame(){
        map.printMap();
        Scanner s  = new Scanner(System.in);
        String[] inputs = new String[map.workers.size()];
        while(timer.tick()) {

            if(timer.isPaused())
                continue;

            for (int i = map.workers.size()-1; 0 <= i; i--){
                System.out.print(map.workers.get(i).getName()+">");
                String input = s.nextLine();

                while (!Worker.CONTROLS.containsKey(input.toLowerCase())){
                    System.out.print("INPUT NOT DEFINED (PRESS ENTER OR G TO PASS)> ");
                    input = s.nextLine();
                }

                inputs[i] = input;
            }

            for (int i = 0; i < map.workers.size(); i++)
                map.workers.get(i).getInput(inputs[i]);

            if (allBoxesLocked() || map.boxes.size() < 1 || map.workers.size() < 2)
                break;

            map.printMap();
        }

        endGame();
    }

    /**
    * Leállítja a Timer-t és megállapítja, ki a győztes.
    */
    private void endGame(){
        timer.stop();
        Worker winner = getWinner();
         if (timer.steppable == true) {
            map.printMap();}
        if (winner == null){
            System.out.println("\nGAME OVER!");
            System.out.println("IT'S A TIE!");
        }
        else {
            System.out.println("\nGAME OVER!");
            System.out.print("WINNER: ");
            System.out.print(winner.getName());
            System.out.println();
        }
    }

    /**
    * Ellenőrzi, hogy mindegyik doboz olyan pozícióban van-e, ahonnan már nem lehet elmozgatni.
    * @return Igazat vagy hamisat ad vissza, annak megfelelően, hogy még van-e mozgatható doboz.
    */
    private boolean allBoxesLocked(){
        boolean temp = true;
        for(Box box : map.boxes) {
            if (box.isLocked() == false)
                temp = false;
        }
        return temp;
    }

    /**
    * Megállapítja, hogy a pontszámok alapján ki a nyertes.
    * @return Visszaadja az nyertest
    */
    private Worker getWinner(){
        if (map.workers.size() ==1)
            return map.workers.get(0);
        if (map.workers.get(0).getPoints() < map.workers.get(1).getPoints() ){
            return map.workers.get(1);
        }
        if (map.workers.get(1).getPoints() < map.workers.get(0).getPoints()){
            return map.workers.get(0);
        }
        else return null;
    }

    /**
    * Elveszi a megadott Worker-t a játékban résztvevő Worker-ek listájából.
    */
    public void removeWorker(Worker worker){
        map.workers.remove(worker);
    }

    /**
    * Elveszi a megadott Box-ot a játékban résztvevő Box-ok listájából.
    */
    public void removeBox(Box box) {
        map.boxes.remove(box);
    }

    /**
    * A programot indító Main függvény.
    */
    public static void main(String[] args) throws SyntaxError, IOException {
        GameEngine ge = new GameEngine();

    }
}


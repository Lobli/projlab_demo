package projlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/*
    w|t1+w1|t2|t3+b1|w
    w|t4|t5|t6|w
    w|t7|s1+h1|h1|w
 */

public class Map {
    private HashMap<String, Tile> switches = new HashMap<>();
    String[] map;
    int height;
    int width;

    ArrayList<Tile> tiles  = new ArrayList<>();
    ArrayList<Worker> workers= new ArrayList<>();
    ArrayList<Box> boxes = new ArrayList<>();

    public Map(String[] map) {
        this.map = map;
        height = map.length;
        width = map[0].split(Pattern.quote("|")).length;
    }

    private void parseCombo(String one, String two) /*throws Exception*/ {
        if(one.charAt(0) == 't'){               // valami tile-on áll
            Tile t = new Tile(one);
            tiles.add(t);
            if (two.charAt(0) == 'W'){          // Worker
                Worker w = new Worker(two);
                w.setTile(t);
                t.setOccupiedBy(w);
                workers.add(w);
            }
            else if (two.charAt(0) == 'B'){     //Box
                Box b = new Box(two);
                b.setTile(t);
                t.setOccupiedBy(b);
                boxes.add(b);
            }
            else {
                // throw new Exception("Invalid field token");
            }
        }
        else if (one.charAt(0) == 's'){         // switch (nem állhat rajta semmi)
            if (switches.containsKey(two)){     //már volt a switch által irányított hole
                Switch s = new Switch(one);
                Hole h = (Hole)switches.remove(two);
                s.setControlling(h);
                tiles.add(s);
            }
            else {                              //még nem volt a switch irányított hole
                Switch s = new Switch(one);
                Hole h = new Hole(two);
                s.setControlling(h);
                switches.put(two, h);
                tiles.add(s);
            }
        }
        else {
            // throw new Exception("Invalid field token");
        }
    }

    private void parseSingle(String field) /* throws Exception */ {
        if (field.charAt(0) == 't'){           //magában álló tile
            Tile t =  new Tile(field);
            tiles.add(t);
        }
        else if (field.charAt(0) == 'w'){      // wall
            Wall w = new Wall(field);
            tiles.add(w);
        }
        else if (field.charAt(0) == 'h'){       //hole
            if (switches.containsKey(field)){   //már volt a hole (egy switch-ben megadva)
                tiles.add(switches.remove(field));
            }
            else {                              //még nem volt a hole
                Hole h = new Hole(field);
                tiles.add(h);
                switches.put(field, h);
            }
        }
        else {
            // throw new Exception("Invalid field token");
        }
    }

    private void printMap(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(tiles.get((i*width + j)));
            }
            System.out.println();
        }
    }

    private void configureNeighborsMultiLine(){
        for (int i = 0; i <height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0) { // első sor
                    if (j == 0) { // első oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width * i + j + 1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width * (i + 1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, null);
                    } else if (j == width - 1) { //utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width * (i + 1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width * i + j - 1));
                    } else { // középső oszlopok
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*(i) + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width*(i+1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));
                    }
                }

                else if (i == height - 1){ // utolsó sor
                    if (j == 0) { // első oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*i + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*i + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, null);
                    }
                    else if (j == width - 1) { // utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));

                    }
                    else { // középső oszlopok
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*(i) + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));
                    }
                }

                else { // középső sorok
                    if (j == 0){ // első oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*(i) + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width*(i+1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, null);
                    }
                    else if (j == width - 1){ // utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, null);
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width*(i+1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));
                    }
                    else {
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width * (i) + j + 1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width*(i+1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));
                    }
                }
            }

        }
    }

    private void configureNeighborsSingleLine(){
        for (int i = 0; i < width; i++) {
            if (i == 0){
                tiles.get(i).setNeighborInDirection(Direction.UP, null);
                tiles.get(i).setNeighborInDirection(Direction.RIGHT, tiles.get(1));
                tiles.get(i).setNeighborInDirection(Direction.DOWN, null);
                tiles.get(i).setNeighborInDirection(Direction.LEFT, null);
            }
            else if (i == width - 1){
                tiles.get(i).setNeighborInDirection(Direction.UP, null);
                tiles.get(i).setNeighborInDirection(Direction.LEFT, tiles.get(width - 2));
                tiles.get(i).setNeighborInDirection(Direction.RIGHT, null);
                tiles.get(i).setNeighborInDirection(Direction.DOWN, null);
            }
            else {
                tiles.get(i).setNeighborInDirection(Direction.UP, null);
                tiles.get(i).setNeighborInDirection(Direction.LEFT, tiles.get(i - 1));
                tiles.get(i).setNeighborInDirection(Direction.RIGHT, tiles.get(i + 1));
                tiles.get(i).setNeighborInDirection(Direction.DOWN, null);
            }
            System.out.println();
        }
    }

    public GameEngine gameEngine() /* throws Exception */{
        for (String row : map){
            String[] fields = row.split(Pattern.quote("|"));
            for (String field : fields){
                if (field.contains("+")){
                    String[] objects = field.split(Pattern.quote("+"));
                    parseCombo(objects[0], objects[1]);
                }
                else {
                    parseSingle(field);
                }
            }
        }


        if (height == 1)
            configureNeighborsSingleLine();
        else
            configureNeighborsMultiLine();

        printMap();
        return new GameEngine(999, tiles, workers, boxes);
    }
}

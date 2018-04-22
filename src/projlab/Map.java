package projlab;

import projlab.SyntaxError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    String name;
    private int height;
    private int width;

    private ArrayList<Tile> tiles  = new ArrayList<>();
    ArrayList<Worker> workers= new ArrayList<>();
    ArrayList<Box> boxes = new ArrayList<>();

    private HashMap<String, Tile> switches = new HashMap<>();
    private HashMap<String, TargetTile> targetTiles = new HashMap<>();

    public Map(String path) throws SyntaxError, IOException {
        String map = new String(Files.readAllBytes(Paths.get(path)));
        read_from(tokenize(map));

        if (height == 1)
            configureNeighborsSingleLine();
        else
            configureNeighborsMultiLine();

    }

    private void read_from(ArrayList<String> tokens) throws SyntaxError {
        String token = tokens.remove(0);
        if (token.equals("[")) {
            parse_list(tokens);
        }
        else if (token.equals("{")) {
            parse_object(tokens);
        }
    }

    private void parse_map(ArrayList<String> tokens) throws SyntaxError {
        while( ! tokens.get(0).equals("}")){
            String next = tokens.remove(0);
            if (next.equals(":name")) {
                tokens.remove(0);
                this.name = parse_string(tokens);
            }
            else if (next.equals(":height")){
                this.height = parse_number(tokens);
            }
            else if (next.equals(":width")){
                this.width = parse_number(tokens);
            }
            else if (next.equals(":rows")){
                read_from(tokens);
            }
            else
                throw new SyntaxError();
        }
        tokens.remove(0);
    }

    private void parse_row(ArrayList<String> tokens) throws SyntaxError {
        while (! tokens.get(0).equals("}")) {
            if (tokens.get(0).equals(":cells")) {
                tokens.remove(0);
                read_from(tokens);
            }
            else
                throw new SyntaxError();
        }
        tokens.remove(0);
    }

    private void parse_hole(ArrayList<String> tokens) throws SyntaxError {
        Hole h = new Hole();
        while (!tokens.get(0).equals("}")) {
            String token = tokens.remove(0);
            if (token.equals(":id")) {
                tokens.remove(0);
                String id = parse_string(tokens);
                if (switches.containsKey(id)) {
                    Switch s = (Switch) switches.get(id);
                    s.setControlling(h);
                } else {
                    switches.put(id, h);
                }
            }
            else if (token.equals(":closed")){
                tokens.remove(0);
                String closed = parse_string(tokens);
                if (closed.equals("true")){
                    h.setClosed(true);
                }
                else if (closed.equals("false")){
                    h.setClosed(false);
                }
                else
                    throw new SyntaxError();
            }
            else {
                throw new SyntaxError();
            }
        }
        tiles.add(h);
    }

    private void parse_switch(ArrayList<String> tokens) throws SyntaxError {
        Switch s = new Switch();
        if (tokens.remove(0).equals(":controlling")) {
            tokens.remove(0);
            String id = parse_string(tokens);
            if (switches.containsKey(id)){
                Hole h = (Hole) switches.get(id);
                s.setControlling(h);
            }
            else {
                switches.put(id, s);
            }
        }
        else
            throw new SyntaxError();
        tiles.add(s);
    }

    public Worker parse_worker(ArrayList<String> tokens, Tile t) throws SyntaxError {
        Worker w = Worker.fromString(tokens, t);
        workers.add(w);
        if (targetTiles.containsKey(w.getName())){
            TargetTile tt = targetTiles.get(w.getName());
            tt.belongsTo = w;
        }
        return w;
    }

    private void parse_targettile(ArrayList<String> tokens) throws SyntaxError {
        if (tokens.remove(0).equals(":belongsTo")){
            TargetTile tt = new TargetTile();
            tokens.remove(0);
            String worker_id = parse_string(tokens);
            Worker w = getWorker(worker_id);
            if (w != null) {
                tt.belongsTo = w;
            }
            else {
                targetTiles.put(worker_id, tt);
            }
            tiles.add(tt);
        }
        else
            throw new SyntaxError();
    }

    public Box parse_box(ArrayList<String> tokens, Tile t) throws SyntaxError {
        Box b = Box.fromString(tokens, t);
        boxes.add(b);
        return b;
    }

    private void parse_object(ArrayList<String> tokens) throws SyntaxError {
        String type = tokens.remove(0);
        if (type.equals("Map")){
            parse_map(tokens);
        }
        else if (type.equals("Row")) {
           parse_row(tokens);
        }
        else if (type.equals("Tile")) {
            tiles.add(Tile.fromString(tokens, this));
        }
        else if (type.equals("Hole")) {
            parse_hole(tokens);
        }
        else if (type.equals("Switch")){
            parse_switch(tokens);
        }
        else if (type.equals("Wall")){
            tiles.add(new Wall());
        }
        else if (type.equals("TargetTile")){
            parse_targettile(tokens);
        }
        else
            throw new SyntaxError();
    }

    public static String parse_string(ArrayList<String> tokens){
        ArrayList<String> words = new ArrayList<>();
        while (! tokens.get(0).equals("'")) {
            words.add(tokens.remove(0));
        }
        tokens.remove(0);
        return String.join(" ", words);
    }

    public static int parse_number(ArrayList<String> tokens) throws SyntaxError {
        String str = tokens.remove(0);
        for (int i = 0; i < str.length(); i++)
            if (Character.isLetter(str.charAt(i)))
                throw new SyntaxError();
        return Integer.parseInt(str);
    }

    private void parse_list(ArrayList<String> tokens) throws SyntaxError {
        while (!tokens.get(0).equals("]")) {
            read_from(tokens);
        }
        tokens.remove(0);
    }

    private ArrayList<String> tokenize(String map){
        String sig_chars[] = { "'", "[", "]", "(", ")", "{", "}" };

        map = map.replace("\n", " ");
        map = map.replace("\t", " ");

        for (String s : sig_chars){
            map = map.replace(s, String.format(" %s ", s));
        }

        map = map.trim();

        ArrayList<String> ret = new ArrayList<>();

        for (String s : map.split("\\s+"))
            ret.add(s.trim());

        return ret;
    }

    private Worker getWorker(String name){
        for (Worker w : workers)
            if (w.getName().equals(name))
                return w;
        return null;
    }

    public void printMap(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(tiles.get((i*width + j)));
            }
            System.out.println();
        }
    }

    private void configureNeighborsMultiLine(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0) { // első sor
                    if (j == 0) { // első oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width * i + j + 1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width * (i + 1) + j));
                    } else if (j == width - 1) { //utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width * (i + 1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width * i + j - 1));
                    } else { // középső oszlopok
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
                    }
                    else if (j == width - 1) { // utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP, tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));

                    }
                    else { // középső oszlopok
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*(i) + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.LEFT, tiles.get(width*i + j-1));
                    }
                }

                else { // középső sorok
                    if (j == 0){ // első oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.RIGHT, tiles.get(width*(i) + j+1));
                        tiles.get(width * i + j).setNeighborInDirection(Direction.DOWN, tiles.get(width*(i+1) + j));
                    }
                    else if (j == width - 1){ // utolsó oszlop
                        tiles.get(width * i + j).setNeighborInDirection(Direction.UP,  tiles.get(width*(i-1) + j));
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
        if (width == 1 && height == 1)
            return;

        for (int i = 0; i < width; i++) {
            if (i == 0){
                tiles.get(i).setNeighborInDirection(Direction.RIGHT, tiles.get(1));
            }
            else if (i == width - 1){
                tiles.get(i).setNeighborInDirection(Direction.LEFT, tiles.get(width - 2));
            }
            else {
                tiles.get(i).setNeighborInDirection(Direction.LEFT, tiles.get(i - 1));
                tiles.get(i).setNeighborInDirection(Direction.RIGHT, tiles.get(i + 1));
            }
            System.out.println();
        }
    }

}
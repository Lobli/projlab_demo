package projlab;

import projlab.SyntaxError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    /**
        A térkép neve
    */
    String name;
    
    /**
        A térkép sorainak száma
    */
    private int height;
    
    /**
        A térkép oszlopainak száma
    */
    private int width;

    /**
        A térképen lévő mezők listája
    */
    private ArrayList<Tile> tiles  = new ArrayList<>();
    
    /**
        A térképen lévő munkások listája
    */
    ArrayList<Worker> workers= new ArrayList<>();
    
    /**
        A térképen lévő dobozok listája
    */
    ArrayList<Box> boxes = new ArrayList<>();

    /**
        A térképen lévő, még meg nem oldott switch-hole összerendeléskhez
        tartozó tároló
    */
    private HashMap<String, Tile> switches = new HashMap<>();
    
    /**
        A térképen lévő, még meg nem oldott worker-targettile összerendeléskhez
        tartozó tároló
    */
    private HashMap<String, TargetTile> targetTiles = new HashMap<>();

    /**
        Konstruktor
        @param path         a térképfile elérési útvonala
        @throws SyntaxError ha a térképfile nem a szintakitkai szabályoknak megfelelően van leírva
        @throws IOException ha a térképfile-t nem találja a program vagy a fájl memóriába olvasása közben hiba történt
    */
    public Map(String path) throws SyntaxError, IOException {
        String map = new String(Files.readAllBytes(Paths.get(path)));
        read_from(tokenize(map));

        if (height == 1)
            configureNeighborsSingleLine();
        else
            configureNeighborsMultiLine();

    }
    
    /**
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
    private void read_from(ArrayList<String> tokens) throws SyntaxError {
        String token = tokens.remove(0);
        if (token.equals("[")) {
            parse_list(tokens);
        }
        else if (token.equals("{")) {
            parse_object(tokens);
        }
    }
    
    /**
        Beolvas egy térkép objektumot szöveges reprezentációjából.
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
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
    
    /**
        Beolvas egy sort egy Row objektum szöveges reprezentációjából.
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
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

    /**
        Beolvas egy Hole-t a szöveges reprezentációból.
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
        @see Hole
    */
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
    
    /**
        Beolvas egy Switch-et a szöveges reprezentációból.
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
        @see Switch
    */
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

    /**
        Beolvas egy munkást a szöveges reprezentációból.
        @param tokens   a térképfájl tokenekre felbontva
        @param t        a mező, amin a feldogozandó Worker áll
        @return         egy worker példány
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
        @see Worker
    */
    public Worker parse_worker(ArrayList<String> tokens, Tile t) throws SyntaxError {
        Worker w = Worker.fromString(tokens, t);
        workers.add(w);
        if (targetTiles.containsKey(w.getName())){
            TargetTile tt = targetTiles.get(w.getName());
            tt.belongsTo = w;
        }
        return w;
    }

    /**
        Beolvas egy targettile-t a szöveges reprezentációból.
        
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
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

    /**
        Beolvas egy dobozt a szöveges reprezentációból.
        
        @param tokens   a térképfájl tokenekre felbontva
        @param t        a mező, amin a feldogozandó Box áll
        @return         egy Box példány
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva.
        @see Box
    */
    public Box parse_box(ArrayList<String> tokens, Tile t) throws SyntaxError {
        Box b = Box.fromString(tokens, t);
        boxes.add(b);
        return b;
    }

    /**
        Beolvas egy térképfile-objektumot.
        
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
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

    /**
        Beolvas egy térképfájl-szöveget.
        
        @param tokens   a térképfájl tokenekre felbontva
        
        @return a szöveg
    */
    public static String parse_string(ArrayList<String> tokens){
        ArrayList<String> words = new ArrayList<>();
        while (! tokens.get(0).equals("'")) {
            words.add(tokens.remove(0));
        }
        tokens.remove(0);
        return String.join(" ", words);
    }

    /**
        Beolvas egy térképfájl-számot.
        
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
        
        @return a beolvasott szám
    */
    public static int parse_number(ArrayList<String> tokens) throws SyntaxError {
        String str = tokens.remove(0);
        for (int i = 0; i < str.length(); i++)
            if (Character.isLetter(str.charAt(i)))
                throw new SyntaxError();
        return Integer.parseInt(str);
    }

    /**
        Beolvas egy térképfájl-listát.
        
        @param tokens   a térképfájl tokenekre felbontva
        @throws SyntaxError ha a feldolgozott szegmens nem a szintakitkai szabályoknak megfelelően van leírva
    */
    private void parse_list(ArrayList<String> tokens) throws SyntaxError {
        while (!tokens.get(0).equals("]")) {
            read_from(tokens);
        }
        tokens.remove(0);
    }

    /**
        Tokenekre bont egy térképfájlt
        
        @param map  a térképfájl tartalma String-ben
        @return     a tokenek listáját
    */
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
    
    /**
        A workerek listájából megkereses egy példányt a neve alapján
        
        @param name a Worker neve
        @return     null ha nem találta a keresett Workert, Worker példány ha igen        
        
        @see Worker
    */
    private Worker getWorker(String name){
        for (Worker w : workers)
            if (w.getName().equals(name))
                return w;
        return null;
    }

    /**
        Kinyomtatja a konzolra a térképet karaketeres reprezentációban
    */
    public void printMap(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(tiles.get((i*width + j)));
            }
            System.out.println();
        }
    }

    /**
        Egy többsoros térképen bekonfigurálja a mezők szomszédait
    */
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

    /**
        Egy egysoros térképen bekonfigurálja a mezők szomszédait
    */
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

package projlab;

import java.util.Scanner;

public class Timer {
    /**
        A jelenleg a játékból hátralévő időlépések száma.
    */
    private int time;
    
    /**
        Megadja, hogy a háttérben számoljon vissza a Timer vagy diszkrét időlépésenként
        menjen.
        
        Háttérben futóra inicializálva.
    */
    private boolean steppable = false;
    
    /**
        Megadja, hogy éppen szünetre van-e állítva az óra.
        Hamisra inicializálva.
    */
    private boolean paused = false;

    /**
        Konstruktor
       
        @param time         hány időlépésből álljon a játék
        @param steppable    léptethető legyen-e a timer
    */
    public Timer(int time, boolean steppable) {
        this.steppable = steppable;
        this.time = time;
    }
    
    /**
        Egy időlépést vezényel le. Figyelembe veszi, hogy van-e még hátra a játékból,
        vagy szünetelve van-e a játék.
    */
    public boolean tick() {
        Scanner s = new Scanner(System.in);
        if (paused) {
            System.out.print("Press [Q] to exit or [P] to continue playing> ");
            String in = s.nextLine();

            while(!"QqPp".contains(in)){
                System.out.print("Press [Q] to exit or [P] to continue playing> ");
                in = s.nextLine();
            }

            if ("Qq".contains(in))
                stop();

            else if ("Pp".contains(in))
                togglePaused();
        }
        if (!steppable) {
            if (time < 1)
                return false;
            else if (time-- < 0)
                return false;
        }
        return true;
    }
    
    /**
        Megadja, hogy szünetelve van-e a játék.
    */
    public boolean isPaused() {
        return paused;
    }
    
    /**
        Leállítja a játékot azzal, hogy 0-ra állítja a hátralévő időt.
    */
    public void stop(){
        time = 0;
    }
    
    /**
        Megváltoztatja a paused állapotát (true -> false vagy false -> true)
    */
    public void togglePaused() {
        paused = !paused;
    }
}

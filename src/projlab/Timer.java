package projlab;

import java.util.Scanner;

public class Timer {
    private int time;
    private boolean steppable = false;
    private boolean paused = false;

    public Timer(int time, boolean steppable) {
        this.steppable = steppable;
        this.time = time;
    }

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

    public boolean isPaused() {
        return paused;
    }

    public void stop(){
        time = 0;
    }

    public void togglePaused() {
        paused = !paused;
    }
}

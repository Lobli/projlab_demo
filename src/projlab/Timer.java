package projlab;

public class Timer {
    private int time;
    private boolean paused;

    public Timer(int time) {
        paused = false;
        this.time = time;
    }

    public void tick(){
        if (!paused) {
            reduceTime();
        }
    }

    public void togglePaused(){
        paused = !paused;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void reduceTime() {
        time -= 1;
    }
}

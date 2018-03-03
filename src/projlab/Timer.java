package projlab;

public class Timer {
    private int time;
    private boolean paused;

    public Timer(int time) {
        paused = false;
        this.time = time;
    }

    public boolean tick() {
        if (!paused) {
            return (time -= 1) > 0;
        }
        return true;
    }

    public void stop(){
        setTime(0);
    }

    public void togglePaused() {
        setPaused(!paused);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

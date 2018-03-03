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
            time -= 1;
                if (time > 0){
                    return true;
                }
                return false;
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

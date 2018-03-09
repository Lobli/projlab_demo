package projlab;

public class Timer {
    private int time;
    private boolean paused;

    public Timer(int time) {
        paused = false;
        this.time = time;
    }

    public boolean tick() {
        SkeletonHelper.call("timer", "", "tick");
        if (!SkeletonHelper.decide("Szüntelve van a játék?")) {
            SkeletonHelper.returnF();
            return SkeletonHelper.decide("Lejárt már az idő?");
        }
        SkeletonHelper.returnF();
        return true;
    }

    public void stop(){
        SkeletonHelper.call("timer", "", "stop");
        SkeletonHelper.returnF();
        time = 0;
    }

    public void togglePaused() {
        SkeletonHelper.call("timer", "", "togglePaused");
        SkeletonHelper.returnF();
        paused = !paused;
    }
}

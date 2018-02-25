package projlab;

public class Worker extends GameObject {
    private int points;
    private GameEngine controller;

    public Worker(GameEngine controller){
        points = 0;
        this.controller = controller;
    }

     @Override
    public void move(Direction d) {
         if (tile.getNeigborInDirection(d).canEnter(this, d)) {
             tile.getNeigborInDirection(d).enter(this, d);
         }
     }

    @Override
    public boolean push(Direction d) {
        if (tile.getNeigborInDirection(d).canEnter(this, d)) {
            tile.getNeigborInDirection(d).enter(this, d);
            return true;
        }
        else {
            kill();
            return false;
        }
    }

    public void kill(){
        controller.kill(this);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

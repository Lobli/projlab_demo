package projlab;

public class TargetTile extends Tile {
    /**
        Megadja, melyik játékos kap pontot, ha doboz tolódik erre a mezőre.
    */
    Worker belongsTo;

    
    /**
        Implementálja a TargetTile-ra vontakozó viselkdést.
        @param b    a belépő doboz
        @param d    a doboz mozgási iránya
    */
    @Override
    public void enter(Box b, Direction d) {
        belongsTo.setPoints(belongsTo.getPoints() + 500);
        b.setTile(null);
        b.removeFromGame();
    }

    @Override
    public String toString() {
        return occupiedBy != null ? occupiedBy.toString() : "t ";
    }
}

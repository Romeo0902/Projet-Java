import java.util.ArrayList;

public class GameElement {
    public static Coordinate coord;

    // evite de creer un objet coordinate a chaque fois
    public void setCoord(int x, int y) {
        GameElement.coord.setX(x);
        GameElement.coord.setY(y);
    }

    public void setCoord(Coordinate coord) {
        GameElement.coord = coord;
    }

    public Coordinate getCoord() {
        return GameElement.coord;
    }

    public boolean isAccessible(int northBaseBot, int southBaseTop, int basesLeft, int basesRight,
            int ROWS, int COLUMNS, ArrayList<GameElement> listOfGameElements) {

        if ((Coordinate.getAccessibleAdjacentCoordinates(this.getCoord(), northBaseBot, southBaseTop, basesLeft,
                basesRight, ROWS, COLUMNS, listOfGameElements)) != null) {
            return true;
        }
        return false;
    }

}

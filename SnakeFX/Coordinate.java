import java.util.ArrayList;

public class Coordinate {

    public int x = 0;
    public int y = 0;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDistanceWith(Coordinate coord) {
        return Math.sqrt((coord.y - this.y) * (coord.y - this.y) + (coord.x - this.x) * (coord.x - this.x));
    }



    public static boolean isABase(Coordinate coord, int northBaseBot, int southBaseTop, int basesLeft, int basesRight) {
        if (coord.y < northBaseBot && coord.x > basesLeft && coord.x < basesRight
                || coord.y > southBaseTop && coord.x > basesLeft && coord.x < basesRight) {
            return true;
        }
        return false;
    }

    public static ArrayList<Coordinate> getAdjacentCoordinates(Coordinate coord) {

        ArrayList<Coordinate> resultList = new ArrayList<Coordinate>();

        for (int i = coord.x - 1; i <= coord.x + 1; i++) {
            for (int j = coord.y - 1; j <= coord.y + 1; j++) {
                if (!(i == coord.x && j == coord.y)) {
                    resultList.add(new Coordinate(i, j));
                }
            }
        }
        return resultList;
    }
    // case dans le board
    public static boolean isCoordinateInBoard(Coordinate coord, int COLUMNS, int ROWS, int northBaseBot,
            int southBaseTop, int basesLeft, int basesRight) {
        return (coord.x >= 0 && coord.y >= 0 && coord.x < COLUMNS && coord.y < ROWS
                && !isABase(coord, northBaseBot, southBaseTop, basesLeft, basesRight));
    }

    // il y a rien sur la case
    public static boolean isCoordinateFree(Coordinate coord, ArrayList<GameElement> listOfGameElements, int northBaseBot, int southBaseTop, 
    int basesLeft, int basesRight){
        for (GameElement gameElement : listOfGameElements) {
            if (gameElement.getCoord()==coord) {
                return false;
            }
        }
        if (Coordinate.isABase(coord, northBaseBot, southBaseTop, basesLeft, basesRight)) {
            return false;
        }

        return true;
    }
    // cases adjacentes libre
    public static ArrayList<Coordinate> getAccessibleAdjacentCoordinates(Coordinate coord, int northBaseBot,
            int southBaseTop, int basesLeft, int basesRight, int ROWS, int COLUMNS, ArrayList<GameElement> listOfGameElements) {

        ArrayList<Coordinate> resultList = new ArrayList<Coordinate>();

        ArrayList<Coordinate> adjacentCoordinatesList = getAdjacentCoordinates(coord);
        for (Coordinate adjacent : adjacentCoordinatesList) {
            if (isCoordinateInBoard(adjacent, COLUMNS, ROWS, northBaseBot, southBaseTop, basesLeft, basesRight)
                    && !Coordinate.isABase(adjacent, northBaseBot, southBaseTop, basesLeft, basesRight) && isCoordinateFree(adjacent, listOfGameElements, northBaseBot, southBaseTop, basesLeft, basesRight)) {
                resultList.add(adjacent);
            }
        }
        return resultList;
    }
    
    public Coordinate getNextCoordinateForTarget(Coordinate currentCoordinate, Coordinate targetCoordinate,
            int northBaseBot, int southBaseTop, int basesLeft, int basesRight, int ROWS, int COLUMNS, ArrayList<GameElement> listOfGameElements) {

        double minDistance = ROWS + COLUMNS;
        int minIndex = -1;
        ArrayList<Coordinate> accessibleAdjacentCoordinatesList = getAccessibleAdjacentCoordinates(currentCoordinate,
                northBaseBot, southBaseTop, basesLeft, basesRight, ROWS, COLUMNS,listOfGameElements);

        for (int index = 0; index < accessibleAdjacentCoordinatesList.size(); index++) {
            Coordinate coord = accessibleAdjacentCoordinatesList.get(index);
            double distance = coord.getDistanceWith(targetCoordinate);
            if (distance < minDistance) {
                minDistance = distance;
                minIndex = index;
            }
        }
        return accessibleAdjacentCoordinatesList.get(minIndex);
    }

}

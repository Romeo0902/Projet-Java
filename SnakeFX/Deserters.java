import java.util.ArrayList;

public class Deserters extends Units {
    private final static int damage = 10;
    private final static int maxHp = 125;
    private static double bonusAgainstPikemen = 1.5;
    private static double bonusAgainstDeserters = 1.5;

    public Deserters(Coordinate coord, double bonusAgainstPikemen, double bonusAgainstDeserters, int damage, int hp,
            String side) {
        super(hp, damage, coord, side);
        Deserters.bonusAgainstPikemen = bonusAgainstPikemen;
        Deserters.bonusAgainstDeserters = bonusAgainstDeserters;
    }

    public static int getDamage() {
        return damage;
    }

    public static int getMaxHp() {
        return maxHp;
    }

    public static double getBonusAgainstPikemen() {
        return bonusAgainstPikemen;
    }

    public static double getBonusAgainstDeserters() {
        return bonusAgainstDeserters;
    }

    public void moveToTarget(int ROWS, int COLUMNS, ArrayList<Units> listOfUnits,
            ArrayList<Units> listOfUnitsNorth, ArrayList<Units> listOfUnitsSouth, int northBaseBot,
            int southBaseTop) {

        String north = "north";
        String south = "south";
        Coordinate deserterCoord = new Coordinate(this.getCoord().getX(), this.getCoord().getY());
        Units closestUnit = null;
        double minimalDistance = ROWS + COLUMNS;

        if (this.getSide() == north) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsSouth.contains(unit)) {
                    if (unit instanceof Collectors || unit instanceof Deserters || unit instanceof Pikemen) {
                        double distance = deserterCoord.getDistanceWith(unit.getCoord());
                        if (distance < minimalDistance) {
                            minimalDistance = distance;
                            closestUnit = unit;
                        }
                    }
                }
            }
        }
        if (this.getSide() == south) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsNorth.contains(unit)) {
                    if (unit instanceof Collectors || unit instanceof Deserters || unit instanceof Pikemen) {
                        double distance = deserterCoord.getDistanceWith(unit.getCoord());
                        if (distance < minimalDistance) {
                            minimalDistance = distance;
                            closestUnit = unit;
                        }
                    }
                }
            }
        }

        Coordinate nextPosition = Controller.getNextCoordinateForTarget(deserterCoord, closestUnit.getCoord());

        deserterCoord = nextPosition;

        if (Controller.isInRange(deserterCoord, closestUnit)) {
            attack(deserterCoord, closestUnit);
        }

    }

    public void attack(Coordinate deserterCoord, Units closestUnit) {

    }

    public void defineTarget(int ROWS, int COLUMNS, ArrayList<Collectors> listOfCollectors,
            ArrayList<Units> listOfUnits, int northBaseBot,
            int southBaseTop, int basesLeft, int basesRight,
            ArrayList<GameElement> listOfGameElements, Bases baseNorth, Bases baseSouth) {
        // trouver le collector ennemi le plus proche du deserter
        Coordinate deserterCoord = new Coordinate(this.getCoord().getX(), this.getCoord().getY());
        Collectors closestCollector = null;
        double minimalDistance = ROWS + COLUMNS;
        double closestDistanceCollector = ROWS + COLUMNS;

        for (Collectors collector : listOfCollectors) {
            if (collector.getSide() != this.getSide()) {

                double distance = deserterCoord.getDistanceWith(collector.getCoord());
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    if (collector.isAccessible(northBaseBot, southBaseTop, basesLeft, basesRight, ROWS, COLUMNS,
                            listOfGameElements)) {
                        closestCollector = collector;
                        closestDistanceCollector = minimalDistance;
                    }

                }
            }

            Units closestUnit = null;
            double minimalDistanceUnit = ROWS + COLUMNS;
            double closestDistanceUnit = ROWS + COLUMNS;
            for (Units unit : listOfUnits) {
                if (unit.getSide() != this.getSide() && !(unit instanceof Collectors)) {

                    double distanceUnit = deserterCoord.getDistanceWith(unit.getCoord());
                    if (distanceUnit < minimalDistanceUnit) {
                        closestUnit = unit;
                        closestDistanceUnit = minimalDistanceUnit;
                    }
                }
            }

            if (closestDistanceUnit < closestDistanceCollector) {
                runAway(closestUnit);
            } else {
                moveToTarget(closestCollector, ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth, listOfGameElements, null);
            }
        }
    }

    @Override
    public void moveToTarget(GameElement gameElement, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth,
    Bases baseSouth, ArrayList<GameElement> listOfGameElements, ArrayList<Tree>listOfTrees) {

        // trouver l'ennemi le plus proche du collector
        Coordinate nextPosition = Controller.getNextCoordinateForTarget(this.getCoord(), gameElement.getCoord());

        this.setCoord(nextPosition);
        if (Controller.isInRange(this.getCoord(), gameElement)) {
            /*
             * this.(gameElement, ROWS, northBaseBot, southBaseTop, baseNorth,
             * baseSouth);
             */
        }

    }

    @Override
    public void die(ArrayList<GameElement> listOfGameElements,ArrayList<Units> listOfUnits, ArrayList<Collectors> listOfCollectors,
            ArrayList<Deserters> listOfDeserters, ArrayList<Horsemen> listOfHorsemen,
            ArrayList<Pikemen> listOfPikemen) {

        this.setCoord(null);
        listOfDeserters.remove(this);
        listOfUnits.remove(this);
        listOfGameElements.remove(this);
    }

    // va a l'opposé de l'unit non collector en face
    public void runAway(Units unit) {

        ArrayList<Coordinate> accessibleCoord = new ArrayList<Coordinate>();

        accessibleCoord = Controller.getAccessibleAdjacentCoordinates(this.coord);
        Coordinate nextCoord;

        if (this.coord.getX() < unit.getCoord().getX()) {

            nextCoord = new Coordinate(this.coord.getX() - 1, this.coord.getY());
            if (accessibleCoord.contains(nextCoord)) {
                this.coord = nextCoord;
            } else {
            }

        } else {
            nextCoord = new Coordinate(this.coord.getX() + 1, this.coord.getY());
            if (accessibleCoord.contains(nextCoord)) {
                this.coord = nextCoord;
            } else {
            }

        }

        if (this.coord.getY() < unit.getCoord().getY()) {
            nextCoord = new Coordinate(this.coord.getX(), this.coord.getY()-1);
            if (accessibleCoord.contains(nextCoord)) {
                this.coord = nextCoord;
            } else {
            }
        } else {
            nextCoord = new Coordinate(this.getCoord().getX(), this.getCoord().getY() + 1);
            if (accessibleCoord.contains(nextCoord)) {
                this.coord = nextCoord;
            }else{

            }
        }

    }

}

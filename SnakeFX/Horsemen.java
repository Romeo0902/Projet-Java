import java.util.ArrayList;

public class Horsemen extends Units {

    private final static int damage = 10;
    private static int maxHp = 200;
    private static double bonus = 2;

    public Horsemen(Coordinate coord, int damage, double bonus, int hp, String side) {
        super(hp, damage, coord, side);
        Horsemen.bonus = bonus;
    }

    public static int getDamage() {
        return damage;
    }

    public static int getMaxHp() {
        return maxHp;
    }

    public static double getBonus() {
        return bonus;
    }


    public void groupHorsemen(int ROWS, int COLUMNS, ArrayList<Units> listOfUnits,
            ArrayList<Units> listOfUnitsNorth, ArrayList<Units> listOfUnitsSouth, int northBaseBot,
            int southBaseTop) {

        String north = "north";
        String south = "south";
        // trouver le cavalier le plus proche
        Coordinate horsemanCoord = new Coordinate(this.coord.getX(), this.coord.getY());
        Units closestHorseman = null;
        double minimalDistance = ROWS + COLUMNS;

        // rejoindre le horseman le plus proche pour former un groupe
        if (this.getSide() == north) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsNorth.contains(unit)) {
                    if (unit instanceof Horsemen) {
                        double distance = horsemanCoord.getDistanceWith(unit.getCoord());
                        if (distance < minimalDistance) {
                            minimalDistance = distance;
                            closestHorseman = unit;
                        }
                    }
                }
            }
        }
        if (this.getSide() == south) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsSouth.contains(unit)) {
                    if (unit instanceof Horsemen) {
                        double distance = horsemanCoord.getDistanceWith(unit.getCoord());
                        if (distance < minimalDistance) {
                            minimalDistance = distance;
                            closestHorseman = unit;
                        }
                    }
                }
            }
        }

        Coordinate nextPosition = Controller.getNextCoordinateForTarget(horsemanCoord, closestHorseman.getCoord());

        horsemanCoord = nextPosition;

        if (Controller.isInRange(horsemanCoord, closestHorseman)) {
            moveToTarget(ROWS, COLUMNS, listOfUnits, listOfUnitsNorth, listOfUnitsSouth, northBaseBot, southBaseTop);
        }

    }
    // aller vers une target

    public void moveToTarget(int ROWS, int COLUMNS, ArrayList<Units> listOfUnits,
            ArrayList<Units> listOfUnitsNorth, ArrayList<Units> listOfUnitsSouth, int northBaseBot,
            int southBaseTop) {

        String north = "north";
        String south = "south";
        // trouver le tree le plus proche du collector
        Coordinate horsemanCoord = new Coordinate(this.coord.getX(), this.coord.getY());
        Units closestUnit = null;
        double minimalDistance = ROWS + COLUMNS;

        if (side == north) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsSouth.contains(unit)) {
                    double distance = horsemanCoord.getDistanceWith(unit.getCoord());
                    if (distance < minimalDistance) {
                        minimalDistance = distance;
                        closestUnit = unit;
                    }
                }
            }
        }
        if (side == south) {
            for (Units unit : listOfUnits) {
                if (listOfUnitsNorth.contains(unit)) {
                    double distance = horsemanCoord.getDistanceWith(unit.getCoord());
                    if (distance < minimalDistance) {
                        minimalDistance = distance;
                        closestUnit = unit;
                    }
                }
            }
        }

        Coordinate nextPosition = Controller.getNextCoordinateForTarget(horsemanCoord, closestUnit.getCoord());

        horsemanCoord = nextPosition;

        if (Controller.isInRange(horsemanCoord, closestUnit)) {
            attack(horsemanCoord, closestUnit);
        }

    }

    public static void attack(Coordinate horsemanCoord, Units closestUnit) {
        int attackDamage = damage;
        if (Controller.isInRange(horsemanCoord, closestUnit)) {
            if (closestUnit instanceof Deserters) {
                attackDamage = (int) (attackDamage * bonus);
                closestUnit.setHp(closestUnit.getHp()-attackDamage);
            }else{
                closestUnit.setHp(closestUnit.getHp()-attackDamage);
            }
        }
    }

    @Override
    public void moveToTarget(GameElement gameElement, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth,
    Bases baseSouth, ArrayList<GameElement> listOfGameElements, ArrayList<Tree>listOfTrees) {
    }

    @Override
    public void die(ArrayList<GameElement> listOfGameElements,ArrayList<Units> listOfUnits, ArrayList<Collectors> listOfCollectors,
            ArrayList<Deserters> listOfDeserters, ArrayList<Horsemen> listOfHorsemen,
            ArrayList<Pikemen> listOfPikemen) {

        this.setCoord(null);
        listOfHorsemen.remove(this);
        listOfUnits.remove(this);
        listOfGameElements.remove(this);
    }

}
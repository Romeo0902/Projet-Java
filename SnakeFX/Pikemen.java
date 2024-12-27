import java.util.ArrayList;

public class Pikemen extends Units {
    private final static int damage = 15;
    private static int maxHp = 175;
    private static double bonus = 3;

    public Pikemen(Coordinate coord, int hp, double bonus, int damage, String side) {
        super(hp, damage, coord, side);
        Pikemen.bonus = bonus;
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

    @Override
    public void moveToTarget(GameElement gameElement, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth,
    Bases baseSouth, ArrayList<GameElement> listOfGameElements, ArrayList<Tree>listOfTrees) {
    }

    @Override
    public void die(ArrayList<GameElement> listOfGameElements,ArrayList<Units> listOfUnits,ArrayList<Collectors> listOfCollectors,
            ArrayList<Deserters> listOfDeserters, ArrayList<Horsemen> listOfHorsemen,
            ArrayList<Pikemen> listOfPikemen) {

        this.setCoord(null);
        listOfUnits.remove(this);
        listOfPikemen.remove(this);
        listOfGameElements.remove(this);
    }

}
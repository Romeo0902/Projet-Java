import java.util.ArrayList;

public abstract class Units extends GameElement {
    public static int damage;
    public String side;
    public Coordinate coord;
    public int hp;

    public Units(int hp, int damage, Coordinate coord, String side) {
        this.coord = coord;
        Units.damage = damage;
        this.side = side;
        this.hp = hp;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return this.side;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public abstract void moveToTarget( GameElement gameElement, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth, Bases baseSouth,
     ArrayList<GameElement> listOfGameElements, ArrayList<Tree> listOfTrees);

    public abstract void die(ArrayList<GameElement> listOfGameElements,ArrayList<Units> listOfUnits,ArrayList<Collectors> listOfCollectors,
    ArrayList<Deserters> listOfDeserters,ArrayList<Horsemen> listOfHorsemen,ArrayList<Pikemen> listOfPikemen );



}
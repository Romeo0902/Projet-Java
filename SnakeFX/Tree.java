import java.util.ArrayList;

import javafx.scene.image.Image;

public class Tree extends Item {
    private int wood;
    public final static int maxWood = 100;
    public Coordinate coord;

    // Declaration img
    private static final String[] TREE_IMAGE100 = new String[] { "/img/Tree100pv.png" };
    private static final String[] TREE_IMAGE50 = new String[] { "/img/Tree50pv.png" };
    private static final String[] TREE_IMAGE25 = new String[] { "/img/Tree25pv.png" };
    private static Image treeImage = new Image(TREE_IMAGE100[0]);

    public Tree(int wood, Coordinate coord) {
        super(coord);
        this.wood = wood;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public static int getMaxWood() {
        return maxWood;
    }

    public Image getImage(){
        if (wood>=50) {
            treeImage = new Image(TREE_IMAGE100[0]);
            return treeImage;
        }else if (wood<=50 && wood>=25) {
            treeImage = new Image(TREE_IMAGE50[0]);
            return treeImage;
        }else{
            treeImage = new Image(TREE_IMAGE25[0]);
            return treeImage;
        }
    }
    public boolean isCollectable(int northBaseBot, int southBaseTop, int basesLeft, int basesRight,
            int ROWS, int COLUMNS, ArrayList<GameElement> listOfGameElements) {

        if (Coordinate.getAccessibleAdjacentCoordinates(this.getCoord(), northBaseBot, southBaseTop, basesLeft,
                basesRight, ROWS, COLUMNS, listOfGameElements) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteItem(ArrayList<GameElement> listOfGameElements, ArrayList<Tree> listOfTrees, ArrayList<Item> listOfItems) {
        this.setCoord(null);
        listOfGameElements.remove(this);
        listOfItems.remove(this);
        listOfTrees.remove(this);

    }

    

}

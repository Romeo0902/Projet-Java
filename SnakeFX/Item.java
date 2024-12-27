import java.util.ArrayList;

public abstract class Item extends GameElement {
    private Coordinate coord;

    public Item(Coordinate coord){
        this.coord = coord;
    }

    public Coordinate getCoord() {
        return coord;
    } 

    public abstract void deleteItem(ArrayList<GameElement> listOfGameElements, ArrayList<Tree> listOfTrees, ArrayList<Item> listOfItems);
    
}
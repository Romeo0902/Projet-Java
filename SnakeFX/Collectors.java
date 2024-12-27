import java.util.ArrayList;

public class Collectors extends Units {
    private final static int damage = 5;
    private static int maxHp = 150;
    private int woodStock = 0;
    private final static int maxWoodStock = 25;
    private final static int emptyWoodStock = 0;

    public Collectors(Coordinate coord, int hp, int damage, String side, int woodStock) {
        super(hp, damage, coord, side);
        this.woodStock = woodStock;
    }

    public int getWoodStock() {
        return woodStock;
    }

    public void setWoodStock(int woodStock) {
        this.woodStock = woodStock;
    }

    public static int getDamage() {
        return damage;
    }

    public static int getMaxHp() {
        return maxHp;
    }

    public static int getEmptyWoodStock() {
        return emptyWoodStock;
    }

    public void defineTarget(int ROWS, int COLUMNS, ArrayList<Tree> listOfTrees, int northBaseBot,
            int southBaseTop, int basesLeft, int basesRight,
            ArrayList<GameElement> listOfGameElements, Bases baseNorth, Bases baseSouth) {
        // Si le stock est plein, aller directement à la base
        if (this.getWoodStock() >= maxWoodStock) {
            this.moveToBase(ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth);
        }

        // Trouver l'arbre le plus proche
        Coordinate collectorCoord = new Coordinate(this.getCoord().getX(), this.getCoord().getY());
        Tree closestTree = null;
        double minimalDistance = ROWS + COLUMNS;

        for (Tree tree : listOfTrees) {
            double distance = collectorCoord.getDistanceWith(tree.getCoord());
            if (distance < minimalDistance) {
                minimalDistance = distance;
                if (tree.isCollectable(northBaseBot, southBaseTop, basesLeft, basesRight, ROWS, COLUMNS,
                        listOfGameElements)) {
                    closestTree = tree;
                }
            }
        }

        // Se déplacer vers l'arbre le plus proche
        moveToTarget(closestTree, ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth, listOfGameElements,
                listOfTrees);
    }

    // Collection des arbres

    private void collectTree(GameElement tree, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth,
            Bases baseSouth, int basesLeft, int basesRight, ArrayList<GameElement> listOfGameElements,
            ArrayList<Tree> listOfTrees) {
        // Vérifier si le stock est plein avant de collecter
        if (this.getWoodStock() >= maxWoodStock) {
            moveToBase(ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth);
            return;
        }

        // Ajouter du bois et diminuer le stock de l'arbre
        this.setWoodStock(this.getWoodStock() + 1);
        ((Tree) tree).setWood(((Tree) tree).getWood() - 1);

        // Supprimer l'arbre si son bois tombe à 0
        if (((Tree) tree).getWood() <= 0) {
            ((Tree) tree).deleteItem(listOfGameElements, listOfTrees, null);
        }
    }

    private static String north = "north";

    private void moveToBase(int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth, Bases baseSouth) {
        Coordinate collectorCoordinate = new Coordinate(this.getCoord().getX(), this.getCoord().getY());

        // Déterminer la coordonnée cible en fonction du côté
        Coordinate woodStockBaseCoord = (this.getSide() == north)
                ? baseNorth.getCoordStockWood()
                : baseSouth.getCoordStockWood();

        // Calculer la prochaine position pour atteindre la cible
        Coordinate nextPosition = Controller.getNextCoordinateForTarget(collectorCoordinate, woodStockBaseCoord);

        // Mettre à jour la position actuelle
        this.setCoord(nextPosition);

        // verif si on a atteint la base et drop le bois
        if (collectorCoordinate==woodStockBaseCoord) {
            // Déposer le bois à la base
            dropWoodToBase(baseNorth, baseSouth, Controller.listOfTrees, Controller.listOfGameElements);

        }

    }

    // Méthode pour déposer le bois à la base
    private void dropWoodToBase(Bases baseNorth, Bases baseSouth, ArrayList<Tree> listOfTrees,
            ArrayList<GameElement> listOfGameElements) {
        // Vérifier la base correspondante en fonction du côté du collector
        if (this.getSide() == north) {
            // Déposer le bois à la base nord
            baseNorth.setWoodStock(baseNorth.getWoodStockBase() + this.getWoodStock());
        } else {
            // Déposer le bois à la base sud
            baseSouth.setWoodStock(baseSouth.getWoodStockBase() + this.getWoodStock());
        }

        // Vider le stock de bois du collector
        this.setWoodStock(0);

    }

    // gameElement doit etre un Tree sinon kapout
    @Override
    public void moveToTarget(GameElement gameElement, int ROWS, int northBaseBot, int southBaseTop, Bases baseNorth,
            Bases baseSouth, ArrayList<GameElement> listOfGameElements, ArrayList<Tree> listOfTrees) {
        // Si le stock est plein, aller à la base
        if (this.getWoodStock() >= maxWoodStock) {
            this.moveToBase(ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth);
            return; // Arrêter la méthode ici
        }

        // Vérifier si à portée de l'arbre
        if (Controller.isInRange(this.getCoord(), gameElement)) {
            // Collecter l'arbre et sortir
            this.collectTree(gameElement, ROWS, northBaseBot, southBaseTop, baseNorth, baseSouth, Controller.basesLeft,
                    Controller.basesRight, listOfGameElements, listOfTrees);
            return;
        }

        // Si pas à portée, calculer la prochaine position vers l'arbre
        Coordinate nextPosition = Controller.getNextCoordinateForTarget(this.getCoord(), gameElement.getCoord());
        this.setCoord(nextPosition);
    }

    @Override
    public void die(ArrayList<GameElement> listOfGameElements, ArrayList<Units> listOfUnits,
            ArrayList<Collectors> listOfCollectors,
            ArrayList<Deserters> listOfDeserters, ArrayList<Horsemen> listOfHorsemen,
            ArrayList<Pikemen> listOfPikemen) {

        this.setCoord(null);
        listOfCollectors.remove(this);
        listOfUnits.remove(this);
        listOfGameElements.remove(this);
    }

}

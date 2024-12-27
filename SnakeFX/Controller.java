
// karnay.romeo.java.q3.2425@gmail.com -  prigogine/rkarnay compte chatgpt
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

//import java.util.List;
public class Controller {

    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH;
    public static final int ROWS = 40;
    public static final int COLUMNS = ROWS;
    public static final int SQUARE_SIZE = WIDTH / ROWS;
    public static final int basesRight = (ROWS / 2) + 3;
    public static final int basesLeft = (ROWS / 2) - 3;
    public static final int southBaseTop = COLUMNS - 6;
    public static final int northBaseBot = 5;
    public static final int treesNumber = 6;
    public static final double chanceOfTreePerCase = 1;

    public static int north = 1;
    public static int south = 2;
    public static ArrayList<GameElement> listOfGameElements = new ArrayList<GameElement>();
    public static ArrayList<Collectors> listOfCollectors = new ArrayList<Collectors>();
    public static ArrayList<Deserters> listOfDeserters = new ArrayList<Deserters>();
    public static ArrayList<Horsemen> listOfHorsemen = new ArrayList<Horsemen>();
    public static ArrayList<Pikemen> listOfPikemen = new ArrayList<Pikemen>();
    public static ArrayList<Units> listOfUnitsNorth = new ArrayList<Units>();
    public static ArrayList<Units> listOfUnitsSouth = new ArrayList<Units>();
    public static ArrayList<Units> listOfUnits = new ArrayList<Units>();
    public static ArrayList<Tree> listOfTrees = new ArrayList<Tree>();
    private ArrayList<Item> listOfItems = new ArrayList<Item>();

    private final Bases baseNorth = new Bases(Bases.coordSpawnNorth, Bases.getEmptyWoodStockBase(),
            Bases.coordStockNorth);
    private final Bases baseSouth = new Bases(Bases.coordSpawnSouth, Bases.getEmptyWoodStockBase(),
            Bases.coordStockSouth);

    GraphicsContext gc;
    private GameElement model;
    private View view;

    public Controller(Stage primaryStage) {
        this.view = new View(ROWS, COLUMNS, HEIGHT, WIDTH, SQUARE_SIZE);
        this.model = new GameElement();
        start(primaryStage);
    }

    public void start(Stage primaryStage) {

        getAccessibleAdjacentCoordinates(new Coordinate(0, 0));

        primaryStage.setTitle("HelbArmy");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        generateTree();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();

                if (code == KeyCode.A) {
                    Bases.collectorsSpawn(north, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.Z) {
                    Bases.desertersSpawn(north, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.E) {
                    Bases.horsemenSpawn(north, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.R) {
                    Bases.pikemenSpawn(north, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.W) {
                    Bases.collectorsSpawn(south, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.X) {
                    Bases.desertersSpawn(south, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.C) {
                    Bases.horsemenSpawn(south, ROWS, northBaseBot, southBaseTop);
                }
                if (code == KeyCode.V) {
                    Bases.pikemenSpawn(south, ROWS, northBaseBot, southBaseTop);
                }
            }

        });

        // Appeler generateTree() pour générer les arbres

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(65), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {

        for (Units unit : listOfUnits) {
            if (unit.getHp() <= 0) {
                unit.die(listOfGameElements, listOfUnits, listOfCollectors, listOfDeserters, listOfHorsemen,
                        listOfPikemen);
                listOfGameElements.remove(unit);
            }
        }
        for (Tree tree : listOfTrees) {
            // System.out.println(tree.toString() + " wood: " + tree.getWood());
            if (tree.getWood() <= 0) {
                tree.deleteItem(listOfGameElements, listOfTrees, listOfItems);
                gc.clearRect(tree.getCoord().getX() * SQUARE_SIZE, tree.getCoord().getY() * SQUARE_SIZE, SQUARE_SIZE,
                        SQUARE_SIZE);
            }
        }

        View.drawBackground(ROWS, COLUMNS, SQUARE_SIZE, gc, basesRight, basesLeft, northBaseBot, southBaseTop);
        // View.drawCollectors(listOfCollectors, gc, SQUARE_SIZE);
        for (Collectors collectors : listOfCollectors) {
            View.drawCollectors(gc, SQUARE_SIZE, collectors.getCoord(), collectors.getSide());
        }
        for (Tree tree : listOfTrees) {
            // System.out.println(tree.getWood());
            if (tree.getWood() > 0) {
                View.drawTrees(gc, SQUARE_SIZE, tree.getWood(), tree.getCoord(), WIDTH, HEIGHT, ROWS, COLUMNS);
            }

        }

        for (Collectors collector : listOfCollectors) {
            // System.out.println(collector.toString()+" stock: "+collector.getWoodStock());
            collector.defineTarget(ROWS, COLUMNS, listOfTrees, northBaseBot, southBaseTop, basesLeft, basesRight,
                    listOfGameElements, baseNorth, baseSouth);
        }

        for (Deserters deserter : listOfDeserters) {
            deserter.moveToTarget(ROWS, COLUMNS, listOfUnits, listOfUnitsNorth, listOfUnitsSouth, northBaseBot,
                    southBaseTop);
        }

        // clearTab(SQUARE_SIZE);

    }

    /*
     * private void generateFood() {
     * start: while (true) {
     * foodX = (int) (Math.random() * ROWS);
     * foodY = (int) (Math.random() * COLUMNS);
     * 
     * if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
     * continue start;
     * }
     * 
     * foodImage = new Image(FOODS_IMAGE[(int) (Math.random() *
     * FOODS_IMAGE.length)]);
     * break;
     * }
     * }
     */

    /*
     * private void drawFood(GraphicsContext gc) {
     * gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE,
     * SQUARE_SIZE, SQUARE_SIZE);
     * }
     */

    /*
     * private void drawSnake(GraphicsContext gc) {
     * gc.setFill(Color.web("4674E9"));
     * gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() *
     * SQUARE_SIZE, SQUARE_SIZE - 1,
     * SQUARE_SIZE - 1, 35, 35);
     * }
     */

    /*
     * public void gameOver() {
     * if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH
     * || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
     * gameOver = true;
     * }
     * }
     */

    /*
     * private void eatFood() {
     * if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
     * generateFood();
     * score += 5;
     * }
     * }
     */

    /*
     * private void drawScore() {
     * gc.setFill(Color.WHITE);
     * gc.setFont(new Font("Digital-7", 35));
     * gc.fillText("Score: " + score, 10, 35);
     * }
     */

    public static boolean isCoordinateInBoard(Coordinate coord) {
        return (coord.x >= 0 && coord.y >= 0 && coord.x < COLUMNS && coord.y < ROWS);
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

    public static ArrayList<Coordinate> getAccessibleAdjacentCoordinates(Coordinate coord) {

        ArrayList<Coordinate> resultList = new ArrayList<Coordinate>();

        ArrayList<Coordinate> adjacentCoordinatesList = getAdjacentCoordinates(coord);
        for (Coordinate adjacent : adjacentCoordinatesList) {
            if (isCoordinateInBoard(adjacent)) {
                resultList.add(adjacent);
            }
        }
        return resultList;
    }

    public static Coordinate getNextCoordinateForTarget(Coordinate currentCoordinate, Coordinate targetCoordinate) {

        double minDistance = ROWS + COLUMNS;
        int minIndex = -1;
        ArrayList<Coordinate> accessibleAdjacentCoordinatesList = getAccessibleAdjacentCoordinates(currentCoordinate);

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

    /*
     * public void moveToTarget() {
     * Coordinate snakeCoord = new Coordinate(snakeHead.x, snakeHead.y);
     * Coordinate foodCoord = new Coordinate(foodX, foodY);
     * Coordinate nextPosition = getNextCoordinateForTarget(snakeCoord, foodCoord);
     * snakeHead.x = nextPosition.x;
     * snakeHead.y = nextPosition.y;
     * }
     */

    // si il est a cote
    public static boolean isInRange(Coordinate unitCoord, GameElement gameElement) {
        GameElement target = gameElement;

        if ((unitCoord.getX() == target.getCoord().getX() &&
                unitCoord.getY() == target.getCoord().getY() - 1) || // Haut

                (unitCoord.getX() == target.getCoord().getX() &&
                        unitCoord.getY() == target.getCoord().getY() + 1)
                || // Bas

                (unitCoord.getX() == target.getCoord().getX() - 1 &&
                        unitCoord.getY() == target.getCoord().getY())
                || // Gauche

                (unitCoord.getX() == target.getCoord().getX() + 1 &&
                        unitCoord.getY() == target.getCoord().getY())
                || // Droite

                (unitCoord.getX() == target.getCoord().getX() - 1 &&
                        unitCoord.getY() == target.getCoord().getY() - 1)
                || // Haut gauche

                (unitCoord.getX() == target.getCoord().getX() + 1 &&
                        unitCoord.getY() == target.getCoord().getY() - 1)
                || // Haut droite

                (unitCoord.getX() == target.getCoord().getX() - 1 &&
                        unitCoord.getY() == target.getCoord().getY() + 1)
                || // Bas gauche

                (unitCoord.getX() == target.getCoord().getX() + 1 &&
                        unitCoord.getY() == target.getCoord().getY() + 1)) { // Bas droite

            // La units est autour du GameElement dans une des 8 directions
            return true;
        } else {
            return false;
        }

    }

    // Donne des coord random qui ne sont pas dans les bases
    public static Coordinate generateRandomCoordinate(int northBaseBot, int southBaseTop, int basesLeft,
            int basesRight, ArrayList<GameElement> listOfGameElements) {
        Coordinate coord = new Coordinate(0, 0);
        do {
            int x = (int) (Math.random() * ROWS);
            int y = (int) (Math.random() * COLUMNS);
            coord = new Coordinate(x, y);
        } while (Coordinate.isABase(coord, northBaseBot, southBaseTop, basesLeft, basesRight)
                || !Coordinate.isCoordinateFree(coord, listOfGameElements, northBaseBot, southBaseTop, basesLeft,
                        basesRight));

        return coord;
    }

    /*
     * private void generateTree() {
     * Coordinate coord = new Coordinate(0, 0);
     * for (int i = 0; i < ROWS; i++) {
     * for (int j = 0; j < COLUMNS; j++) {
     * 
     * if ((Coordinate.isABase(coord, northBaseBot, southBaseTop, basesLeft,
     * basesRight))
     * && (Coordinate.isCoordinateFree(coord, listOfGameElements))) {
     * if (treeRatio()) {
     * Tree tree = new Tree(Tree.maxWood, coord);
     * listOfTrees.add(tree);
     * listOfGameElements.add(tree);
     * }
     * }
     * 
     * coord.y++;
     * }
     * coord.x++;
     * }
     * 
     * }
     */

    private void generateTree() {

        /*
         * for (int i = 0; i < treesNumber; i++) {
         * Tree tree = new Tree(Tree.getMaxWood(),
         * generateRandomCoordinate(northBaseBot, southBaseTop, basesLeft, basesRight,
         * listOfGameElements));
         * listOfTrees.add(tree);
         * listOfItems.add(tree);
         * listOfGameElements.add(tree);
         * }
         */

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Coordinate treeCoord = new Coordinate(i, j);

                if (treeRatio() == true && Coordinate.isCoordinateFree(treeCoord, listOfGameElements, northBaseBot, southBaseTop, basesLeft, basesRight)) {
                    Tree tree = new Tree(Tree.getMaxWood(), treeCoord);
                    listOfTrees.add(tree);
                    listOfItems.add(tree);
                    listOfGameElements.add(tree);
                }
            }
        }
    }

    private boolean treeRatio() {
        int percentage = (int) (Math.random() * 100);
        System.out.println(percentage);
        if (percentage <= chanceOfTreePerCase) {
            return true;
        } else {
            return false;
        }

    }

    public static void removeTree(Tree tree) {
        // Supprimer l'arbre de toutes les listes nécessaires
        listOfGameElements.remove(tree);
        listOfTrees.remove(tree);

        // Ajouter un message ou une action pour vérifier si la suppression s'est bien
        // effectuée
        System.out.println("Arbre supprimé : " + tree.getCoord());
    }

}

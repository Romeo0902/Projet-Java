
//import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class View {

    // Declaration img
    private static final String[] COLLECTORS_IMAGE_NORTH = new String[] { "/img/Northcollector.png" };
    private static final String[] COLLECTORS_IMAGE_SOUTH = new String[] { "/img/Southcollector.png" };

    // IMG
    // foodImage = new Image(FOODS_IMAGE[(int) (Math.random() *
    // FOODS_IMAGE.length)]);

    private static Image collectorImageToDraw;
    private static Image collectorImageNorth = new Image(COLLECTORS_IMAGE_NORTH[0]);
    private static Image collectorImageSouth = new Image(COLLECTORS_IMAGE_SOUTH[0]);

    public View(int ROWS, int COLUMNS, int HEIGHT, int WIDTH, int SQUARE_SIZE) {

    }

    // Méthode pour dessiner le fond du jeu
    public static void drawBackground(int ROWS, int COLUMNS, int SQUARE_SIZE, GraphicsContext gc, int basesRight,
            int basesLeft, int northBaseBot, int southBaseTop) {
        // Dessiner un fond avec des carrés
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                if (i < basesRight && i > basesLeft && j < northBaseBot && !(j == northBaseBot - 1 && i == ROWS / 2)
                        && !(i == basesRight - 1 && j == northBaseBot / 2)) {
                    gc.setFill(Color.web("D3D3D3"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                } else if (j == northBaseBot - 1 && i == ROWS / 2) {
                    gc.setFill(Color.web("FFFFFF"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                } else if (i == basesRight - 1 && j == northBaseBot / 2) {
                    gc.setFill(Color.web("FFFFFF"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                } else if (i < basesRight && i > basesLeft && j > southBaseTop
                        && !(j == southBaseTop + 1 && i == ROWS / 2)
                        && !(i == basesLeft + 1 && j == southBaseTop + 3)) {
                    gc.setFill(Color.web("D3D3D3"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                } else if (j == southBaseTop + 1 && i == ROWS / 2) {
                    gc.setFill(Color.web("000000"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                } else if (i == basesLeft + 1 && j == southBaseTop + 3) {
                    gc.setFill(Color.web("000000"));
                    gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                } else {

                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }

            }
        }
    }

    // Method pour dessiner collectors
    public static void drawCollectors( GraphicsContext gc, int SQUARE_SIZE, Coordinate collectorCoord, String side) {

            System.out.println(collectorCoord.getX()+" , "+collectorCoord.getY());
            String north = "north";
            String south = "south";
            // image en fonction du side
            if (side == north) {
                collectorImageToDraw = collectorImageNorth;
            } else if (side == south) {
                collectorImageToDraw = collectorImageSouth;
            }

            // dessiner le collecteur
            gc.drawImage(collectorImageToDraw, collectorCoord.getX() * SQUARE_SIZE,
            collectorCoord.getY() * SQUARE_SIZE,
                    SQUARE_SIZE, SQUARE_SIZE);

        
        
    }


    // Method pour dessiner les arbres
    public static void drawTrees(GraphicsContext gc, int SQUARE_SIZE, int treeWood, Coordinate treeCoord, int basesRight, int basesLeft, int northBaseBot, int southBaseTop) {


        // Dessiner l arbre

        // Supposons que SQUARE_SIZE soit la taille de la case
        double xPos = treeCoord.getX() * SQUARE_SIZE;
        double yPos = treeCoord.getY() * SQUARE_SIZE;

        // Calculer les points du losange à dessiner
        double centerX = xPos + SQUARE_SIZE / 2;
        double centerY = yPos + SQUARE_SIZE / 2;
        double halfSize = SQUARE_SIZE / 2;

        double[] xPoints = {
                centerX, // Haut
                centerX + halfSize, // Droite
                centerX, // Bas
                centerX - halfSize // Gauche
        };
        double[] yPoints = {
                centerY - halfSize, // Haut
                centerY, // Droite
                centerY + halfSize, // Bas
                centerY // Gauche
        };

        // Définir la couleur du losange
        int wood = treeWood;
        if (wood > 50) {
            gc.setFill(Color.rgb(0, 255, 0));
        } else if (wood <= 50 && wood >= 25) {
            gc.setFill(Color.rgb(0, 127, 0));
        } else {
            gc.setFill(Color.rgb(0, 63, 0));
        }
        //System.out.println("Drawing tree at " + treeCoord + " with wood: " + treeWood);

        // gc.setFill(Color.GREEN);

        // Dessiner le losange
        gc.fillPolygon(xPoints, yPoints, 4);

        gc.setFill(Color.WHITE);



    }


    public static void clear(Coordinate coord, GraphicsContext gc, int SQUARE_SIZE) {
        // Effacer la case
        gc.clearRect(coord.getX() * SQUARE_SIZE, coord.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

        // Redessiner un contour noir autour de la case
        gc.setStroke(Color.BLACK);
        gc.strokeRect(coord.getX() * SQUARE_SIZE, coord.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

}

package mazesolver.domain;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A class that is used to keep track of and to remove walls of each rectangle
 * in the maze.
 */
public class Rect {
    /**
     * The size of the rectangle.
     */
    private final int rectangleSize = 30;
    /**
     * Region object that represents a single square in the maze.
     */
    private Region rect;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    /**
     * Keeps count of times that the rectangle has been painted.
     */
    private int timesPainted = 0;
    /**
     * The X coordinate of the rectangle in the maze.
     */
    private int x;
    /**
     * The Y coordinate of the rectangle in the maze.
     */
    private int y;

    public Rect(int x, int y) {
        this.x = x;
        this.y = y;
        this.rect = new Region();
        this.rect.setPrefSize(rectangleSize, rectangleSize);
        updateBorders();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Returns the Region object that represents a single rectangle in a maze.
     *
     * @return Region object
     */
    public Region getRectangle() {
        return this.rect;
    }

    public boolean getTopWall() {
        return this.topWall;
    }

    public boolean getRightWall() {
        return this.rightWall;
    }

    public boolean getBottomWall() {
        return this.bottomWall;
    }

    public boolean getLeftWall() {
        return this.leftWall;
    }

    /**
     * Removes the top Border from the Region object.
     */
    public void removeTopWall() {
        this.topWall = false;
        updateBorders();
    }

    /**
     * Removes the right Border from the Region object.
     */
    public void removeRightWall() {
        this.rightWall = false;
        updateBorders();
    }

    /**
     * Removes the bottom Border from the Region object.
     */
    public void removeBottomWall() {
        this.bottomWall = false;
        updateBorders();
    }

    /**
     * Removes the left Border from the Region object.
     */
    public void removeLeftWall() {
        this.leftWall = false;
        updateBorders();
    }

    /**
     * Paints the rectangle green.
     */
    public void paintGreen() {
        this.rect.setStyle("-fx-background-color: rgb(0,255,0); -fx-background-insets: 4px");
    }

    /**
     * Paints the rectangle red. The darkness of the color depends on how many time
     * the square has been visited.
     */
    @SuppressWarnings("checkstyle:magicNumber")
    public void paint() {

        switch (timesPainted) {
            case 0:
                this.rect.setStyle(
                        "-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px");
                break;
            case 1:
                this.rect.setStyle(
                        "-fx-background-color: rgb(210,0,0); -fx-background-insets: 4px");
                break;
            case 2:
                this.rect.setStyle(
                        "-fx-background-color: rgb(180,0,0); -fx-background-insets: 4px");
                break;
            case 3:
                this.rect.setStyle(
                        "-fx-background-color: rgb(140,0,0); -fx-background-insets: 4px");
                break;
            case 4:
                this.rect.setStyle(
                        "-fx-background-color: rgb(100,0,0); -fx-background-insets: 4px");
                break;
            case 5:
                this.rect.setStyle(
                        "-fx-background-color: rgb(90,0,0); -fx-background-insets: 4px");
                break;
            default:
                this.rect.setStyle(
                        "-fx-background-color: rgb(40,0,0); -fx-background-insets: 4px");
        }

        this.timesPainted++;
    }

    /**
     * Clears the background color of the rectangle.
     */
    public void removeBackground() {
        this.rect.setStyle("");
        this.timesPainted = 0;
    }

    /**
     * Updates the borders of the Region object.
     */
    private void updateBorders() {
        this.rect.setBorder(
                new Border(
                        new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                                topWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                                rightWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                                bottomWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                                leftWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                                null, new BorderWidths(1), null)));
    }
}

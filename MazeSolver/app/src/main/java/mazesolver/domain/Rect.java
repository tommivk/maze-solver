package mazesolver.domain;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mazesolver.enums.Direction;

/**
 * A class that is used to keep track of and to remove walls of each rectangle
 * in the maze.
 */
public class Rect {
    private final int rectangleSize = 30;
    private Region rect;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;

    public Rect() {
        this.rect = new Region();
        this.rect.setPrefSize(rectangleSize, rectangleSize);
        updateBorders();
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

    public void paint() {
        Paint paint = Paint.valueOf("red");
        Insets insets = new Insets(4);
        BackgroundFill fill = new BackgroundFill(paint, null, insets);
        Background b = new Background(fill);
        this.rect.setBackground(b);
    }

    public void removeBackground() {
        this.rect.setBackground(null);
    }

    /**
     * Updates the borders of the Region object.
     */
    private void updateBorders() {
        this.rect.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                topWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                rightWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                bottomWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                leftWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                null, new BorderWidths(1), null)));
    }
}

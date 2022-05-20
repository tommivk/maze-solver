package mazesolver.domain;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Rect {
    private Region rect;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;

    public Rect() {
        this.rect = new Region();
        this.rect.setPrefSize(30, 30);
        updateBorders();
    }

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

    public void removeTopWall() {
        this.topWall = false;
        updateBorders();
    }

    public void removeRightWall() {
        this.rightWall = false;
        updateBorders();
    }

    public void removeBottomWall() {
        this.bottomWall = false;
        updateBorders();
    }

    public void removeLeftWall() {
        this.leftWall = false;
        updateBorders();
    }

    private void updateBorders() {
        this.rect.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                topWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                rightWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                bottomWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                leftWall ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE,
                null, new BorderWidths(1), null)));
    }
}

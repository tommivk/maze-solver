package mazesolver;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Region;
import mazesolver.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class RectTest {

    @Test
    public void getRectangleWorksAndRectSizeIsCorrect() {
        Rect rect = new Rect(0, 0);
        Region rectangle = rect.getRectangle();
        assertEquals(30, rectangle.getPrefHeight());
        assertEquals(30, rectangle.getPrefWidth());
    }

    @Test
    public void intialWallsAresetToTrue() {
        Rect rect = new Rect(0, 0);
        assertEquals(true, rect.getTopWall());
        assertEquals(true, rect.getRightWall());
        assertEquals(true, rect.getBottomWall());
        assertEquals(true, rect.getLeftWall());
    }

    @Test
    public void removingWallsWorks() {
        Rect rect = new Rect(0, 0);
        rect.removeTopWall();
        rect.removeRightWall();
        rect.removeBottomWall();
        rect.removeLeftWall();

        assertEquals(false, rect.getTopWall());
        assertEquals(false, rect.getRightWall());
        assertEquals(false, rect.getBottomWall());
        assertEquals(false, rect.getLeftWall());
    }

    @Test
    public void initialBordersAreCorrect() {
        Rect rect = new Rect(0, 0);
        Region rectangle = rect.getRectangle();
        BorderStroke border = rectangle.getBorder().getStrokes().get(0);

        assertEquals(BorderStrokeStyle.SOLID, border.getTopStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getRightStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getBottomStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getLeftStyle());
    }

    @Test
    public void removingBordersSetsBorderStrokeStyleToNone() {
        Rect rect = new Rect(0, 0);
        Region rectangle = rect.getRectangle();

        rect.removeTopWall();
        rect.removeRightWall();
        rect.removeBottomWall();
        rect.removeLeftWall();

        BorderStroke border = rectangle.getBorder().getStrokes().get(0);

        assertEquals(BorderStrokeStyle.NONE, border.getTopStyle());
        assertEquals(BorderStrokeStyle.NONE, border.getRightStyle());
        assertEquals(BorderStrokeStyle.NONE, border.getBottomStyle());
        assertEquals(BorderStrokeStyle.NONE, border.getLeftStyle());
    }

    @Test
    public void paintGreenShouldWorkCorrectly() {
        Rect rect = new Rect(0, 0);
        assertEquals("", rect.getRectangle().getStyle());
        rect.paintGreen();
        assertEquals("-fx-background-color: rgb(0,255,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
    }

    @Test
    public void paintingRectangleShouldWorkCorrectly() {
        Rect rect = new Rect(0, 0);
        assertEquals("", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(255,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(210,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(180,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(140,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(100,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(90,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
        rect.paint();
        assertEquals("-fx-background-color: rgb(40,0,0); -fx-background-insets: 4px", rect.getRectangle().getStyle());
    }

}

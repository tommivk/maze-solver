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
        Rect rect = new Rect();
        Region rectangle = rect.getRectangle();
        assertEquals(30, rectangle.getPrefHeight());
        assertEquals(30, rectangle.getPrefWidth());
    }

    @Test
    public void intialWallsAresetToTrue() {
        Rect rect = new Rect();
        assertEquals(true, rect.getTopWall());
        assertEquals(true, rect.getRightWall());
        assertEquals(true, rect.getBottomWall());
        assertEquals(true, rect.getLeftWall());
    }

    @Test
    public void removingWallsWorks() {
        Rect rect = new Rect();
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
        Rect rect = new Rect();
        Region rectangle = rect.getRectangle();
        BorderStroke border = rectangle.getBorder().getStrokes().get(0);

        assertEquals(BorderStrokeStyle.SOLID, border.getTopStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getRightStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getBottomStyle());
        assertEquals(BorderStrokeStyle.SOLID, border.getLeftStyle());
    }

    @Test
    public void removingBordersSetsBorderStrokeStyleToNone() {
        Rect rect = new Rect();
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

}

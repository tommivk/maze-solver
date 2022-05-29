package mazesolver.domain;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mazesolver.enums.Direction;

/**
 * An algorithm that keeps following the right wall of the maze until an exit is
 * found.
 */
public class WallFollower {
    private int x;
    private int y;
    private Rect[][] maze;
    private Direction previousDirection;
    private int moves = 0;

    /**
     * Returns the current X coordinate.
     *
     * @return An integer number representing the X coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the current Y coordinate.
     *
     * @return An integer number representing the Y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the maze array
     *
     * @return Two dimensional array of Rect objects
     */
    public Rect[][] getMaze() {
        return this.maze;
    }

    /**
     * Returns the amount of moves it took to solve the maze.
     *
     * @return An integer number representing the amount of moves
     */
    public int getMoves() {
        return this.moves;
    }

    /**
     * Returns the direction of the previous move.
     *
     * @return A direction enum representing the direction of the previous move
     */
    public Direction getPreviousDirection() {
        return this.previousDirection;
    }

    /**
     * Constructor for the Wall Follower algorithm.
     *
     * @param maze Two dimensional array of Rect objects that represent the maze.
     */
    public WallFollower(Rect[][] maze) {
        this.x = 0;
        this.y = 0;
        this.maze = maze;
        this.previousDirection = Direction.South;
    }

    /**
     * Resets the current coordinate position and removes background colors from all
     * of the rectangles in the maze.
     */
    public void reset() {
        this.x = 0;
        this.y = 0;
        for (int i = 0; i < maze.length; i++) {
            for (int k = 0; k < maze.length; k++) {
                maze[i][k].removeBackground();
            }
        }
    }

    /**
     * Solves the maze with JavaFx timeline delay between the moves. Paints the
     * current coordinate on the maze after each move.
     */
    public void animate(int moves, int delay) {
        Timeline[] timelines = new Timeline[moves];
        maze[0][0].paint();
        int i = 0;
        while (i < moves) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(delay), event -> {
                        calculateNextMove();
                        paintRectangle();
                    }));
            timelines[i] = timeline;
            i++;
        }
        SequentialTransition sequence = new SequentialTransition(timelines);
        sequence.play();
    }

    /**
     * A Method that solves the maze.
     */
    public void solve() {
        int moves = 0;
        while (x != maze.length - 1 || y != maze.length - 1) {
            calculateNextMove();
            moves++;
        }
        this.x = 0;
        this.y = 0;
        this.moves = moves;
    }

    /**
     * Paints the rectangle in the current X and Y position.
     */
    public void paintRectangle() {
        maze[this.x][this.y].paint();
    }

    /**
     * Moves the current position one step to the West in the maze and updates the
     * previous direction.
     */
    public void moveLeft() {
        this.x = this.x - 1;
        this.previousDirection = Direction.West;
    }

    /**
     * Moves the current position one step to the East in the maze and updates the
     * previous direction.
     */
    public void moveRight() {
        this.x = this.x + 1;
        this.previousDirection = Direction.East;
    }

    /**
     * Moves the current position one step to the Norh in the maze and updates the
     * previous direction.
     */
    public void moveUp() {
        this.y = this.y - 1;
        this.previousDirection = Direction.North;
    }

    /**
     * Moves the current position one step to the South in the maze and updates the
     * previous direction.
     */
    public void moveDown() {
        this.y = this.y + 1;
        this.previousDirection = Direction.South;
    }

    /**
     * Calculates the coordinate that the algorithm will move next based on the
     * previous direction and the walls that are around the current rectangle.
     */
    public void calculateNextMove() {
        Rect current = maze[x][y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        if (previousDirection == Direction.South) {
            if (!hasLeftWall) {
                moveLeft();
            } else if (hasLeftWall && !hasBottomWall) {
                moveDown();
            } else if (hasLeftWall && hasBottomWall
                    && !hasRightWall) {
                moveRight();
            } else if (hasLeftWall && hasBottomWall
                    && hasRightWall) {
                moveUp();
            }
        } else if (previousDirection == Direction.East) {
            if (!hasBottomWall) {
                moveDown();
            } else if (hasBottomWall && !hasRightWall) {
                moveRight();
            } else if (hasBottomWall && hasRightWall && hasTopWall) {
                moveLeft();
            } else if (hasBottomWall && hasRightWall && !hasTopWall) {
                moveUp();
            }
        } else if (previousDirection == Direction.North) {
            if (!hasRightWall) {
                moveRight();
            } else if (hasRightWall && !hasTopWall) {
                moveUp();
            } else if (hasTopWall && hasLeftWall && hasRightWall) {
                moveDown();
            } else if (hasRightWall && hasTopWall && !hasLeftWall) {
                moveLeft();
            }
        } else if (previousDirection == Direction.West) {
            if (!hasTopWall) {
                moveUp();
            } else if (hasTopWall && !hasLeftWall) {
                moveLeft();
            } else if (hasTopWall && hasLeftWall && !hasBottomWall) {
                moveDown();
            } else if (hasTopWall && hasLeftWall && hasBottomWall) {
                moveRight();
            }
        }
    }
}

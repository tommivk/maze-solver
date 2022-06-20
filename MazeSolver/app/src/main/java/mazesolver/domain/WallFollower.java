package mazesolver.domain;

import mazesolver.enums.Direction;

/**
 * An algorithm that keeps following the right wall of the maze until an exit is
 * found.
 */
public class WallFollower {
    /**
     * Current x coordinate in the maze.
     */
    private int x;
    /**
     * Current y coordinate in the maze.
     */
    private int y;
    /**
     * Two dimensional array of Rect objects that represents the maze.
     */
    private Rect[][] maze;
    /**
     * The current direction.
     */
    private Direction facing;

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
     * Sets the current X Coordinate.
     *
     * @param x Integer value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the current Y Coordinate.
     *
     * @param y Integer value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the maze array.
     *
     * @return Two dimensional array of Rect objects
     */
    public Rect[][] getMaze() {
        return this.maze;
    }

    /**
     * Returns the direction of the previous move.
     *
     * @return A direction enum representing the direction of the previous move
     */
    public Direction getFacing() {
        return this.facing;
    }

    /**
     * Sets the current direction.
     *
     * @param direction A Direction enum
     */
    public void setFacing(Direction direction) {
        this.facing = direction;
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
        this.facing = Direction.South;
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
     * A Method that solves the maze.
     * 
     * @return The amount of moves it took to solve the maze.
     */
    public int solve() {
        int moves = 0;
        while (x != maze.length - 1 || y != maze.length - 1) {
            calculateNextMove();
            moves++;
        }
        return moves;
    }

    /**
     * Paints the rectangle in the current X and Y position red.
     */
    public void paintRectangle() {
        maze[this.x][this.y].paint();
    }

    /**
     * Paints the rectangle in the current X and Y position green.
     */
    public void paintGreen() {
        maze[this.x][this.y].paintGreen();
    }

    /**
     * Moves the current position one step to the West in the maze and updates the
     * previous direction.
     */
    public void moveLeft() {
        this.x = this.x - 1;
        this.facing = Direction.West;
    }

    /**
     * Moves the current position one step to the East in the maze and updates the
     * previous direction.
     */
    public void moveRight() {
        this.x = this.x + 1;
        this.facing = Direction.East;
    }

    /**
     * Moves the current position one step to the Norh in the maze and updates the
     * previous direction.
     */
    public void moveUp() {
        this.y = this.y - 1;
        this.facing = Direction.North;
    }

    /**
     * Moves the current position one step to the South in the maze and updates the
     * previous direction.
     */
    public void moveDown() {
        this.y = this.y + 1;
        this.facing = Direction.South;
    }

    /**
     * Calculates the next move when the algorithm is facing South.
     * 
     * @param hasTopWall    True if the current square has a top border.
     * @param hasRightWall  True if the current square has a right border.
     * @param hasBottomWall True if the current square has a bottom border.
     * @param hasLeftWall   True if the current square has a left border.
     */
    public void calculateMoveFacingSouth(boolean hasTopWall, boolean hasRightWall, boolean hasBottomWall,
            boolean hasLeftWall) {

        if (!hasLeftWall) {
            moveLeft();
        } else if (!hasBottomWall) {
            moveDown();
        } else if (!hasRightWall) {
            moveRight();
        } else {
            moveUp();
        }
    }

    /**
     * Calculates the next move when the algorithm is facing East.
     * 
     * @param hasTopWall    True if the current square has a top border.
     * @param hasRightWall  True if the current square has a right border.
     * @param hasBottomWall True if the current square has a bottom border.
     * @param hasLeftWall   True if the current square has a left border.
     */
    public void calculateMoveFacingEast(boolean hasTopWall, boolean hasRightWall, boolean hasBottomWall,
            boolean hasLeftWall) {

        if (!hasBottomWall) {
            moveDown();
        } else if (!hasRightWall) {
            moveRight();
        } else if (hasTopWall) {
            moveLeft();
        } else {
            moveUp();
        }
    }

    /**
     * Calculates the next move when the algorithm is facing West.
     * 
     * @param hasTopWall    True if the current square has a top border.
     * @param hasRightWall  True if the current square has a right border.
     * @param hasBottomWall True if the current square has a bottom border.
     * @param hasLeftWall   True if the current square has a left border.
     */
    public void calculateMoveFacingWest(boolean hasTopWall, boolean hasRightWall, boolean hasBottomWall,
            boolean hasLeftWall) {

        if (!hasTopWall) {
            moveUp();
        } else if (!hasLeftWall) {
            moveLeft();
        } else if (!hasBottomWall) {
            moveDown();
        } else {
            moveRight();
        }
    }

    /**
     * Calculates the next move when the algorithm is facing North.
     * 
     * @param hasTopWall    True if the current square has a top border.
     * @param hasRightWall  True if the current square has a right border.
     * @param hasBottomWall True if the current square has a bottom border.
     * @param hasLeftWall   True if the current square has a left border.
     */
    public void calculateMoveFacingNorth(boolean hasTopWall, boolean hasRightWall, boolean hasBottomWall,
            boolean hasLeftWall) {

        if (!hasRightWall) {
            moveRight();
        } else if (!hasTopWall) {
            moveUp();
        } else if (hasLeftWall) {
            moveDown();
        } else {
            moveLeft();
        }
    }

    /**
     * Calculates the coordinate that the algorithm will move next based on the
     * current direction and the walls that are around the current rectangle.
     */
    public void calculateNextMove() {
        Rect current = maze[x][y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        switch (this.facing) {
            case South:
                calculateMoveFacingSouth(hasTopWall, hasRightWall, hasBottomWall, hasLeftWall);
                break;
            case East:
                calculateMoveFacingEast(hasTopWall, hasRightWall, hasBottomWall, hasLeftWall);
                break;
            case North:
                calculateMoveFacingNorth(hasTopWall, hasRightWall, hasBottomWall, hasLeftWall);
                break;
            case West:
                calculateMoveFacingWest(hasTopWall, hasRightWall, hasBottomWall, hasLeftWall);
                break;
        }
    }
}

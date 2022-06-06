package mazesolver.domain;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mazesolver.enums.Direction;

/**
 * Tremaux's algorithm. Used for solving mazes
 */
public class Tremaux {
    private Rect[][] maze;
    private int[][] visited;
    private Direction previousDirection;
    private int x;
    private int y;

    public Tremaux(Rect[][] maze) {
        this.maze = maze;
        int size = maze.length;
        this.visited = new int[size][size];
        this.visited[0][0] = 1;
        this.previousDirection = Direction.East;
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisited(int[][] visited) {
        this.visited = visited;
    }

    public Direction getPreviousDirection() {
        return this.previousDirection;
    }

    public void setPreviousDirection(Direction direction) {
        this.previousDirection = direction;
    }

    /**
     * Turns around 180 degrees
     */
    public void turnAround() {
        switch (this.previousDirection) {
            case North:
                this.previousDirection = Direction.South;
                break;
            case East:
                this.previousDirection = Direction.West;
                break;
            case South:
                this.previousDirection = Direction.North;
                break;
            case West:
                this.previousDirection = Direction.East;
                break;
            default:
                break;
        }
    }

    /**
     * Checks if rectangle is a Junction or not.
     *
     * @param x X coordinate of the rectangle
     * @param y Y coordinate of the rectangle
     *
     * @return true if it is a Junction
     */
    public boolean isJunction(int x, int y) {
        Rect current = maze[x][y];
        int paths = 0;

        if (!current.getTopWall()) {
            paths++;
        }
        if (!current.getRightWall()) {
            paths++;
        }
        if (!current.getBottomWall()) {
            paths++;
        }
        if (!current.getLeftWall()) {
            paths++;
        }

        return paths > 2 ? true : false;
    }

    /**
     * Checks if there are valid paths forward to the current direction.
     *
     * @return true if there are no possible paths.
     */
    public boolean isDeadEnd() {
        Direction direction = this.previousDirection;
        Rect current = maze[this.x][this.y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        if (direction == Direction.North) {
            if (hasTopWall && hasLeftWall && hasRightWall) {
                return true;
            }
        }
        if (direction == Direction.East) {
            if (hasTopWall && hasBottomWall && hasRightWall) {
                return true;
            }
        }
        if (direction == Direction.South) {
            if (hasBottomWall && hasLeftWall && hasRightWall) {
                return true;
            }
        }
        if (direction == Direction.West) {
            if (hasTopWall && hasLeftWall && hasBottomWall) {
                return true;
            }
        }
        return false;
    }

    /**
     * Advances one step forward. This method is only used when the square is not a
     * junction.
     */
    public void advance() {
        Direction direction = this.previousDirection;
        Rect current = maze[this.x][this.y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        if (direction != Direction.East && !hasLeftWall) {
            moveLeft();
        } else if (direction != Direction.South && !hasTopWall) {
            moveUp();
        } else if (direction != Direction.North && !hasBottomWall) {
            moveDown();
        } else if (direction != Direction.West && !hasRightWall) {
            moveRight();
        }
    }

    /*
     * Increases the visited value of the coordinate by one.
     */
    public void markVisited() {
        visited[this.x][this.y]++;
    }

    /**
     * Moves one step left and updates previous direction.
     */
    public void moveLeft() {
        this.x = this.x - 1;
        this.previousDirection = Direction.West;
    }

    /**
     * Moves one step tright and updates previous direction.
     */
    public void moveRight() {
        this.x = this.x + 1;
        this.previousDirection = Direction.East;
    }

    /**
     * Moves one step up and updates previous direction.
     */
    public void moveUp() {
        this.y = this.y - 1;
        this.previousDirection = Direction.North;
    }

    /**
     * Moves one step down and updates previous direction.
     */
    public void moveDown() {
        this.y = this.y + 1;
        this.previousDirection = Direction.South;
    }

    /**
     * Checks if previous square has been visited 2 times or more.
     * 
     * @return returns true if square has been visited twice or more.
     */
    public boolean isPreviousVisitedTwice() {
        switch (this.previousDirection) {
            case North:
                return visited[this.x][this.y + 1] >= 2;
            case East:
                return visited[this.x - 1][this.y] >= 2;
            case South:
                return visited[this.x][this.y - 1] >= 2;
            case West:
                return visited[this.x + 1][this.y] >= 2;
            default:
                return false;
        }
    }

    /**
     * Trys to move forward to contiguous square that has been visited N many times.
     * 
     * @param timesVisited amount of visits.
     * 
     * @return true if move was successfull
     */
    public boolean tryMoveToTimesVisited(int timesVisited) {
        Rect current = maze[this.x][this.y];
        Direction direction = this.previousDirection;

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();
        Boolean hasBottomWall = current.getBottomWall();

        if (direction != Direction.North && !hasBottomWall && visited[this.x][this.y + 1] == timesVisited) {
            moveDown();
            return true;
        }
        if (direction != Direction.South && !hasTopWall && visited[this.x][this.y - 1] == timesVisited) {
            moveUp();
            return true;
        }
        if (direction != Direction.West && !hasRightWall && visited[this.x + 1][this.y] == timesVisited) {
            moveRight();
            return true;
        }
        if (direction != Direction.East && !hasLeftWall && visited[this.x - 1][this.y] == timesVisited) {
            moveLeft();
            return true;
        }
        return false;
    }

    /**
     * Trys to move to junction that is congiguous with current square.
     * 
     * @return returns true if move was successfull.
     */
    public boolean tryMoveToContiguousJunction() {
        Direction direction = this.previousDirection;

        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();
        Boolean hasBottomWall = current.getBottomWall();

        if (direction != Direction.South && !hasTopWall && isJunction(this.x, this.y - 1)) {
            moveUp();
            return true;
        }
        if (direction != Direction.West && !hasRightWall && isJunction(this.x + 1, this.y)) {
            moveRight();
            return true;
        }
        if (direction != Direction.East && !hasLeftWall && isJunction(this.x - 1, this.y)) {
            moveLeft();
            return true;
        }
        if (direction != Direction.North && !hasBottomWall && isJunction(this.x, this.y + 1)) {
            moveDown();
            return true;
        }
        return false;
    }

    /**
     * Moves one step forward.
     */
    public void calculateNextMove() {
        markVisited();
        this.maze[this.x][this.y].paint();

        if (isJunction(this.x, this.y)) {
            this.maze[this.x][this.y].paintGreen();
        }

        if (isDeadEnd()) {
            turnAround();
            return;
        }

        if (!isJunction(this.x, this.y)) {
            advance();
            return;
        }

        if (tryMoveToTimesVisited(0)) {
            return;
        }

        if (!isPreviousVisitedTwice()) {
            turnAround();
            return;
        }

        if (tryMoveToTimesVisited(1)) {
            return;
        }
        if (tryMoveToContiguousJunction()) {
            return;
        }

        turnAround();

        if (tryMoveToContiguousJunction()) {
            return;
        }

    }

    public void solve() {
        int moves = 3000;

        Timeline[] timelines = new Timeline[moves];
        maze[0][0].paint();
        int i = 0;
        while (i < moves) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1), event -> {
                        if (x < maze.length - 1 || y < maze.length - 1) {
                            calculateNextMove();
                        }
                    }));

            timelines[i] = timeline;

            i++;
        }
        SequentialTransition sequence = new SequentialTransition(timelines);
        sequence.play();
    }
}

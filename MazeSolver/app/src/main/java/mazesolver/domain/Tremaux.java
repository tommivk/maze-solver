package mazesolver.domain;

import mazesolver.enums.Direction;

/**
 * Tremaux's algorithm. Used for solving mazes
 */
public class Tremaux {
    /**
     * Two dimensional array of Rect objects that represents the maze.
     */
    private Rect[][] maze;
    /**
     * Two dimensional Integer array which values represents the amount of times
     * that the algorithm has visited the same index in the maze.
     */
    private int[][] visited;
    /**
     * The direction of the previous move.
     */
    private Direction previousDirection;
    /**
     * Current x coordinate in the maze.
     */
    private int x;
    /**
     * Current y coordinate in the maze.
     */
    private int y;

    public Tremaux(Rect[][] maze) {
        this.maze = maze;
        int size = maze.length;
        this.visited = new int[size][size];
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

    public int[][] getVisited() {
        return this.visited;
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

    public void paintRectangle() {
        maze[this.x][this.y].paint();
    }

    public void paintGreen() {
        maze[this.x][this.y].paintGreen();
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
        this.previousDirection = Direction.East;
        this.visited = new int[maze.length][maze.length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                maze[i][j].removeBackground();
            }
        }
    }

    /**
     * Turns around 180 degrees.
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
     * Advances one step forward straight towards the current direction.
     * 
     * @return returns true if the move was successfull
     */
    public boolean advance() {
        Direction direction = this.previousDirection;
        Rect current = maze[this.x][this.y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        if (direction == Direction.West && !hasLeftWall) {
            moveLeft();
            return true;
        } else if (direction == Direction.North && !hasTopWall) {
            moveUp();
            return true;
        } else if (direction == Direction.South && !hasBottomWall) {
            moveDown();
            return true;
        } else if (direction == Direction.East && !hasRightWall) {
            moveRight();
            return true;
        }
        return false;
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
     * Moves one step right and updates previous direction.
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
        Rect current = maze[this.x][this.y];

        boolean hasTopWall = current.getTopWall();
        boolean hasRightWall = current.getRightWall();
        boolean hasBottomWall = current.getBottomWall();
        boolean hasLeftWall = current.getLeftWall();

        switch (this.previousDirection) {
            case North:
                return !hasBottomWall ? visited[this.x][this.y + 1] >= 2 : true;
            case East:
                return !hasLeftWall ? visited[this.x - 1][this.y] >= 2 : true;
            case South:
                return !hasTopWall ? visited[this.x][this.y - 1] >= 2 : true;
            case West:
                return !hasRightWall ? visited[this.x + 1][this.y] >= 2 : true;
            default:
                return true;
        }
    }

    /**
     * Trys to move forward to contiguous square that has been visited N times.
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

        if (direction != Direction.North
                && !hasBottomWall
                && visited[this.x][this.y + 1] == timesVisited) {
            moveDown();
            return true;
        }
        if (direction != Direction.South
                && !hasTopWall
                && visited[this.x][this.y - 1] == timesVisited) {
            moveUp();
            return true;
        }
        if (direction != Direction.West
                && !hasRightWall
                && visited[this.x + 1][this.y] == timesVisited) {
            moveRight();
            return true;
        }
        if (direction != Direction.East
                && !hasLeftWall
                && visited[this.x - 1][this.y] == timesVisited) {
            moveLeft();
            return true;
        }
        return false;
    }

    /**
     * Trys to move to junction that is congiguous with current square and that has
     * been visited N times.
     * 
     * @param timesVisited amount of visits
     * @return returns true if move was successfull.
     */
    public boolean tryMoveToContiguousJunction(int timesVisited) {
        Direction direction = this.previousDirection;

        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();
        Boolean hasBottomWall = current.getBottomWall();

        if (direction != Direction.South && !hasTopWall && isJunction(this.x, this.y - 1)
                && visited[this.x][this.y - 1] == timesVisited) {
            moveUp();
            return true;
        }
        if (direction != Direction.West && !hasRightWall && isJunction(this.x + 1, this.y)
                && visited[this.x + 1][this.y] == timesVisited) {
            moveRight();
            return true;
        }
        if (direction != Direction.East && !hasLeftWall && isJunction(this.x - 1, this.y)
                && visited[this.x - 1][this.y] == timesVisited) {
            moveLeft();
            return true;
        }
        if (direction != Direction.North && !hasBottomWall && isJunction(this.x, this.y + 1)
                && visited[this.x][this.y + 1] == timesVisited) {
            moveDown();
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of visits of the least visited contiguous junctions.
     * 
     * @return amount if visits.
     */
    public int getLeastVisitedJunction() {
        Direction direction = this.previousDirection;

        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();
        Boolean hasBottomWall = current.getBottomWall();
        int smallest = Integer.MAX_VALUE;

        if (direction != Direction.South && !hasTopWall && isJunction(this.x, this.y - 1)) {
            if (visited[this.x][this.y - 1] < smallest) {
                smallest = visited[this.x][this.y - 1];
            }
        }
        if (direction != Direction.West && !hasRightWall && isJunction(this.x + 1, this.y)) {
            if (visited[this.x + 1][this.y] < smallest) {
                smallest = visited[this.x + 1][this.y];
            }
        }
        if (direction != Direction.East && !hasLeftWall && isJunction(this.x - 1, this.y)) {
            if (visited[this.x - 1][this.y] < smallest) {
                smallest = visited[this.x - 1][this.y];
            }
        }
        if (direction != Direction.North && !hasBottomWall && isJunction(this.x, this.y + 1)) {
            if (visited[this.x][this.y + 1] < smallest) {
                smallest = visited[this.x][this.y + 1];
            }
        }
        return smallest;
    }

    /**
     * Moves one step forward.
     */
    public void calculateNextMove() {
        markVisited();

        if (isDeadEnd()) {
            turnAround();
            markVisited();
        }

        if (tryMoveToTimesVisited(0)) {
            return;
        }

        if (!isPreviousVisitedTwice()) {
            turnAround();
            advance();
            return;
        }

        if (tryMoveToTimesVisited(1)) {
            return;
        }

        if (tryMoveToContiguousJunction(getLeastVisitedJunction())) {
            return;
        }

        turnAround();

        tryMoveToContiguousJunction(getLeastVisitedJunction());
    }

    public int solve() {
        int moves = 0;
        while (this.x != maze.length - 1 || this.y != maze.length - 1) {
            calculateNextMove();
            moves++;
        }

        return moves;
    }
}

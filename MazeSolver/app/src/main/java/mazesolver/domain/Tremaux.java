package mazesolver.domain;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mazesolver.enums.Direction;

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

    public void markVisited() {
        visited[this.x][this.y]++;
    }

    public void moveLeft() {
        this.x = this.x - 1;
        this.previousDirection = Direction.West;
    }

    public void moveRight() {
        this.x = this.x + 1;
        this.previousDirection = Direction.East;
    }

    public void moveUp() {
        this.y = this.y - 1;
        this.previousDirection = Direction.North;
    }

    public void moveDown() {
        this.y = this.y + 1;
        this.previousDirection = Direction.South;
    }

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

    public boolean tryMoveToTimesVisited(int timesVisited) {
        Direction direction = this.previousDirection;

        switch (direction) {
            case North:
                return tryMoveFromNorth(timesVisited);
            case East:
                return tryMoveFromEast(timesVisited);
            case South:
                return tryMoveFromSouth(timesVisited);
            case West:
                return tryMoveFromWest(timesVisited);
            default:
                return false;

        }
    }

    public boolean tryMoveFromNorth(int v) {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasTopWall && visited[this.x][this.y - 1] == v) {
            moveUp();
            return true;
        }
        if (!hasRightWall && visited[this.x + 1][this.y] == v) {
            moveRight();
            return true;
        }
        if (!hasLeftWall && visited[this.x - 1][this.y] == v) {
            moveLeft();
            return true;
        }
        return false;
    }

    public boolean tryMoveFromEast(int v) {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasRightWall = current.getRightWall();

        if (!hasBottomWall && visited[this.x][this.y + 1] == v) {
            moveDown();
            return true;
        }
        if (!hasTopWall && visited[this.x][this.y - 1] == v) {
            moveUp();
            return true;
        }
        if (!hasRightWall && visited[this.x + 1][this.y] == v) {
            moveRight();
            return true;
        }
        return false;
    }

    public boolean tryMoveFromSouth(int v) {
        Rect current = maze[this.x][this.y];

        Boolean hasRightWall = current.getRightWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasRightWall && visited[this.x + 1][this.y] == v) {
            moveRight();
            return true;
        }
        if (!hasLeftWall && visited[this.x - 1][this.y] == v) {
            moveLeft();
            return true;
        }
        if (!hasBottomWall && visited[this.x][this.y + 1] == v) {
            moveDown();
            return true;
        }
        return false;
    }

    public boolean tryMoveFromWest(int v) {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasTopWall && visited[this.x][this.y - 1] == v) {
            moveUp();
            return true;
        }

        if (!hasLeftWall && visited[this.x - 1][this.y] == v) {
            moveLeft();
            return true;
        }
        if (!hasBottomWall && visited[this.x][this.y + 1] == v) {
            moveDown();
            return true;
        }
        return false;
    }

    public boolean tryMoveToJunctionFromNorth() {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasRightWall = current.getRightWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasTopWall && isJunction(this.x, this.y - 1)) {
            moveUp();
            return true;
        }
        if (!hasRightWall && isJunction(this.x + 1, this.y)) {
            moveRight();
            return true;
        }
        if (!hasLeftWall && isJunction(this.x - 1, this.y)) {
            moveLeft();
            return true;
        }
        return false;
    }

    public boolean tryMoveToJunctionFromEast() {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasRightWall = current.getRightWall();

        if (!hasBottomWall && isJunction(this.x, this.y + 1)) {
            moveDown();
            return true;
        }
        if (!hasTopWall && isJunction(this.x, this.y - 1)) {
            moveUp();
            return true;
        }
        if (!hasRightWall && isJunction(this.x + 1, this.y)) {
            moveRight();
            return true;
        }
        return false;
    }

    public boolean tryMoveToJunctionFromSouth() {
        Rect current = maze[this.x][this.y];

        Boolean hasRightWall = current.getRightWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasRightWall && isJunction(this.x + 1, this.y)) {
            moveRight();
            return true;
        }
        if (!hasLeftWall && isJunction(this.x - 1, this.y)) {
            moveLeft();
            return true;
        }
        if (!hasBottomWall && isJunction(this.x, this.y + 1)) {
            moveDown();
            return true;
        }
        return false;
    }

    public boolean tryMoveToJunctionFromWest() {
        Rect current = maze[this.x][this.y];

        Boolean hasTopWall = current.getTopWall();
        Boolean hasBottomWall = current.getBottomWall();
        Boolean hasLeftWall = current.getLeftWall();

        if (!hasTopWall && isJunction(this.x, this.y - 1)) {
            moveUp();
            return true;
        }

        if (!hasLeftWall && isJunction(this.x - 1, this.y)) {
            moveLeft();
            return true;
        }
        if (!hasBottomWall && isJunction(this.x, this.y + 1)) {
            moveDown();
            return true;
        }
        return false;
    }

    public boolean tryMoveToContiguousJunction() {
        Direction direction = this.previousDirection;
        switch (direction) {
            case North:
                return tryMoveToJunctionFromNorth();
            case East:
                return tryMoveToJunctionFromEast();
            case South:
                return tryMoveToJunctionFromSouth();
            case West:
                return tryMoveToJunctionFromWest();
            default:
                return false;
        }

    }

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

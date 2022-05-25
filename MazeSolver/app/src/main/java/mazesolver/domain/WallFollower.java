package mazesolver.domain;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mazesolver.enums.Direction;

public class WallFollower {
    private int x;
    private int y;
    private Rect[][] maze;
    private Direction previousDirection;
    private int moves = 0;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getMoves() {
        return this.moves;
    }

    public Direction getPreviousDirection() {
        return this.previousDirection;
    }

    public WallFollower(Rect[][] maze) {
        this.x = 0;
        this.y = 0;
        this.maze = maze;
        this.previousDirection = Direction.South;
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
        for (int i = 0; i < maze.length; i++) {
            for (int k = 0; k < maze.length; k++) {
                maze[i][k].removeBackground();
            }
        }
    }

    public void animate(int moves, int duration) {
        Timeline[] timelines = new Timeline[moves];
        maze[0][0].paint();
        int i = 0;
        while (i < moves) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(duration), event -> {
                        calculateNextMove();
                        paintRectangle();
                    }));
            timelines[i] = timeline;
            i++;
        }
        SequentialTransition sequence = new SequentialTransition(timelines);
        sequence.play();
    }

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

    public void paintRectangle() {
        maze[this.x][this.y].paint();
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
        }

        else if (previousDirection == Direction.East) {
            if (!hasBottomWall) {
                moveDown();
            } else if (hasBottomWall && !hasRightWall) {
                moveRight();
            } else if (hasBottomWall && hasRightWall && hasTopWall) {
                moveLeft();
            } else if (hasBottomWall && hasRightWall && !hasTopWall) {
                moveUp();
            }
        }

        else if (previousDirection == Direction.North) {
            if (!hasRightWall) {
                moveRight();
            } else if (hasRightWall && !hasTopWall) {
                moveUp();
            } else if (hasTopWall && hasLeftWall && hasRightWall) {
                moveDown();
            } else if (hasRightWall && hasTopWall && !hasLeftWall) {
                moveLeft();
            }
        }

        else if (previousDirection == Direction.West) {
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

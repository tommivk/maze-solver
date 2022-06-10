package mazesolver.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import mazesolver.domain.AStar;
import mazesolver.domain.Edge;
import mazesolver.domain.Kruskal;
import mazesolver.domain.Rect;
import mazesolver.domain.Tremaux;
import mazesolver.domain.WallFollower;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MazeSolverUI extends Application {
    private Kruskal kruskal;
    private Rect[][] maze;
    private WallFollower wallFollower;
    private AStar aStar;
    private Tremaux tremaux;
    private int delay = 5;
    private SequentialTransition tremauxSequence = new SequentialTransition();
    private SequentialTransition wallFollowerSequence = new SequentialTransition();
    private SequentialTransition aStarSequence = new SequentialTransition();
    private SequentialTransition aStarResultSequence = new SequentialTransition();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maze Solver");
        stage.setHeight(1000);
        stage.setWidth(1500);
        int mazeSize = 30;

        Scene scene = getScene(stage, mazeSize);
        stage.setScene(scene);
        stage.show();

    }

    public void stopSequences() {
        tremauxSequence.stop();
        wallFollowerSequence.stop();
        aStarSequence.stop();
        aStarResultSequence.stop();
    }

    public Scene getScene(Stage stage, int size) {
        kruskal = new Kruskal();
        maze = kruskal.generateEdges(size, size);
        wallFollower = new WallFollower(maze);
        tremaux = new Tremaux(maze);
        aStar = new AStar(maze);

        VBox statistics = new VBox();

        Label title = new Label("Algorithm | Time taken in ms");
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        HBox tremauxStats = new HBox();
        Label tremauxLabel = new Label("Tremaux's: ");
        Label tremauxTime = new Label("");
        tremauxStats.getChildren().addAll(tremauxLabel, tremauxTime);

        HBox wallFollowerStats = new HBox();
        Label wallFollowerLabel = new Label("Wall Follower: ");
        Label wallFollowerTime = new Label(null);
        wallFollowerStats.getChildren().addAll(wallFollowerLabel, wallFollowerTime);

        HBox aStarStats = new HBox();
        Label aStarLabel = new Label("A*: ");
        Label aStarTime = new Label("");
        aStarStats.getChildren().addAll(aStarLabel, aStarTime);

        statistics.getChildren().addAll(title, tremauxStats, wallFollowerStats, aStarStats);

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            stopSequences();
            wallFollower.reset();

            long start = System.nanoTime();
            int moves = wallFollower.solve();
            long end = System.nanoTime();
            double timeTaken = (end - start) / 1000000.0;
            wallFollower.reset();

            Timeline[] timelines = new Timeline[moves];

            int i = 0;
            while (i < moves) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(this.delay), e -> {
                            wallFollower.paintRectangle();
                            wallFollower.calculateNextMove();
                            wallFollower.paintGreen();
                        }));
                timelines[i] = timeline;
                i++;
            }
            this.wallFollowerSequence = new SequentialTransition(timelines);

            wallFollowerSequence.setOnFinished(e -> {
                wallFollowerTime.setText(Double.toString(timeTaken));
            });

            wallFollowerSequence.play();

        });

        Button startAStar = new Button("A*");
        startAStar.setOnMouseClicked(event -> {
            stopSequences();
            aStar.reset();
            long start = System.nanoTime();
            aStar.solve();
            long end = System.nanoTime();

            double timeTaken = (end - start) / 1000000.0;
            aStarTime.setText(Double.toString(timeTaken));

            List<Rect> visited = aStar.getSequence();

            Timeline[] timelines = new Timeline[visited.size()];
            int i = 0;
            for (Rect rect : visited) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(this.delay), e -> {
                            rect.paint();
                        }));

                timelines[i] = timeline;
                i++;
            }
            this.aStarSequence = new SequentialTransition(timelines);
            this.aStarSequence.play();

            aStarSequence.setOnFinished(e -> {
                HashMap<Rect, Rect> parents = aStar.getParents();
                List<Rect> rects = new ArrayList<Rect>();
                Rect current = maze[maze.length - 1][maze.length - 1];
                rects.add(current);
                while (true) {
                    Rect rect = parents.get(current);
                    if (rect == null) {
                        break;
                    }
                    rects.add(rect);
                    current = rect;
                }

                Collections.reverse(rects);

                Timeline[] lines = new Timeline[rects.size()];
                int index = 0;
                for (Rect r : rects) {
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.millis(50), ev -> {
                                r.removeBackground();
                                r.paintGreen();
                            }));
                    lines[index] = timeline;
                    index++;
                }

                this.aStarResultSequence = new SequentialTransition(lines);
                this.aStarResultSequence.play();
            });
        });

        Button startTremaux = new Button("Tremaux's");
        startTremaux.setOnMouseClicked(event -> {
            stopSequences();
            tremaux.reset();

            long start = System.nanoTime();
            int moves = tremaux.solve();
            long end = System.nanoTime();
            double timeTaken = (end - start) / 1000000.0;
            tremaux.reset();

            Timeline[] timelines = new Timeline[moves];
            int i = 0;
            while (i < moves) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(this.delay), e -> {
                            tremaux.paintRectangle();
                            tremaux.calculateNextMove();
                            tremaux.paintGreen();
                        }));

                timelines[i] = timeline;

                i++;
            }
            this.tremauxSequence = new SequentialTransition(timelines);

            tremauxSequence.setOnFinished(e -> {
                tremauxTime.setText(Double.toString(timeTaken));
            });

            tremauxSequence.play();
        });

        Button restartButton = new Button("New maze");
        restartButton.setOnMouseClicked(event -> {
            tremauxSequence.stop();
            wallFollowerSequence.stop();
            Scene scene = getScene(stage, size);
            stage.setScene(scene);
            stage.show();
        });

        Button clearButton = new Button("Clear maze");
        clearButton.setOnMouseClicked(event -> {
            stopSequences();

            tremaux.reset();
            wallFollower.reset();
            aStar.reset();
        });

        VBox delayBox = new VBox();
        Label delayText = new Label("Delay in ms: ");
        TextField delayAmount = new TextField(Integer.toString(delay));
        delayBox.setMaxWidth(200);
        delayAmount.textProperty().addListener((_observable, _oldValue, newValue) -> {
            this.delay = Integer.parseInt(newValue);
        });
        delayBox.getChildren().addAll(delayText, delayAmount);

        VBox navigation = new VBox();

        Button newMazeButton = new Button("generate maze");
        newMazeButton.setOnMouseClicked(event -> {
            List<Edge> edges = kruskal.getEdges();
            Timeline[] timelines = new Timeline[edges.size()];
            for (int i = 0; i < edges.size(); i++) {
                final int index = i;
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(this.delay), e -> {
                            kruskal.kruskalStep(edges.get(index));
                        }));
                timelines[i] = timeline;
            }
            SequentialTransition sequence = new SequentialTransition(timelines);
            sequence.play();
            sequence.setOnFinished(e -> {
                navigation.getChildren().add(wfSolveButton);
                navigation.getChildren().add(startTremaux);
                navigation.getChildren().add(startAStar);
                navigation.getChildren().add(restartButton);
                navigation.getChildren().add(clearButton);
                navigation.getChildren().remove(newMazeButton);
            });
        });

        navigation.getChildren().add(newMazeButton);
        navigation.getChildren().add(delayBox);
        navigation.setPrefWidth(300);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_RIGHT);

        HBox mainContainer = new HBox();
        mainContainer.getChildren().addAll(navigation, pane, statistics);
        HBox.setMargin(statistics, new Insets(40, 20, 20, 20));
        HBox.setMargin(navigation, new Insets(40, 20, 20, 20));

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                pane.add(maze[i][k].getRectangle(), i, k);
            }
        }

        return new Scene(mainContainer);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

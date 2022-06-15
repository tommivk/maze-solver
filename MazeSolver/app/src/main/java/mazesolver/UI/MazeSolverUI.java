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
import mazesolver.domain.GrowingTree;
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
    private GrowingTree growingTree;
    private Rect[][] maze;
    private WallFollower wallFollower;
    private AStar aStar;
    private Tremaux tremaux;
    private int delay = 30;
    private int mazeGenerationDelay = 3;
    private int mazeSize = 30;
    private SequentialTransition tremauxSequence = new SequentialTransition();
    private SequentialTransition wallFollowerSequence = new SequentialTransition();
    private SequentialTransition aStarSequence = new SequentialTransition();
    private SequentialTransition aStarResultSequence = new SequentialTransition();
    private Stage stage;
    private Label aStarTime = new Label(null);
    private Label tremauxTime = new Label(null);
    private Label wallFollowerTime = new Label(null);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maze Solver");
        stage.setHeight(1000);
        stage.setWidth(1500);
        this.stage = stage;
        Scene scene = mazeGeneratorChoiceScene();
        stage.setScene(scene);
        stage.show();
    }

    public void stopSequences() {
        tremauxSequence.stop();
        wallFollowerSequence.stop();
        aStarSequence.stop();
        aStarResultSequence.stop();
    }

    public Scene mazeGeneratorChoiceScene() {
        VBox container = new VBox();
        HBox delayBox = new HBox();
        HBox mazeSelection = new HBox();
        Label delayLabel = new Label("Animation delay in ms: ");
        TextField delayField = new TextField(Integer.toString(this.mazeGenerationDelay));
        delayField.setMaxWidth(150);
        delayField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                this.mazeGenerationDelay = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
            }
        });
        delayBox.getChildren().addAll(delayLabel, delayField);
        Button chooseKruskal = new Button("Kruskal's");
        chooseKruskal.setOnMouseClicked(event -> {
            Scene scene = getKruskalScene();
            stage.setScene(scene);
            stage.show();

        });

        Button chooseGrowingTree = new Button("Growing tree");
        chooseGrowingTree.setOnMouseClicked(event -> {
            Scene scene = getGrowingTreeScene();
            stage.setScene(scene);
            stage.show();
        });

        mazeSelection.setAlignment(Pos.CENTER);
        delayBox.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        mazeSelection.getChildren().addAll(chooseKruskal, chooseGrowingTree);
        HBox.setMargin(chooseKruskal, new Insets(0, 10, 0, 0));
        VBox.setMargin(delayBox, new Insets(30, 0, 0, 0));
        container.getChildren().addAll(mazeSelection, delayBox);
        return new Scene(container);
    }

    public Scene getGrowingTreeScene() {
        this.growingTree = new GrowingTree(mazeSize);
        this.maze = growingTree.generate();

        this.wallFollower = new WallFollower(maze);
        this.tremaux = new Tremaux(maze);
        this.aStar = new AStar(maze);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        for (int i = 0; i < mazeSize; i++) {
            for (int k = 0; k < mazeSize; k++) {
                pane.add(maze[i][k].getRectangle(), i, k);
            }
        }
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        VBox controls = getControlButtons();
        VBox statistics = getStatistics();
        container.getChildren().addAll(controls, pane, statistics);
        HBox.setMargin(statistics, new Insets(40, 20, 20, 20));
        HBox.setMargin(controls, new Insets(40, 20, 20, 20));
        return new Scene(container);
    }

    public Scene getKruskalScene() {
        this.kruskal = new Kruskal();
        this.maze = kruskal.generateEdges(mazeSize, mazeSize);
        this.wallFollower = new WallFollower(maze);
        this.tremaux = new Tremaux(maze);
        this.aStar = new AStar(maze);
        List<Edge> edges = kruskal.getEdges();
        Timeline[] timelines = new Timeline[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            final int index = i;
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(this.mazeGenerationDelay), e -> {
                        kruskal.kruskalStep(edges.get(index));
                    }));
            timelines[i] = timeline;
        }
        SequentialTransition sequence = new SequentialTransition(timelines);
        sequence.play();
        sequence.setOnFinished(e -> {
            Scene scene = getMainScene();
            stage.setScene(scene);
            stage.show();
        });

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        for (int i = 0; i < mazeSize; i++) {
            for (int k = 0; k < mazeSize; k++) {
                pane.add(maze[i][k].getRectangle(), i, k);
            }
        }

        return new Scene(pane);

    }

    public VBox getControlButtons() {
        VBox navigation = new VBox();

        HBox delayBox = new HBox();
        Label delayLabel = new Label("Delay in ms: ");
        TextField delayField = new TextField(Integer.toString(this.delay));
        delayField.setMaxWidth(150);
        delayField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                this.delay = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
            }
        });
        delayBox.getChildren().addAll(delayLabel, delayField);

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            stopSequences();
            wallFollower.reset();

            long start = System.nanoTime();
            int moves = wallFollower.solve();
            long end = System.nanoTime();
            double timeTaken = (end - start) / 1000000.0;
            this.wallFollowerTime.setText(Double.toString(timeTaken));
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
            this.aStarTime.setText(Double.toString(timeTaken));

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
            this.tremauxTime.setText(Double.toString(timeTaken));

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

            tremauxSequence.play();
        });

        Button restartButton = new Button("New maze");
        restartButton.setOnMouseClicked(event -> {
            tremauxSequence.stop();
            wallFollowerSequence.stop();
            this.aStarTime.setText(null);
            this.tremauxTime.setText(null);
            this.wallFollowerTime.setText(null);
            Scene scene = mazeGeneratorChoiceScene();
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
        navigation.getChildren().add(delayBox);
        navigation.getChildren().add(wfSolveButton);
        navigation.getChildren().add(startTremaux);
        navigation.getChildren().add(startAStar);
        navigation.getChildren().add(restartButton);
        navigation.getChildren().add(clearButton);
        return navigation;

    }

    public Scene getMainScene() {
        VBox statistics = getStatistics();
        VBox navigation = getControlButtons();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        for (int i = 0; i < mazeSize; i++) {
            for (int k = 0; k < mazeSize; k++) {
                pane.add(maze[i][k].getRectangle(), i, k);
            }
        }

        HBox container = new HBox();
        HBox.setMargin(statistics, new Insets(40, 20, 20, 20));
        HBox.setMargin(navigation, new Insets(40, 20, 20, 20));
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(navigation, pane, statistics);
        return new Scene(container);
    }

    public VBox getStatistics() {

        VBox statistics = new VBox();

        Label title = new Label("Algorithm | Time taken in ms");
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        HBox tremauxStats = new HBox();
        Label tremauxLabel = new Label("Tremaux's: ");
        tremauxStats.getChildren().addAll(tremauxLabel, tremauxTime);

        HBox wallFollowerStats = new HBox();
        Label wallFollowerLabel = new Label("Wall Follower: ");

        wallFollowerStats.getChildren().addAll(wallFollowerLabel, wallFollowerTime);

        HBox aStarStats = new HBox();
        Label aStarLabel = new Label("A*: ");
        aStarStats.getChildren().addAll(aStarLabel, aStarTime);

        statistics.getChildren().addAll(title, tremauxStats, wallFollowerStats, aStarStats);

        return statistics;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

package mazesolver.UI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private Tremaux tremaux;
    private int delay = 5;
    private SequentialTransition tremauxSequence = new SequentialTransition();
    private SequentialTransition wallFollowerSequence = new SequentialTransition();

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = getScene(stage);
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene(Stage stage) {
        kruskal = new Kruskal();
        int size = 30;
        maze = kruskal.generateEdges(size, size);
        wallFollower = new WallFollower(maze);
        tremaux = new Tremaux(maze);

        stage.setTitle("Maze Solver");
        stage.setHeight(1200);
        stage.setWidth(1800);

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            tremauxSequence.stop();
            int moves = wallFollower.solve();
            wallFollower.reset();
            Timeline[] timelines = new Timeline[moves];
            maze[0][0].paint();

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

        Button startTremaux = new Button("Tremaux's");
        startTremaux.setOnMouseClicked(event -> {
            wallFollowerSequence.stop();

            int moves = 0;
            for (int i = 0; i < 20; i++) {
                long startTime = System.nanoTime();
                moves = tremaux.solve();
                long endTime = System.nanoTime();
                long time = endTime - startTime;
                long result = TimeUnit.NANOSECONDS.toMillis(time);
                System.out.println(time);
                System.out.println(result);
                tremaux.reset();
            }

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
            Scene scene = getScene(stage);
            stage.setScene(scene);
            stage.show();
        });

        Button clearButton = new Button("Clear maze");
        clearButton.setOnMouseClicked(event -> {
            tremaux.reset();
            wallFollower.reset();
            tremauxSequence.stop();
            wallFollowerSequence.stop();
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
                navigation.getChildren().add(restartButton);
                navigation.getChildren().add(clearButton);
            });
        });

        navigation.getChildren().add(newMazeButton);
        navigation.getChildren().add(delayBox);
        navigation.setPrefWidth(300);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_RIGHT);

        HBox mainContainer = new HBox();
        mainContainer.getChildren().addAll(navigation, pane);

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

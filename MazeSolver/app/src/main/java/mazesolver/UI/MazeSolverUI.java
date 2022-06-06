package mazesolver.UI;

import java.util.List;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MazeSolverUI extends Application {
    private Kruskal kruskal;
    private Rect[][] maze;
    private WallFollower wallFollower;
    private Tremaux tremaux;

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
        stage.setWidth(1400);

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            int moves = wallFollower.solve();
            wallFollower.reset();
            Timeline[] timelines = new Timeline[moves];
            maze[0][0].paint();
            int i = 0;
            while (i < moves) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(30), e -> {
                            wallFollower.paintRectangle();
                            wallFollower.calculateNextMove();
                            wallFollower.paintGreen();
                        }));
                timelines[i] = timeline;
                i++;
            }
            SequentialTransition sequence = new SequentialTransition(timelines);
            sequence.play();

        });

        Button startTremaux = new Button("Tremaux's");
        startTremaux.setOnMouseClicked(event -> {
            int moves = tremaux.solve();

            Timeline[] timelines = new Timeline[moves];
            int i = 0;
            while (i < moves) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(70), e -> {
                            tremaux.paintRectangle();
                            tremaux.calculateNextMove();
                            tremaux.paintGreen();
                        }));

                timelines[i] = timeline;

                i++;
            }
            SequentialTransition sequence = new SequentialTransition(timelines);
            sequence.play();
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(event -> {
            Scene scene = getScene(stage);
            stage.setScene(scene);
            stage.show();
        });

        VBox navigation = new VBox();

        Button newMazeButton = new Button("generate maze");
        newMazeButton.setOnMouseClicked(event -> {
            List<Edge> edges = kruskal.getEdges();
            Timeline[] timelines = new Timeline[edges.size()];
            for (int i = 0; i < edges.size(); i++) {
                final int index = i;
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(2), e -> {
                            kruskal.kruskalStep(edges.get(index));
                        }));
                timelines[i] = timeline;
            }
            SequentialTransition sequence = new SequentialTransition(timelines);
            sequence.play();
            sequence.setOnFinished(e -> {
                navigation.getChildren().add(wfSolveButton);
                navigation.getChildren().add(startTremaux);
                navigation.getChildren().add(clearButton);
            });
        });

        navigation.getChildren().add(newMazeButton);
        navigation.setPrefWidth(200);

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

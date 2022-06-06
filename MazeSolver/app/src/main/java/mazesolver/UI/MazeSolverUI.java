package mazesolver.UI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
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

        Button newMazeButton = new Button("generate maze");
        newMazeButton.setOnMouseClicked(event -> {
            kruskal.generateMaze(false, 0);
        });

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            wallFollower.solve();
            wallFollower.animate(wallFollower.getMoves(), 30);
        });

        Button startTremaux = new Button("Tremaux's");
        startTremaux.setOnMouseClicked(event -> {
            tremaux.solve();
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(event -> {
            Scene scene = getScene(stage);
            stage.setScene(scene);
            stage.show();
        });

        VBox navigation = new VBox();
        navigation.getChildren().add(newMazeButton);
        navigation.getChildren().add(wfSolveButton);
        navigation.getChildren().add(startTremaux);
        navigation.getChildren().add(clearButton);
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

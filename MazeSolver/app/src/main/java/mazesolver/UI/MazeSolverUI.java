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

    @Override
    public void start(Stage stage) throws Exception {
        Kruskal kruskal = new Kruskal();
        int size = 30;
        Rect[][] maze = kruskal.generateEdges(size, size);
        WallFollower wf = new WallFollower(maze);
        Tremaux tremaux = new Tremaux(maze);

        stage.setTitle("Maze Solver");
        stage.setHeight(1200);
        stage.setWidth(1400);

        Button newMazeButton = new Button("generate maze");
        newMazeButton.setOnMouseClicked(event -> {
            kruskal.generateMaze(true, 4);
        });

        Button wfSolveButton = new Button("Wall follower");
        wfSolveButton.setOnMouseClicked(event -> {
            wf.solve();
            wf.animate(wf.getMoves(), 30);
        });

        Button startTremaux = new Button("Tremaux's");
        startTremaux.setOnMouseClicked(event -> {
            tremaux.solve();
        });

        VBox navigation = new VBox();
        navigation.getChildren().add(newMazeButton);
        navigation.getChildren().add(wfSolveButton);
        navigation.getChildren().add(startTremaux);
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

        Scene scene = new Scene(mainContainer);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

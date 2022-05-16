package mazesolver.UI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class MazeSolverUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maze Solver");
        stage.setHeight(700);
        stage.setWidth(1000);
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

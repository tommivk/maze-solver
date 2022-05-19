package mazesolver.UI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import mazesolver.domain.Kruskal;
import mazesolver.domain.Rect;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class MazeSolverUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maze Solver");
        stage.setHeight(1200);
        stage.setWidth(1200);
        GridPane pane = new GridPane();

        Kruskal kruskal = new Kruskal();
        int size = 30;
        Rect[][] rects = kruskal.generateEdges(size, size);
        kruskal.generateMaze();
        pane.setAlignment(Pos.CENTER);
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                pane.add(rects[i][k].getRectangle(), i, k);
            }
        }

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

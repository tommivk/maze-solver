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
        stage.setHeight(1000);
        stage.setWidth(1000);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Kruskal kruskal = new Kruskal();
        int size = 30;
        Rect[][] rects = kruskal.generateEdges(size, size);
        kruskal.generateMaze(true, 4);

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

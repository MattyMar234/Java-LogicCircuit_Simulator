package Java.Main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class ApplicationPageController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane windowBack;

    @FXML
    private AnchorPane sideBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);

        
    }

}

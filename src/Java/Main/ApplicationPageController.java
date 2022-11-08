package Java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Component.SimulationObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import Java.Component.*;

public class ApplicationPageController implements Initializable {

    @FXML private Canvas canvas;
    @FXML private AnchorPane windowBack;
    @FXML private AnchorPane sideBar;

    private App main;
    private ThreadWireNet Thread1;
    protected ArrayList<Wire> WiresList = new ArrayList<Wire>();
    protected ArrayList<SimulationObject> deviceList = new ArrayList<>();


    public ApplicationPageController(App main) {
       this.main = main;
       this.main.ControllerReference = this;
    }

    public ApplicationPageController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.WiresList.add(new Wire());
        this.Thread1 = new ThreadWireNet(this);
        this.Thread1.start();
    }


    public Canvas getCanvas() {
        return this.canvas;
    }

}

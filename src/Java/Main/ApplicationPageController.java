package Java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Component.Wire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;


public class ApplicationPageController implements Initializable 
{
    private static final double MaxFPS = 120;


    @FXML private Canvas canvas;
    @FXML private AnchorPane windowBack;
    @FXML private AnchorPane sideBar;
    @FXML private StackPane canvasPane;

    @FXML private Button EEPROM_bt;


    private Timeline timeline = new Timeline();
    private Timeline FrameTimeLine = new Timeline();
    private double FrameDelay;
    private int frameCount = 0;
    private App main;

    private double gridScale = 1.0;
    private double gridOffsetX = 0.0;
    private double gridOffsetY = 0.0;
    private double gridWidth = 0;
    private double gridHeight = 0;

    private boolean gridDragged = false;
    private double StartGridDraggeX = 0;
    private double StartGridDraggeY = 0;

    private ArrayList <SimulationObject> SimulationComponent = new ArrayList<>();
    private Scene scene;


    /*private ThreadWireNet Thread1;
    protected ArrayList<Wire> WiresList = new ArrayList<Wire>();
    protected ArrayList<SimulationObject> deviceList = new ArrayList<>();*/

    

    public ApplicationPageController(App main) 
    {
       this.main = main;
       this.main.ControllerReference = this;
       this.SimulationComponent.add(new Wire());
       this.scene = main.AttualScene;
    }

    public ApplicationPageController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());

        FrameDelay = (1 / ApplicationPageController.MaxFPS) * 1000;

        

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(FrameDelay), this::onTimerTick));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        FrameTimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(1000), this::FramesTick));
        FrameTimeLine.setCycleCount(Timeline.INDEFINITE);
        FrameTimeLine.play();

        gridWidth = canvas.getWidth();
        gridHeight = canvas.getHeight();


    }

    private void FramesTick(ActionEvent event) {
        System.out.println("[FPS]: " + frameCount);
        frameCount = 0;
    }

    private void onTimerTick(ActionEvent event) 
    {
        frameCount++;
        Render();
        

    }

    private void Render() 
    {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double Width = canvas.getWidth();
        double Height = canvas.getHeight();
        double lineOffsetX =  (gridOffsetX % (20 * gridScale));
        double lineOffsetY =  (gridOffsetY % (20 * gridScale));

        
        g.clearRect(0, 0, Width, Height);
        
        g.setFill(Color.GRAY);
        g.setStroke(Color.GRAY);

        g.setLineWidth(4);
        g.strokeLine(0, 0, Width, 0);
        g.strokeLine(0, 0, 0, Height);
        g.strokeLine(0, Height, Width, Height);
        g.strokeLine(Width, 0, Width, Height);
        

        g.setLineWidth(0.5);




        for(double y = 0; y <= Height - lineOffsetY; y += (20 * gridScale)) {
            g.strokeLine(0, y + lineOffsetY, Width - lineOffsetX, y + lineOffsetY);
        }

        for(double x = 0; x <= Width - lineOffsetX; x += (20 * gridScale)) {
            g.strokeLine(x + lineOffsetX, 0, x + lineOffsetX, Height);
        }

        for (SimulationObject obj : SimulationComponent) {
            obj.Draw(g);
        }

        g.setFill(Color.RED);
        g.fillOval(Width/2 + gridOffsetX, Height/2 + gridOffsetY, 20 * gridScale, 20 * gridScale);

    }

    @FXML
    void GridZoom(ScrollEvent event) 
    {
        double Px = event.getDeltaX();
        double Py = event.getDeltaY();
        double gridWidth = canvas.getWidth();
        double gridHeight = canvas.getHeight();

        double CenterDx = (gridWidth/2)  - Px;
        double CenterDy = (gridHeight/2) - Py;

        System.out.println(CenterDx);


        if(event.getDeltaY() < 0) {
            if(gridScale > 0.50) {

                gridScale = gridScale / 2;
                gridOffsetX = gridOffsetX / 2;
                gridOffsetY = gridOffsetY / 2;

                
            }
        }
        else {
            if(gridScale <= 100) {


                gridScale = gridScale * 2;
                gridOffsetX = gridOffsetX * 2;
                gridOffsetY = gridOffsetY * 2;
            }
        }

        gridWidth = canvas.getWidth()   / gridScale;
        gridHeight = canvas.getHeight() / gridScale;

        


        System.out.println("scale: " + (25 * gridScale));
        System.out.println("gridSize: " + gridWidth + ", " + gridHeight);
    }


    @FXML
    void OnMousePressed(MouseEvent event) 
    {  
        if(event.getButton() == MouseButton.PRIMARY) {

        }
        else if(event.getButton() == MouseButton.SECONDARY) {

        }
        else if(event.getButton() == MouseButton.MIDDLE) {
            gridDragged = true;
            StartGridDraggeX = event.getX() - gridOffsetX;
            StartGridDraggeY = event.getY() - gridOffsetY;
        }   
    }

    @FXML
    void OnMouseReleased(MouseEvent event) 
    {
        if(event.getButton() == MouseButton.PRIMARY) {

        }
        else if(event.getButton() == MouseButton.SECONDARY) {

        }
        else if(event.getButton() == MouseButton.MIDDLE) {
            gridDragged = false;
        }   
    }


    @FXML
    void OnDragged(MouseEvent event) {
        
        if(gridDragged) {
            gridOffsetX = (event.getX() - StartGridDraggeX);
            gridOffsetY = (event.getY() - StartGridDraggeY);
        }
    }
}

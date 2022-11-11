package Java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Component.Wire;
import Java.Memory.AT28c512;
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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;


public class ApplicationPageController implements Initializable 
{
    private static final double MaxFPS = 20;


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

    private double gridOffsetX = 0.0;
    private double gridOffsetY = 0.0;
    private double gridOffsetLastX = 0.0;
    private double gridOffsetLastY = 0.0;
    private double gridWidth = 0;
    private double gridHeight = 0;
    double BigSquareScaled;
    double SmallSquareScaled;
    double lineOffsetX;
    double lineOffsetY;

    private boolean gridDragged = false;
    private double StartGridDraggeX = 0;
    private double StartGridDraggeY = 0;

    private boolean MOVE_UP = false;
    private boolean MOVE_DOWN = false;
    private boolean MOVE_LEFT = false;
    private boolean MOVE_RIGHT = false;
    private boolean ZOOM   = false;
    private boolean DEZOOM = false;

    private ArrayList <SimulationObject> SimulationComponent = new ArrayList<>();
    private Scene scene;


    /*private ThreadWireNet Thread1;
    protected ArrayList<Wire> WiresList = new ArrayList<Wire>();
    protected ArrayList<SimulationObject> deviceList = new ArrayList<>();*/

    /*=========== PARAMETRI GLOBALI ============*/
    public static class SimulationParametre {
        public static double scaleValue = 1.0;
        public static double baseSquareSize = 20;
        public static double BigSquare = 100;
        public static int fontBaseSize = 20;
        public static Canvas canvas;
    }


    

    public ApplicationPageController(App main) 
    {
       this.main = main;
       this.main.ControllerReference = this;
       this.SimulationComponent.add(new AT28c512(0,0, ""));
       this.scene = main.AttualScene;
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

        SimulationParametre.canvas = this.canvas;

        ZoomOperations(0);
    }

    private void FramesTick(ActionEvent event) {
        this.main.stage.setTitle("Circuit Simulator  " + frameCount + " FPS");
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

        
        g.clearRect(0, 0, Width, Height);
        
        g.setFill(Color.GRAY);
        g.setStroke(Color.GRAY);

        g.setLineWidth(4);
        g.strokeLine(0, 0, Width, 0);
        g.strokeLine(0, 0, 0, Height);
        g.strokeLine(0, Height, Width, Height);
        g.strokeLine(Width, 0, Width, Height);


        for(double y = -BigSquareScaled; y <= Height + BigSquareScaled; y += SmallSquareScaled)   {
            if(((y) % 100) == 0)
                g.setLineWidth(1);
            else
                g.setLineWidth(0.5);

            g.strokeLine(0, y + lineOffsetY, Width, y + lineOffsetY);
        }

        for(double x = -BigSquareScaled; x <= Width + BigSquareScaled; x += SmallSquareScaled) {
            if(x % 100 == 0)
                g.setLineWidth(1);
            else
                g.setLineWidth(0.5);

            g.strokeLine(x + lineOffsetX, 0, x + lineOffsetX, Height);
        }

        for (SimulationObject obj : SimulationComponent) {
            obj.Draw(g);
        }

        g.setFill(Color.RED);
        g.fillOval(500 + gridOffsetX, 500 + gridOffsetY, 20 * SimulationParametre.scaleValue, 20 * SimulationParametre.scaleValue);

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
        double DeltaY = event.getDeltaY();

        ZoomOperations(DeltaY);
    }

    private void ZoomOperations(double DeltaY)
    {
        if(DeltaY < 0 && SimulationParametre.scaleValue > 1){
            SimulationParametre.scaleValue = SimulationParametre.scaleValue / 2;
            gridOffsetX = gridOffsetX / 2;
            gridOffsetY = gridOffsetY / 2;

            for (SimulationObject comp : SimulationComponent) {
                comp.setX(comp.getX()/2);
                comp.setY(comp.getY()/2);
                comp.Rescale();
            }

            
        }
        else if(DeltaY > 0 && SimulationParametre.scaleValue < 4) {
            SimulationParametre.scaleValue = SimulationParametre.scaleValue * 2;
            gridOffsetX = gridOffsetX * 2;
            gridOffsetY = gridOffsetY * 2;

            for (SimulationObject comp : SimulationComponent) {
                comp.setX(comp.getX()*2);
                comp.setY(comp.getY()*2);
                comp.Rescale();
            }
        }

        System.out.println("scale: " + SimulationParametre.scaleValue);

        BigSquareScaled = SimulationParametre.BigSquare*SimulationParametre.scaleValue;
        SmallSquareScaled = SimulationParametre.baseSquareSize*SimulationParametre.scaleValue;
        lineOffsetX =  ((gridOffsetX/SimulationParametre.scaleValue) % (BigSquareScaled));
        lineOffsetY =  ((gridOffsetY/SimulationParametre.scaleValue) % (BigSquareScaled));


        gridWidth  = canvas.getWidth()  / SimulationParametre.scaleValue;
        gridHeight = canvas.getHeight() / SimulationParametre.scaleValue;


        //System.out.println("offsetX=" + gridOffsetX + ", offsetY=" + gridOffsetY);
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

            gridOffsetLastX = gridOffsetX;
            gridOffsetLastY = gridOffsetY;

            gridOffsetX = (event.getX() - StartGridDraggeX);
            gridOffsetY = (event.getY() - StartGridDraggeY);

            double DX = gridOffsetX - gridOffsetLastX;
            double DY = gridOffsetY - gridOffsetLastY;

            for (SimulationObject obj : SimulationComponent) {
                obj.Traslate(DX, DY);
            }
        }
    }



    @FXML
    void AddEEProm(ActionEvent event) {

    }


    @FXML
    void OnKeyPressed(KeyEvent event)
    {
        if(event.getCode() == KeyCode.A) {
            MOVE_LEFT  = true;
        }
        else if(event.getCode() == KeyCode.D) {
            MOVE_RIGHT = true;
        }
        else if(event.getCode() == KeyCode.W) {
            MOVE_UP    = true;
        }
        else if(event.getCode() == KeyCode.S) {
            MOVE_DOWN  = true;
        }
        else if(event.getCode() == KeyCode.Q) {
            DEZOOM  = true;
        }
        else if(event.getCode() == KeyCode.E) {
            ZOOM  = true;
        }
    }

    @FXML
    void OnKeyReleased(KeyEvent event)
    {
        if(event.getCode() == KeyCode.A) {
            MOVE_LEFT  = false;
        }
        else if(event.getCode() == KeyCode.D) {
            MOVE_RIGHT = false;
        }
        else if(event.getCode() == KeyCode.W) {
            MOVE_UP    = false;
        }
        else if(event.getCode() == KeyCode.S) {
            MOVE_DOWN  = false;
        }
        else if(event.getCode() == KeyCode.Q) {
            DEZOOM  = false;
        }
        else if(event.getCode() == KeyCode.E) {
            ZOOM  = false;
        }
    }

    @FXML
    void onKeyActive(KeyEvent event) {


        if(MOVE_LEFT) {
            gridOffsetX += 5;
            for (SimulationObject obj : SimulationComponent) {
                obj.Traslate(5, 0);
            }
        }
        if(MOVE_RIGHT) {
            gridOffsetX -= 5;
            for (SimulationObject obj : SimulationComponent) {
                obj.Traslate(-5, 0);
            }
        }
        if(MOVE_UP) {
            gridOffsetY += 5;
            for (SimulationObject obj : SimulationComponent) {
                obj.Traslate(0, 5);
            }
        }
        if(MOVE_DOWN) {
            gridOffsetY -= 5;
            for (SimulationObject obj : SimulationComponent) {
                obj.Traslate(0, -5);
            }
        }
        if(DEZOOM) {
            ZoomOperations(40);
        }
        if(ZOOM) {
            ZoomOperations(-40);
        }
    }
}

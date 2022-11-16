package Java.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Component.IntegratedCircuit;
import Java.Component.Point;
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
import javafx.util.Duration;


public class ApplicationPageController extends Camera implements Initializable 
{
    private static final double MaxFPS = 20;

    public enum UserMode {

        WIRING,
        PLACING_COMPONENT,
        SELECTION

    }

    public enum UserOperation {

        NONE,
        START_WRIRING,
        DEPLOY_COMPONENT

    }

    public static UserMode userMode = UserMode.WIRING;
    public static UserOperation userOperation = UserOperation.NONE;


    @FXML private Canvas canvas;
    @FXML private AnchorPane windowBack;
    @FXML private AnchorPane sideBar;
    @FXML private StackPane canvasPane;
   
    @FXML private Button WiringBt;
    @FXML private Button EEPROM_bt;


    private Timeline timeline = new Timeline();
    private Timeline FrameTimeLine = new Timeline();
    private double FrameDelay;
    private int frameCount = 0;
    private App main;

   
    private double gridWidth = 0;
    private double gridHeight = 0;
 

    private Scene scene;


    /*private ThreadWireNet Thread1;
    protected ArrayList<Wire> WiresList = new ArrayList<Wire>();
    protected ArrayList<SimulationObject> deviceList = new ArrayList<>();*/

    /*=========== PARAMETRI GLOBALI ============*/
    public static class SimulationGlobalParametre 
    {
        
        public static double SmallGridSquare = 20;
        public static double BigSquare = 100;
        public static int fontBaseSize = 20;
        public static Canvas canvas;

        public static double MouseX;
        public static double MouseY;
        
    }


    

    public ApplicationPageController(App main) 
    {
       super();
       
       this.main = main;
       this.main.ControllerReference = this;
       this.RenderedObjectList.add(new AT28c512(0,0, ""));
       this.RenderedObjectList.add(new AT28c512(200,200, ""));
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

        SimulationGlobalParametre.canvas = this.canvas;
        super.setCanvas(canvas);

        
    }

    private void FramesTick(ActionEvent event) {
        this.main.stage.setTitle("Circuit Simulator  " + frameCount + " FPS");
        frameCount = 0;
    }

    private void onTimerTick(ActionEvent event)  {
        frameCount++;
        Render();
    }

    private void Render() 
    {
        GraphicsContext g = canvas.getGraphicsContext2D();
        super.update();

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawGrid(g);




        for (SimulationObject obj : RenderedObjectList) {
            obj.Draw(g, this);
        }

        for (Wire obj : wires) {
            obj.Draw(g, this);
        }

        if(ApplicationPageController.userOperation == UserOperation.START_WRIRING) {
            Point A = WorldToScreen(selectedPin.getCenetr());

            g.setStroke(Color.GREEN);
            g.setLineWidth(3);

            //g.strokeLine(A.X, A.Y, fMouseX + (fMouseX%20 >= 10 ? 20 : 0 - fMouseX%20), fMouseY + (fMouseY%20 >= 10 ? 20 : 0 - fMouseY%20));
            g.strokeLine(A.X, A.Y, fMouseX, fMouseY);
            System.out.println("Angle: " + CalcRotationAngleInDegrees(A, new Point(fMouseX, fMouseY)));

            //TAN
        }

        if(ApplicationPageController.userOperation == UserOperation.DEPLOY_COMPONENT) {
            if(movingObject instanceof IntegratedCircuit) {
                ((IntegratedCircuit) movingObject).MoveTo(ScreenToWorld_X(fMouseX), ScreenToWorld_Y(fMouseY));
            }
        }
        DrawBorder(g);
    }

    private double CalcRotationAngleInDegrees(Point pinTarget, Point target) {
        double theta = Math.atan2(target.Y - pinTarget.Y, target.X - pinTarget.X);
        theta += Math.PI/ 2.0f;

        double angle = Math.toDegrees(theta);

        if(angle < 0) angle += 360;

        return angle;
    }


    @FXML
    void AddEEProm(ActionEvent event) {
        ApplicationPageController.userMode = UserMode.PLACING_COMPONENT;
        ApplicationPageController.userOperation = UserOperation.DEPLOY_COMPONENT;

        AT28c512 at = new AT28c512(fMouseX, fMouseY, "");
        RenderedObjectList.add(at);
        movingObject = at;
    }


    @FXML
    void setWiringMode(MouseEvent event) {
        ApplicationPageController.userMode = UserMode.WIRING;
    }


    private void DrawBorder(GraphicsContext g) 
    {
        double Width = canvas.getWidth();
        double Height = canvas.getHeight();

        g.setLineWidth(4);
        g.strokeLine(0, 0, Width, 0);
        g.strokeLine(0, 0, 0, Height);
        g.strokeLine(0, Height, Width, Height);
        g.strokeLine(Width, 0, Width, Height);
    }


    private void DrawGrid(GraphicsContext g) 
    {
        double startGridX = WorldInScreen_sX;
        double startGridY = WorldInScreen_sY;
        double EndGridX = WorldInScreen_eX;
        double EndGridY = WorldInScreen_eY;
        
        
        double [] starts = new double[4]; 
        starts[0] = startGridX - ((startGridX % 100)); //startLineX_BIGSQ
        starts[1] = startGridX - ((startGridX % 20));  //startLineX_SMALLSQ
        starts[2] = startGridY - ((startGridY % 100)); // startLineY_BIGSQ
        starts[3] = startGridY - ((startGridY % 20));  // startLineY_SMALLSQ

        g.setStroke(Color.GRAY);
        g.setLineWidth(1.2);

        for(double y = starts[2]; y <= EndGridY; y += 100)
            g.strokeLine(WorldToScreen_X(startGridX), WorldToScreen_Y(y), WorldToScreen_X(EndGridX), WorldToScreen_Y(y));
        for(double x = starts[0]; x <= EndGridX; x += 100)
            g.strokeLine(WorldToScreen_X(x), WorldToScreen_Y(startGridY), WorldToScreen_X(x), WorldToScreen_Y(EndGridY));
        
        g.setLineWidth(0.5);

        for(double y = starts[3]; y <= EndGridY; y += 20) 
            g.strokeLine(WorldToScreen_X(startGridX), WorldToScreen_Y(y), WorldToScreen_X(EndGridX), WorldToScreen_Y(y));
        for(double x = starts[1]; x <= EndGridX; x += 20) 
            g.strokeLine(WorldToScreen_X(x), WorldToScreen_Y(startGridY), WorldToScreen_X(x), WorldToScreen_Y(EndGridY));
    }


    /*for(int k = 0; k < 4; k++ ) {

            double step = k % 2 == 0 ? 100 : 20;
            double val = (k >= 2 ? startGridY : startGridX) - ((k >= 2 ? startGridY : startGridX) % step);
            double end = k % 2 == 0 ? startGridY : startGridX;

            for(double i = val; i <= end; i += step) {
                if(k < 2) 
                g.strokeLine(WorldToScreen_X(i), WorldToScreen_Y(startGridY), WorldToScreen_X(i), WorldToScreen_Y(EndGridY));
                else
                    g.strokeLine(WorldToScreen_X(startGridX), WorldToScreen_Y(i), WorldToScreen_X(EndGridX), WorldToScreen_Y(i));
            }
        }*/
}

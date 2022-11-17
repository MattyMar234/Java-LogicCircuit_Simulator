package Java.Main;

import java.util.LinkedList;

import Java.Component.IntegratedCircuit;
import Java.Component.Pin;
import Java.Component.Point;
import Java.Component.Wire;
import Java.Component.WireNode;
import Java.Component.WiresNetWork;
import Java.Main.ApplicationPageController.SimulationGlobalParametre;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Camera 
{
    private static final double MAX_ZOOM = 1;
    private static final double MIN_ZOOM = 1;
    private static final double ScaleStep = 0.010f;

    public LinkedList<SimulationObject> SimulationObjectList = new LinkedList<SimulationObject>();
    public LinkedList<IntegratedCircuit> RenderedObjectList = new LinkedList<IntegratedCircuit>();
    public LinkedList <WiresNetWork> wiresNetwork = new LinkedList<>();

    private Canvas canvas;

    protected double canvasWidth;
    protected double canvasHeight;

    protected double mapOffsetX = 0.0f;
    protected double mapOffsetY = 0.0f;
    public double scale = 1.0f;

    protected double WorldInScreen_sX;
    protected double WorldInScreen_sY;
    protected double WorldInScreen_eX;
    protected double WorldInScreen_eY;

    protected double startPanX = 0.0f;
    protected double startPanY = 0.0f;
    protected double LastPanX = 0.0f;
    protected double LastPanY = 0.0f;

    protected double fMouseX = 0.0f;
    protected double fMouseY = 0.0f;

    protected Boolean IS_DRAGGING = false;
    protected boolean MOVE_UP = false;
    protected boolean MOVE_DOWN = false;
    protected boolean MOVE_LEFT = false;
    protected boolean MOVE_RIGHT = false;
    protected boolean ZOOM   = false;
    protected boolean DEZOOM = false;

    protected Pin selectedPin;
    protected SimulationObject movingObject;
    
    

    public Camera(Canvas canvas) {
        this.canvas = canvas;
    }

    public Camera() 
    {
        
    }


    protected void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    protected void update() {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        WorldInScreen_sX = ScreenToWorld_X(0);
        WorldInScreen_sY = ScreenToWorld_Y(0);
        WorldInScreen_eX = ScreenToWorld_X(canvasWidth);
        WorldInScreen_eY = ScreenToWorld_Y(canvasHeight);

    }

    public double gridAllingOffset(Point p, char axis) 
    {
        double distance = 0;

        if(axis == 'x') {
            distance = ScreenToWorld_X(p.X) % SimulationGlobalParametre.SmallGridSquare;
        }
        else if(axis == 'y') {
            distance = ScreenToWorld_Y(p.Y) % SimulationGlobalParametre.SmallGridSquare;
        }
        else {
            return 0.0f;
        }
            
        
        if(distance < SimulationGlobalParametre.SmallGridSquare / 2)
            return -distance;
        else
            return SimulationGlobalParametre.SmallGridSquare - distance;
    }



    @FXML
    void OnMousePressed(MouseEvent event) 
    {  
        if(event.getButton() == MouseButton.PRIMARY) {
            
        }
        else if(event.getButton() == MouseButton.SECONDARY) {

        }
        else if(event.getButton() == MouseButton.MIDDLE) {
            IS_DRAGGING = true; 
            startPanX = event.getX();
            startPanY = event.getY();           
        }   
    }

    @FXML
    void OnMouseReleased(MouseEvent event) 
    {
        if(event.getButton() == MouseButton.PRIMARY) {

            if(ApplicationPageController.userMode == ApplicationPageController.UserMode.PLACING_COMPONENT) 
            {
                if(ApplicationPageController.userOperation == ApplicationPageController.UserOperation.DEPLOY_COMPONENT) {
                    ApplicationPageController.userOperation = ApplicationPageController.UserOperation.NONE;
                }
            }
            if(ApplicationPageController.userMode == ApplicationPageController.UserMode.WIRING) 
            {
                if(ApplicationPageController.userOperation == ApplicationPageController.UserOperation.NONE) {
                    for (IntegratedCircuit chip : RenderedObjectList) {
                        for(int i = 0; i < chip.PinCount(); i++) {
                            Pin p = chip.getPin(i);
    
                            if(p.isHovered()) {
                                selectedPin = p;
                                ApplicationPageController.userOperation = ApplicationPageController.UserOperation.START_WRIRING;
                                return;
                            }
                        }
                    }
                }
                else if(ApplicationPageController.userOperation == ApplicationPageController.UserOperation.START_WRIRING) {
                    for (IntegratedCircuit chip : RenderedObjectList) {
                        for(int i = 0; i < chip.PinCount(); i++) {
                            Pin p = chip.getPin(i);
    
                            if(p.isHovered() && p != selectedPin) {  
                                
                                WiresNetWork w = new WiresNetWork();

                                w.addNode(p);
                                w.addNode(selectedPin);

                                wiresNetwork.add(w);

                                ApplicationPageController.userOperation = ApplicationPageController.UserOperation.NONE;
                                return;
                            }
                        }
                    }

                    WiresNetWork w = new WiresNetWork();
                    Point p = new Point(fMouseX, fMouseY);
                    ScreenToWorld(p);

                    w.addNode(new WireNode(ScreenToWorld_X(p.X + gridAllingOffset(p, 'x')), ScreenToWorld_Y(p.Y + gridAllingOffset(p, 'y'))));
                    w.addNode(selectedPin);
                    wiresNetwork.add(w);
                    ApplicationPageController.userOperation = ApplicationPageController.UserOperation.NONE;
                }
            }
        }
        else if(event.getButton() == MouseButton.SECONDARY) {

        }
        else if(event.getButton() == MouseButton.MIDDLE) {
            IS_DRAGGING = false;
        }   
    }

    @FXML
    void OnDragged(MouseEvent event) 
    {
        if(IS_DRAGGING) 
        {
            LastPanX = mapOffsetX;
            LastPanY = mapOffsetY;
            mapOffsetX -= (event.getX() - startPanX);
            mapOffsetY -= (event.getY() - startPanY);

            fMouseX = startPanX = event.getX();
            fMouseY = startPanY = event.getY();  

        
            double DeltaX = (mapOffsetX - LastPanX);
            double DeltaY = (mapOffsetY - LastPanY);

            WorldInScreen_sX = ScreenToWorld_X(0);
            WorldInScreen_sY = ScreenToWorld_Y(0);
            WorldInScreen_eX = ScreenToWorld_X(canvasWidth);
            WorldInScreen_eY = ScreenToWorld_Y(canvasHeight);

            //System.out.println("DX: " + DX + " DY: " + DY + " lineOffsetX: " + lineOffsetX + " lineOffsetY: " + lineOffsetY);
        
        }
    }

    @FXML
    void OnMouseMoving(MouseEvent event) 
    {
        fMouseX = (event.getX());
        fMouseY = (event.getY());

        SimulationGlobalParametre.MouseX = ScreenToWorld_X(fMouseX);
        SimulationGlobalParametre.MouseY = ScreenToWorld_Y(fMouseY);
    }


    @FXML
    void GridZoom(ScrollEvent event)  {
        ScaleOperation(event.getDeltaY(), event);
    }

    private void ScaleOperation(double DeltaY, ScrollEvent event) {
        ScaleFunc(DeltaY, event.getX(), event.getY());
    }

    private void ScaleFunc(double DeltaY, double mx, double my)
    {
        boolean rescaleFigures = false;
        double MouseWorldX_BeforeZoom = ScreenToWorld_X(mx);
        double MouseWorldY_BeforeZoom = ScreenToWorld_Y(my);

        
        if(DeltaY < 0 && scale > 0.25){
            scale *= 1.0f - Camera.ScaleStep;
            rescaleFigures = true;   
        }
        else if(DeltaY > 0 && scale < 4) {
            scale *= 1.0f + Camera.ScaleStep;
            rescaleFigures = true;
        }

        if(rescaleFigures) 
        {
            double MouseWorldX_AfterZoom = ScreenToWorld_X(mx);
            double MouseWorldY_AfterZoom = ScreenToWorld_Y(my);

            mapOffsetX += (MouseWorldX_BeforeZoom - MouseWorldX_AfterZoom);
            mapOffsetY += (MouseWorldY_BeforeZoom - MouseWorldY_AfterZoom);

        
            /*for (SimulationObject comp : SimulationComponent) {
                comp.setX(comp.getX()/2);
                comp.setY(comp.getY()/2);
                comp.Rescale();
            }*/
        }

    


        //System.out.println("offsetX=" + gridOffsetX + ", offsetY=" + gridOffsetY);
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
        else if(event.getCode() == KeyCode.ESCAPE) {
            ApplicationPageController.userOperation = ApplicationPageController.UserOperation.NONE;
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
            mapOffsetX -= 10;
        }
        if(MOVE_RIGHT) {
            mapOffsetX += 10;
        }
        if(MOVE_UP) {
            mapOffsetY -= 10;
        }
        if(MOVE_DOWN) {
            mapOffsetY += 10;
        }


        if(DEZOOM) {
            ScaleFunc(+1, fMouseX ,fMouseY);
        }
        if(ZOOM) {
            ScaleFunc(-1, fMouseX ,fMouseY);
        }
    }



    //per passare delle coordinate del mondo alle coordinate del monitor
    public double WorldToScreen_X(double fworldX) {
        return (fworldX - mapOffsetX) * scale;
    }

    public double WorldToScreen_Y(double fworldY) {
        return (fworldY - mapOffsetY) * scale;
    }


    public double ScreenToWorld_X(double screenX) {
        return (screenX / scale) + mapOffsetX;
    }

    public double ScreenToWorld_Y(double screenY) {
        return (screenY / scale) + mapOffsetY;
    }


    public Point WorldToScreen(Point point) {
        return new Point((point.X - mapOffsetX) * scale, (point.Y - mapOffsetY) * scale);
    }

    public void  ScreenToWorld(Point point) {
        point.X = (point.X / scale) + mapOffsetX;
        point.Y = (point.Y / scale) + mapOffsetY;
    }


    


    




    
}

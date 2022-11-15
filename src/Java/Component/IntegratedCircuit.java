package Java.Component;

import Java.Main.Camera;
import Java.Main.SimulationObject;
import Java.Main.ApplicationPageController.SimulationGlobalParametre;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class IntegratedCircuit extends SimulationObject 
{
    private static final Paint ICPackageColor = Color.rgb(40,40,40);
    
    protected Point[] drawingPoints = new Point[3];
    protected Pin[] pinList;
    protected String IcSigle;

    private int IC_PinNumber;
    private int PinRowElement;

    protected double halfSquare = SimulationGlobalParametre.SmallGridSquare/2;
    protected double startRowOffset = (SimulationGlobalParametre.SmallGridSquare/2) + SimulationGlobalParametre.SmallGridSquare/4;
    protected double LenghtX;
    protected double LenghtY;


    public IntegratedCircuit(double x, double y, int pinNumber, String IcSigle) {
        super(x, y);

        IC_PinNumber = pinNumber;
        PinRowElement = pinNumber/2;
        this.IcSigle = IcSigle;

        LenghtX = SimulationGlobalParametre.SmallGridSquare*5;
        LenghtY = SimulationGlobalParametre.SmallGridSquare*(PinRowElement + 1);


        drawingPoints[0] = new Point(x, y);
        drawingPoints[1] = new Point(x + LenghtX, y + LenghtY);
        drawingPoints[2] = new Point(x + LenghtX/2 + 10, y + LenghtY/2 + 40);

        pinList = new Pin[pinNumber];

        for(int i = 0; i < pinList.length; i++) {
            pinList[i] = new Pin(calculatePinPosX(i), calculatePinPosY(i), 0);
        }
    }

    @Override
    public void Draw(GraphicsContext g, Camera c) 
    {
        //ricalcolo i punti
        drawingPoints[0] = new Point(WorldPosX, WorldPosY);
        drawingPoints[1] = new Point(WorldPosX + LenghtX, WorldPosY + LenghtY);
        drawingPoints[2] = new Point(WorldPosX + LenghtX/2 + 10, WorldPosY + LenghtY/2 + (IcSigle.length()/2) * 10);
    
        Point A = c.WorldToScreen(drawingPoints[0]);
        Point B = c.WorldToScreen(drawingPoints[1]);
        Point C = c.WorldToScreen(drawingPoints[2]);

        g.setFill(ICPackageColor);
        g.fillRect(A.X, A.Y, B.X - A.X, B.Y - A.Y);

        g.setFill(Color.WHITE);
        g.rotate(-90);
        g.setFont(Font.font ("Verdana", 20*c.scale));
        g.fillText(IcSigle, -C.Y, C.X);
        g.rotate(90);

        g.setFill(Color.rgb(100,100,100));

        for (Pin p : pinList) {
            p.Draw(g, c);
        }
    }

    @Override
    public void setX(double x) {
        super.setX(x);

        for(int i = 0; i < pinList.length; i++) 
            pinList[i].setX(calculatePinPosX(i));
        }

    @Override
    public void setY(double y) {
        super.setY(y);
        
        for(int i = 0; i < pinList.length; i++) 
            pinList[i].setY(calculatePinPosY(i));
    }


    public int PinCount() {
        return pinList.length;
    }


    public Pin getPin(int index) 
    {
        if(index < pinList.length)
            return pinList[index];
        return null;
    }


    public void MoveTo(double x, double y) {
        WorldPosX = x; 
        WorldPosY = y;

        for(int i = 0; i < pinList.length; i++) {
            pinList[i].setX(calculatePinPosX(i));
            pinList[i].setY(calculatePinPosY(i));
        }
    }

    @Override
    public void Update() {

    }

    protected double calculatePinPosX(int i) {
        return WorldPosX  + (i >= IC_PinNumber/2 ? LenghtX : - halfSquare);
    }

    protected double calculatePinPosY(int i) {
        return WorldPosY + SimulationGlobalParametre.SmallGridSquare*(i % PinRowElement) + startRowOffset;
    }
    
}

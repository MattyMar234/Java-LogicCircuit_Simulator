package Java.Component;

import Java.Main.ApplicationPageController;
import Java.Main.Camera;
import Java.Main.SimulationObject;
import Java.Main.ApplicationPageController.SimulationGlobalParametre;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

public class Pin extends SimulationObject 
{
    private Point P1;
    private Point P2;
    private Point Center;

    private int PinType;
    private double pinSize;
    private Rectangle Box;

    private boolean hovered = false;


    public Pin(double x, double y, int PinType) {
        super(x,y);

        this.PinType = PinType;
        this.pinSize = (SimulationGlobalParametre.SmallGridSquare/2);

        P1 = new Point(x,y);
        P2 = new Point(x + pinSize, y + pinSize);  
        Center = new Point(x + pinSize/2, y + pinSize/2);
    }

    @Override
    public void Update() {

    }
    @Override
    public void Draw(GraphicsContext g, Camera c) {

        //System.out.println("Vx: " +  V1.x + "  Vy: " +  V1.y + "  PX: " + SimulationParametre.MouseX + "  PY: " + SimulationParametre.MouseY);
        if(isHovered())
            g.setFill(Color.WHITE);
        else
            g.setFill(Color.GRAY);

        Point A = c.WorldToScreen(P1);
        Point B = c.WorldToScreen(P2);

        g.fillRect(A.X, A.Y, B.X - A.X, B.Y - A.Y);
    }

    public Point getCenetr() {
        return new Point(Center);
    }

    


    @Override
    public void setX(double x) {
        super.setX(x);

        P1.X = x;
        P2.X = x + pinSize;
        Center.X = x + pinSize/2;
    }

    @Override
    public void setY(double y) {
        super.setY(y);

        P1.Y = y;
        P2.Y = y + pinSize;
        Center.Y = y + pinSize/2;
    }

    public boolean isHovered() {
        return SimulationGlobalParametre.MouseX >= P1.X && SimulationGlobalParametre.MouseY >= P1.Y && SimulationGlobalParametre.MouseX <= P2.X && SimulationGlobalParametre.MouseY <= P2.Y;
    }
}

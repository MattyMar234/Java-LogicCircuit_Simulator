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

public class Pin extends WireNode 
{
    private int PinType;
    private double pinSize;
    private Rectangle Box;

    private boolean hovered = false;


    public Pin(double x, double y, int PinType) {
        super(x,y);

        this.PinType = PinType;
        this.pinSize = (SimulationGlobalParametre.SmallGridSquare/2);

        drawingPoints[FistPointIndex]   = new Point(x,y);
        drawingPoints[SecondPointIndex] = new Point(x + pinSize, y + pinSize);;
        drawingPoints[CenetrPointIndex] = new Point(x + pinSize/2, y + pinSize/2);;
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

        Point A = c.WorldToScreen(drawingPoints[FistPointIndex]);
        Point B = c.WorldToScreen(drawingPoints[SecondPointIndex]);

        g.fillRect(A.X, A.Y, B.X - A.X, B.Y - A.Y);
    }



    


    @Override
    public void setX(double x) {
        super.setX(x);

        drawingPoints[FistPointIndex].X = x;
        drawingPoints[SecondPointIndex].X = x + pinSize;
        drawingPoints[CenetrPointIndex].X = x + pinSize/2;
    }

    @Override
    public void setY(double y) {
        super.setY(y);

        drawingPoints[FistPointIndex].Y = y;
        drawingPoints[SecondPointIndex].Y = y + pinSize;
        drawingPoints[CenetrPointIndex].Y = y + pinSize/2;
    }

}

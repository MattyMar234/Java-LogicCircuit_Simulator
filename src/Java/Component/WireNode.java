package Java.Component;

import Java.Main.ApplicationPageController;
import Java.Main.Camera;
import Java.Main.SimulationObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WireNode extends SimulationObject {

    private static double diametre = 12.0f;
    public WiresNetWork ownNetWork;

    public WireNode(double x, double y) {
        super(x, y);

        drawingPoints[FistPointIndex]   = new Point(x - diametre/2, y - diametre/2);
        drawingPoints[SecondPointIndex] = new Point(x + diametre/2, y + diametre/2);
        drawingPoints[CenetrPointIndex] = new Point(x, y);
        ownNetWork = null;

        //System.out.println("secondo nodo: " + drawingPoints[CenetrPointIndex].toString());
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Draw(GraphicsContext g, Camera C)
    {
        double RescaledDiametre = diametre * C.scale;

        Point A = new Point(drawingPoints[CenetrPointIndex]);
        A.X -= diametre/2;
        A.Y -= diametre/2;
        A = C.WorldToScreen(A);

        if(isHovered())
            g.setFill(Color.WHITE);
        else
            g.setFill(ownNetWork.getColor());

        g.fillOval(A.X , A.Y, RescaledDiametre, RescaledDiametre);
    }

    public Point getCenetr() {
        return new Point(drawingPoints[CenetrPointIndex]);
    }

}



package Java.Main;

import Java.Component.Point;
import javafx.scene.canvas.GraphicsContext;

public abstract class SimulationObject implements SimulationObjectMethod
{
    public static int FistPointIndex = 0;
    public static int SecondPointIndex = 1;
    public static int CenetrPointIndex = 2;
    public Point[] drawingPoints;
    protected double WorldPosX;
    protected double WorldPosY;
    protected double Rotation;




    public SimulationObject(double x, double y) {
        this.WorldPosX = x;
        this.WorldPosY = y;

        this.drawingPoints = new Point[3];
    }

    public boolean isHovered()
    {
        Point P1 = drawingPoints[FistPointIndex];
        Point P2 = drawingPoints[SecondPointIndex];

        return ApplicationPageController.SimulationGlobalParametre.MouseX >= P1.X && ApplicationPageController.SimulationGlobalParametre.MouseY >= P1.Y && ApplicationPageController.SimulationGlobalParametre.MouseX <= P2.X && ApplicationPageController.SimulationGlobalParametre.MouseY <= P2.Y;
    }

    public double getX() {
        return this.WorldPosX;
    }

    public double getY() {
        return this.WorldPosY;
    }

    public void setX(double x) {
        this.WorldPosX = x;
    }

    public void setY(double y) {
        this.WorldPosY = y;
    }
}

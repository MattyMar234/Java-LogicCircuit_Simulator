package Java.Main;

import javafx.scene.canvas.GraphicsContext;

public abstract class SimulationObject implements SimulationObjectMethod
{
    protected double WorldPosX;
    protected double WorldPosY;
    protected double Rotation;


    public SimulationObject(double x, double y) {
        this.WorldPosX = x;
        this.WorldPosY = y;
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

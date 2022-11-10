package Java.Main;

import javafx.scene.canvas.GraphicsContext;

public abstract class SimulationObject implements  SimulationObjectMethod{

    protected double PosX;
    protected double PosY;
    protected double Rotation;

    public SimulationObject(double x, double y) {
        this.PosX = x;
        this.PosY = y;
    }

    public double getX() {
        return this.PosX;
    }

    public double getY() {
        return this.PosY;
    }

    public void setX(double x) {
        this.PosX = x;
    }

    public void setY(double y) {
        this.PosY = y;
    }

    public void Traslate(double tx, double ty) {
        this.PosX += tx;
        this.PosY += ty;
    }


}

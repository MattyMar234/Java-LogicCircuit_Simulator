package Java.Component;

import Java.Main.SimulationObject;
import javafx.scene.canvas.GraphicsContext;

public class Pin extends SimulationObject {

    private int PinType;

    public Pin(double x, double y, int PinType) {
        super(x,y);

        this.PinType = PinType;
    }

    @Override
    public void Update() {

    }
    @Override
    public void Draw(GraphicsContext g) {
        g.fillRect(PosX, PosY, 8,8);
    }
}

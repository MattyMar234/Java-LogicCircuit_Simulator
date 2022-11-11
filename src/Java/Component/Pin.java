package Java.Component;

import Java.Main.SimulationObject;
import Java.Main.ApplicationPageController.SimulationParametre;
import javafx.scene.canvas.GraphicsContext;

public class Pin extends SimulationObject {

    private int PinType;
    private double pinSize;

    public Pin(double x, double y, int PinType) {
        super(x,y);

        this.PinType = PinType;
        this.pinSize = (SimulationParametre.baseSquareSize/2)*SimulationParametre.scaleValue;
    }

    @Override
    public void Update() {

    }
    @Override
    public void Draw(GraphicsContext g) {
        g.fillRect(PosX, PosY, pinSize, pinSize);
    }

    @Override
    public void Rescale() {
        pinSize = (SimulationParametre.baseSquareSize/2)*SimulationParametre.scaleValue;
    }
}

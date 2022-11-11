package Java.Component;

import Java.Main.ApplicationPageController;
import Java.Main.SimulationObject;
import Java.Main.ApplicationPageController.SimulationParametre;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

public class Pin extends SimulationObject {

    private int PinType;
    private double pinSize;
    private Rectangle Box;

    private boolean hovered = false;

    public Pin(double x, double y, int PinType) {
        super(x,y);

        this.PinType = PinType;
        this.pinSize = (SimulationParametre.baseSquareSize/2)*SimulationParametre.scaleValue;
        Box = new Rectangle(x, y, pinSize, pinSize);

        Box.setOnMouseMoved(e -> {

            @Override
            public void handle(MouseEvent t) {

            }
        });

    }

    @Override
    public void Update() {

    }
    @Override
    public void Draw(GraphicsContext g) {
        if(hovered) {
            g.setFill(Color.RED);
        }
        else {
            g.setFill(Color.GRAY);
        }

        g.fillRect(PosX, PosY, pinSize, pinSize);
    }

    @Override
    public void Rescale() {
        pinSize = (SimulationParametre.baseSquareSize/2)*SimulationParametre.scaleValue;

    }

    @Override
    public void Traslate(double tx, double ty) {
        super.Traslate(tx,ty);
        Box.setX(Box.getX() + tx);
        Box.setY(Box.getY() + ty);
    }
}

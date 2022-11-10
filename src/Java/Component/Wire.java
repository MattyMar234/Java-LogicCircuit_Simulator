package Java.Component;

import Java.Main.SimulationObject;
import Java.Main.SimulationObjectMethod;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wire implements SimulationObjectMethod {


    public Wire() {
    }

    @Override
    public void Update() {

    }


    @Override
    public void Draw(GraphicsContext g) {
        
        g.setFill(Color.GREEN);
        g.setStroke(Color.BLUE);
        g.setLineWidth(5);
        g.strokeLine(40, 10, 10, 40);
        
    }
}

package Java.Component;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wire {


    public Wire() {

    }

    public void Update() {

    }

    public void Paint(GraphicsContext g) {

        g.setFill(Color.GREEN);
        g.setStroke(Color.BLUE);
        g.setLineWidth(5);
        g.strokeLine(40, 10, 10, 40);

    }
}

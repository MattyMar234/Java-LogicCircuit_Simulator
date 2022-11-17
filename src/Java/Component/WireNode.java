package Java.Component;

import Java.Main.Camera;
import Java.Main.SimulationObject;
import javafx.scene.canvas.GraphicsContext;

public class WireNode extends SimulationObject {

    private static double radius = 12.0f;
    public Point center;
    public WiresNetWork ownNetWork;

    public WireNode(double x, double y) {
        super(x, y);
        
        center = new Point(x, y);
        ownNetWork = null;
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Draw(GraphicsContext g, Camera C) {
        
        Point A = C.WorldToScreen(center);
        g.setFill(ownNetWork.getColor());

        g.fillOval(A.X - radius/2, A.Y - radius/2, radius, radius);

        
    }
    
}

package Java.Component;

import java.util.ArrayList;

import Java.Main.Camera;
import Java.Main.SimulationObject;
import Java.Main.SimulationObjectMethod;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wire implements SimulationObjectMethod {

    private class NetworkNode {

        public Point point;
        public ArrayList<NetworkNode> adiacenze = new ArrayList<NetworkNode>();

        public NetworkNode(Pin p) {
            this.point = p.getCenetr();
        }
        

    }

    private ArrayList <NetworkNode> netWorkPins = new ArrayList<NetworkNode>();


    public Wire(Pin... p) {
        for(Pin pin : p) {
            netWorkPins.add(new NetworkNode(pin));
        }
    }

    @Override
    public void Update() {

    }


    @Override
    public void Draw(GraphicsContext g, Camera c) 
    {
        g.setFill(Color.GREEN);
        g.setStroke(Color.GREEN);
        g.setLineWidth(3);

        for(int i = 0; i < netWorkPins.size() - 1 && netWorkPins.size() >= 2; i++) {
            Point A = c.WorldToScreen(netWorkPins.get(i).point);
            Point B = c.WorldToScreen(netWorkPins.get(i + 1).point);

            g.strokeLine(A.X, A.Y, B.X, B.Y);
        }
        
        
        
        
        
    }

}

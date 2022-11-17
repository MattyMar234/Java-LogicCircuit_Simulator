package Java.Component;

import java.util.LinkedList;

import Java.Main.Camera;
import Java.Main.SimulationObjectMethod;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WiresNetWork implements SimulationObjectMethod{

    //INPUTS

    //OUTPUT

    protected Paint currentNetWPaint = Color.rgb(200,0,0);
    protected LinkedList<WireNode> netWorkNodes = new LinkedList<WireNode>();

    public WiresNetWork() {

    }

    public Paint getColor() {
        return currentNetWPaint;
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Draw(GraphicsContext g, Camera C) {

        if(netWorkNodes.size() > 1) {
            for(int i = 0; i < netWorkNodes.size() - 1; i++) {

                WireNode n1 = netWorkNodes.get(i);
                WireNode n2 = netWorkNodes.get(i + 1);

                Point A = getNodeCenter(n1,C);
                Point B = getNodeCenter(n2,C);

                

                g.setStroke(currentNetWPaint);
                g.setLineWidth(2.0f);
                g.strokeLine(A.X, A.Y, B.X, B.Y);
            
                if(n1 instanceof WireNode) n1.Draw(g, C);
                if(n2 instanceof WireNode) n2.Draw(g, C);

            }
        }
        
        //drawWire 

        //darw point
        
    }

    private Point getNodeCenter(WireNode n, Camera c) {
        if(n instanceof Pin) 
        return c.WorldToScreen((((Pin)n).getCenetr()));
        else 
            
            return c.WorldToScreen(n.center);
    }

    public void addNode(WireNode node) {
        netWorkNodes.add(node);

        node.ownNetWork = this;
    }
    
}

package Java.Memory;

import Java.Component.Pin;
import Java.Main.SimulationObject;
import Java.Main.SimulationObjectMethod;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AT28c512 extends MemoryObject {

    /*private Pin [] dataPins    = new Pin[8];
    private Pin [] addressPins = new Pin[16];
    private Pin [] controlsPins = new Pin[3];*/

    private Pin[] PinList = new Pin[32];

    private int IC_PinNumber = 32;
    private double LenghtX = 75;
    private double LenghtY = 250;
    private double pinStep = (LenghtY - 20) / (IC_PinNumber/2);

    //private String Name = this.getClass().getName().split(".")[0];

    public AT28c512(double x, double y, String fileHEX) {
        super(x, y, 64, fileHEX);

        for(int i = 0; i < PinList.length; i++) {

            double Px = PosX - 4 + (i >= IC_PinNumber/2 ? LenghtX : 0);
            double Py = PosY + pinStep*(i%(IC_PinNumber/2) + 1) - 4;

            PinList[i] = new Pin(Px, Py, 0);
        }
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(GraphicsContext g)
    {
        g.setFill(Color.rgb(40,40,40));
        g.fillRect(PosX, PosY, LenghtX, LenghtY);

        g.setFill(Color.WHITE);
        g.rotate(-90);
        g.fillText("AT28c512", -PosX, PosY);
        g.rotate(90);

        g.setFill(Color.rgb(100,100,100));

        for (Pin p : PinList) {
            p.Draw(g);
        }
    }

    @Override
    public void Traslate(double tx, double ty) {
        super.Traslate(tx,ty);

        for (Pin p : PinList) {
            p.Traslate(tx,ty);
        }
    }
}

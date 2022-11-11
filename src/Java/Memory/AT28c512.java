package Java.Memory;

import Java.Component.Pin;
import Java.Main.SimulationObject;
import Java.Main.SimulationObjectMethod;
import Java.Main.ApplicationPageController.SimulationParametre;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AT28c512 extends MemoryObject {

    private Pin[] PinList = new Pin[32];
    /*private Pin [] dataPins    = new Pin[8];
    private Pin [] addressPins = new Pin[16];
    private Pin [] controlsPins = new Pin[3];*/

    static final double baseSizeX = SimulationParametre.baseSquareSize*4;
    static final double baseSizeY = SimulationParametre.baseSquareSize*17;

    
    private int IC_PinNumber = 32;
    private double LenghtX = baseSizeX*SimulationParametre.scaleValue;
    private double LenghtY = baseSizeY*SimulationParametre.scaleValue;
    private double pinStep = (LenghtY - SimulationParametre.baseSquareSize) / (IC_PinNumber/2);
    private double halfSquare = SimulationParametre.baseSquareSize/2;
    private double SquareDiv4 = SimulationParametre.baseSquareSize/4;



    public AT28c512(double x, double y, String fileHEX) {
        super(x, y, 64, fileHEX);

    
        for(int i = 0; i < PinList.length; i++) {
            PinList[i] = new Pin(0, 0, 0);
        }

        Rescale();
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
        g.fillText("AT28c512", -PosY, PosX);
        g.rotate(90);

        g.setFill(Color.rgb(100,100,100));

        for (Pin p : PinList) {
            p.Draw(g);
        }
    }

    @Override
    public void setX(double x) {
        super.setX(PosX);

        for(int i = 0; i < PinList.length; i++) 
            PinList[i].setX(PosX - SimulationParametre.baseSquareSize/2 + (i >= IC_PinNumber/2 ? LenghtX : 0));
    }

    @Override
    public void setY(double y) {
        super.setY(PosY);

        for(int i = 0; i < PinList.length; i++) 
            PinList[i].setY(PosY + pinStep*(i%(IC_PinNumber/2) + 1) - halfSquare);
    }

    @Override
    public void Traslate(double tx, double ty) {
        super.Traslate(tx,ty);

        for (Pin p : PinList) {
            p.Traslate(tx,ty);
        }
    }

    @Override
    public void Rescale() 
    {
        
        LenghtX = baseSizeX*SimulationParametre.scaleValue;
        LenghtY = baseSizeY*SimulationParametre.scaleValue;
        pinStep = (LenghtY - SimulationParametre.baseSquareSize*SimulationParametre.scaleValue) / (IC_PinNumber/2);
        halfSquare = (SimulationParametre.baseSquareSize/2) * SimulationParametre.scaleValue;
        SquareDiv4 = (SimulationParametre.baseSquareSize/4) * SimulationParametre.scaleValue;

        System.out.println("============= EEPROM =============");
        System.out.println("PIN step: " + pinStep);
        System.out.println("LenghtX: " + LenghtX);
        System.out.println("LenghtY: " + LenghtY);  
        System.out.println("PosX: " + PosX);
        System.out.println("Posy: " + PosY);
        

        for(int i = 0; i < PinList.length; i++) 
        {
            double Px = PosX + (halfSquare)*(i >= IC_PinNumber/2 ? 0 : -1) + (i >= IC_PinNumber/2 ? LenghtX : 0);
            double Py = PosY + pinStep*(i%(IC_PinNumber/2) + 1) - SquareDiv4;

            PinList[i].setX(Px);
            PinList[i].setY(Py);
            PinList[i].Rescale();

            
        }
        
    }
}

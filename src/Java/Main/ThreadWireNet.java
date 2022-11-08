package Java.Main;

import Java.Component.Wire;
import javafx.scene.canvas.GraphicsContext;

public class ThreadWireNet extends Thread
{
    private boolean running;
    private double LastUpdate;
    private int FPS = 60;
    private double Framedelay;
    private ApplicationPageController controller;
    private GraphicsContext g;

    public ThreadWireNet(ApplicationPageController controller) {
        this.controller = controller;
        this.g = controller.getCanvas().getGraphicsContext2D();
        running = true;

        Framedelay = (1 / FPS) * 1000000000; //delay in nS
        LastUpdate = 0;
    }


    public void run() {

        while(running) {
            double time = System.nanoTime();

            if(time - LastUpdate >= Framedelay) {
                LastUpdate = time;

                for (Wire w : controller.WiresList) {
                    w.Paint(g);
                    w.Update();
                }
            }
            else {
                for (Wire w : controller.WiresList ) {
                    w.Update();
                }
            }
        }
    }
}

package Java.Memory;

public class AT28c512 extends MemoryObject { 

    private Pin [] dataPins    = new Pin[8];
    private Pin [] addressPins = new Pin[16];
    private Pin [] controlsPins = new Pin[3];


    public AT28c512(int KB, String fileHEX) {
        super(KB, fileHEX);
    
    }
        
    
}

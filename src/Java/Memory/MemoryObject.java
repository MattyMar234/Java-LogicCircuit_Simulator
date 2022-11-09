package Java.Memory;

public abstract class MemoryObject {

    //protected InternalBus addressBus;
    //protected InternalBus dataBus;
    //protected InternalBus controllBus;

    private byte [][] CellsMatrix;
    private double MemoryCapacity;
    public double memoryUsed;
    private Boolean WriteEnabled;
    private static int rowSize = (int)Math.pow(2,10);

    /*
     * public double capacity {
            get { return MemoryCapacity;}
        }
     */

     //ogni riga è composta da un 1KB
    public MemoryObject(int KB, String fileHEX) 
    {
        if(KB > 1024) KB = 1024;

        CellsMatrix = new byte [KB][rowSize];
        MemoryCapacity = rowSize * KB;
        WriteEnabled = false;
        memoryUsed = 0;

        //HexReader reader = new HexReader("C:\\Users\\Utente\\Desktop\\C - C# - C++\\C#\\68k_CPU_emulator\\Operating_System_V7.hex", this);
    }


    public byte read(double position) {
            
        //non possiedo quell'indirizzo, è come se ricominciassi da capo
        position = position % MemoryCapacity;

        int row = (int) position / rowSize;
        int col = (int) position - row*rowSize;

        return this.CellsMatrix[row][col];
    }


    public void write(double position, Byte data) 
    {
        //non posso scrivere
        if(!WriteEnabled)
            return;

        //Console.WriteLine("writing in memory: " + position);

        //non possiedo quell'indirizzo, è come se ricominciassi da capo
        position = position % MemoryCapacity;

        int row = (int) position / rowSize;
        int col = (int) position - row*rowSize;

        this.CellsMatrix[row][col] = data;
    }


    public void EnableWritiningProtection() {
        WriteEnabled = true;
    }

    public void DisableWritiningProtection() {
        WriteEnabled = false;
    }

    

    

    @Override
    public String toString()
    {
        String str = "";
        char [] buffer = new char[16];

        for(int i = 0; i < memoryUsed; i++) {
            if(i%16 == 0) 
            {
                str += "\n";
                for(int j = 0; j < 5; j++) {
                    str += HexToChars((byte)((i >> (32 - (8 * j))) & 0xFF));
                }
                str += " | ";
            }

            byte b = read(i);
            buffer[i%16] = (char)b;
            str += "[" + HexToChars(b) + "] ";

            if(i%16 == 15) {
                str += "  |  ";
                for(int j = 0; j < 16; j++)
                    str += (buffer[j] >= 32 ? buffer[j] : '.' );
                str += "  |  ";
            }
        }

        return str;
    }

    private String HexToChars(byte b) {
        byte upper = (byte)(b >> 4);
        byte lower = (byte)(b & 0x0F);

        char ch1 = (char)(upper + (upper < 10 ? 48 : 55));
        char ch2 = (char)(lower + (lower < 10 ? 48 : 55));

        //Console.WriteLine("upper: " + ch1 + ", lower: " + ch2);
        
        String str = "";
        str += ch1;
        str += ch2;

        return str;
    }
    
}






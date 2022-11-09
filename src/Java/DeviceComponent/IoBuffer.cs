using DeviceComponenet;

namespace DeviceComponenet
{

    class IoBuffer {

        /*NOTA, I NUMERO MASSIMO DI PIN E' 64*/
        private static Byte maxPin = 64;

        List<Pin> pins = new List<Pin>();
        private Byte bufferMode;             //0 -> in, 1 -> out, 2 bi-directional
        private Boolean Connected   = true;  //false -> LOW , true -> HIGH
        private Boolean WorkingMode = true;  //false -> OUTPUT, true -> INPUT
        private UInt64 bufferDeviceValue;


        //accessibile solo dal dispositico che lo contiene
        public UInt64 DeviceOutputBuffer 
        {
            //imposta il valore dei pin di uscita
            set {
                bufferDeviceValue = value;
                //if it can be bi-dir or out  AND  is set as output
                if( bufferMode != 0 && !WorkingMode && Connected) writeBus(value);
            }

            //legge il valore che ho scritto dell BUFFER di output
            get {
                if(bufferMode == 0) // se solo INPUt
                    return 0x00;
                else {
                    return bufferDeviceValue;
                }
            }
        }

        //accessibile solo dal dispositico che lo contiene
        public UInt64 DeviceInputBuffer {
            get { 
                if(bufferMode == 0 && WorkingMode && Connected) {
                    return readBus(); //ritorna lo stato dei pin 
                }
                else 
                    return 0x00; //creare exception
            }
        }


        public IoBuffer(Byte mode, params Pin[] pins) 
        {
            this.bufferMode = mode;         //modalità del bus
            this.bufferDeviceValue = 0;     //valore di output
            this.Connected = false;         //lo diconetto dal sistema
            this.WorkingMode = true;        //di base sono input

            if(pins.Length >= IoBuffer.maxPin) {
                throw new ArgumentException("il massimo di pin è 64"); // ?
            }

            for (int i = 0; i < pins.Length && i < IoBuffer.maxPin; i++) {
                this.pins.Add(pins[i]);
            }
        }


        public void ConnectBus() {
            if(bufferMode != 0 && !WorkingMode) //as OUTPUT
                writeBus(bufferDeviceValue);

            this.Connected = true;
        }

        public void DisconnectBus() {
            if(bufferMode != 0 && !WorkingMode) //as OUTPUT
                writeBus(0x00);

            this.Connected = false;
        }

        private UInt64 readBus() 
        {
            UInt64 v = 0;
            Byte index = 0;

            //buff[]

            //Console.Write("Buffer: ");
            foreach(Pin pin in pins) {
                //Console.Write(pin.Value ? 1: 0);
                v |= ((UInt64)(pin.Value ? 1 : 0) << index++);
            }

            //Console.Write("  ");

            return v;
        }

        private void writeBus(UInt64 v) 
        {
            int index = 0;
            foreach(Pin pin in pins) {
               pin.Value = (((v >> index++) & 0x01) == 1)? true : false;
            }
        }

        public Boolean setBufferAs_INPUT() {
            return ChangeBufferDirection(true);
        }

        public Boolean setBufferAs_OUTPUT() {
            return ChangeBufferDirection(false);
        }

        private Boolean ChangeBufferDirection(Boolean dir) {
            if(this.bufferMode == 2) {
                this.WorkingMode = dir;

                foreach(Pin p in pins) {
                    p.changeMode(dir);
                }

                return true;
            }
            return false;
        }

        


    }

}
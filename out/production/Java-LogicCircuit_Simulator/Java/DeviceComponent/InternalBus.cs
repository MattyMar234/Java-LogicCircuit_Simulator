using DeviceComponenet;

namespace DeviceComponenet
{

    class InternalBus {

        List<Pin> pins = new List<Pin>();
        private Byte busMode;                //0 -> in, 1 -> out, 2 bi-directional
        private Boolean Operation = true;    //false -> LOW , true -> HIGH

        public UInt64 Busvalue {
            get { return readBus();}
            set { writeBus(value);}
        }

        public InternalBus(Byte mode, params Pin[] pins) 
        {
            this.busMode = mode;

            for (int i = 0; i < pins.Length; i++) {
                this.pins.Add(pins[i]);

                //HIGH impedenze
        
                //pins[i].Tristate_close();
                
            }
        }


        public void ConnectBus() {
            foreach(Pin pin in pins) {
         
            }
        }

        public void DisconnectBus() {
            foreach(Pin pin in pins) {
       
            }
        }

        private UInt64 readBus() 
        {
            UInt64 v = 0;
            Byte index = 0;

            //Console.Write("bus: ");
            foreach(Pin pin in pins) {
                //Console.Write(pin.Value ? 1: 0);
                v |= ((UInt64)(pin.Value ? 1 : 0) << index++);
            }

            //Console.Write("  ");

            return v;
        }

        private void writeBus(UInt64 v) {

            int index = 0;
            foreach(Pin pin in pins) {
               pin.Value = (((v >> index++) & 0x01) == 1)? true : false;
            }
        }

        


    }

}
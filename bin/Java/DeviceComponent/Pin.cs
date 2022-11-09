


namespace DeviceComponenet
{
    class Pin
    {
        public static Byte OUTPUT = 0;
        public static Byte INPUT = 1;
        public static Byte BI_Direction = 2;

        private Boolean state;         //0 LOW, 1 HIGH
        private Boolean Mode;          //0 OUTPUT, 1 INPUT
        private Byte WorkingMode;      //0 OUTPUT, 1 INPUT, 2 BI-DIRECTION

        private List<Wire> WireList = new List<Wire>();


        public Boolean Value {
            set { 
                if(Mode) state = value; //se è un input posso assegnarli li valore
            }
            get { 
                return state;
            }
        }

        public Boolean BufferWrite {
            set { 
                if(!Mode) state = value; //se è un output posso assegnarli li valore dal buffer
            }
            get { 
                return state;
            }
        }

        public Byte workingMode {
            get { 
                return WorkingMode;
            }
        }

        public Pin(Byte WorkingMode) 
        {
            this.WorkingMode = WorkingMode;
            this.Mode = (WorkingMode == 0 ? false : true);
        }


        public void Add_Wire_Network(Wire w) {
            this.WireList.Add(w);
        }

        public void Remove_Wire_Network(Wire w) {
            this.WireList.Remove(w);
        }

        public void RemoveAll_Wires_Network() {
            this.WireList.Clear();
        }

        public Boolean changeMode(Boolean newMode) {
            if(workingMode == 2) {
                Mode = newMode;

                if(!Mode) {
                    state = false;
                }


                return true;
            }
            return false;
        }

        public void toggle() {
            this.state = !this.state;
        }
    }

}
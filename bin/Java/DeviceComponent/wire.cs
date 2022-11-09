namespace DeviceComponenet
{
    class Wire {

        private List<Pin> net = new List<Pin>();
        private List<Pin> INPUT_pins = new List<Pin>();
        private List<Pin> OUTPUT_pins = new List<Pin>();

        public Wire(params Pin[] pinArray) {
            foreach(Pin p in pinArray)
                addElement(p);
            
            Console.WriteLine("[New Wire Created]");
        }

        public void addElement(Pin newPin) {
            net.Add(newPin);

            /*switch (newPin.Mode) {
                case 0: OUTPUT_pins.Add(newPin);        break;
                case 1: INPUT_pins.Add(newPin);         break;
                case 2:
                    INPUT_pins.Add(newPin);
                    OUTPUT_pins.Add(newPin);   
                break;
            }*/

        }

        public void removeElement(Pin PinToRemove) {
            net.Remove(PinToRemove);

            /*
            switch (PinToRemove.Mode) {
                case 0: OUTPUT_pins.Remove(PinToRemove);        break;
                case 1: INPUT_pins.Remove(PinToRemove);         break;
                case 2:
                    INPUT_pins.Add(PinToRemove);
                    OUTPUT_pins.Add(PinToRemove);   
                    break;
            }*/
        }

        public void update() {

            Boolean wireValue = false;
            Pin source = null;

            foreach(Pin output in OUTPUT_pins) {
                if(output.Value) {
                    wireValue = true;
                    source = output;            //mi salvo l'origine
                    break;
                }
            }

            foreach(Pin input in INPUT_pins) {
                //per evitare di fare un loop
                if(input != source) {
                    input.Value = wireValue;
                }
            }
        }

        public override string ToString()
        {
            string str = "Wire-Network:\n";
            int count = 1;

            foreach(Pin p in net) {
               string st = "?";

                switch (p.workingMode) {
                    case 0: st = "OUTPUT";        break;
                    case 1: st = "INPUT";         break;
                    case 2: st = "BI-DIRETIONAL"; break;
                }
               str += "[Pin"+ (count++) + "]: " + st + "\n";
            }
            return str;
        }
    }
}
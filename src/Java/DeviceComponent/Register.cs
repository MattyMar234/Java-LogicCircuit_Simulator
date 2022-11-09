


namespace DeviceComponenet
{
    class Register
    {
        private UInt32 value;
        private Byte bytes;


        public Register(Byte bytes)
        {
            this.bytes = bytes;
            value = 0;
        }

        public UInt32 getValue() {
            return value & (UInt32)(Math.Pow(256,bytes) - 1);
        }


        public void setValue(UInt32 v) {
            this.value = v;
        }


        public Byte getBit(Byte index) {
            return (Byte)((this.value >> index) & 0xFFFFFFFE);
        }


        public void setBit(Byte index, Boolean b) 
        {
            if(b) {
                this.value |= this.value | (UInt32)(1 << index);
            }   
            else {
                this.value &= (UInt32)(0xFF ^ (1 << index));
            }
        }       

    }

    class Register16_t : Register {
        
        public Register16_t() : base(2) 
        {}

        public new Boolean getBit(Byte index) {
            return base.getBit(index) == 1 ? true : false;
        }


        public new void setBit(Byte index, Boolean b) {
            base.setBit(index, b);
        }
    }

    class CPU_Control_Register : Register16_t
    {
        //T1 T0 S M 0 I2 I1 I0 0 0 0 X N Z V C

       public Boolean T1 { get { return getBit(15); } set { setBit(15, value); }}
       public Boolean T0 { get { return getBit(14); } set { setBit(14, value); }}
       public Boolean S  { get { return getBit(13); } set { setBit(13, value); }}
       public Boolean M  { get { return getBit(12); } set { setBit(12, value); }}

       public Boolean I2  { get { return getBit(10); } set { setBit(10, value); }}
       public Boolean I1  { get { return getBit(9); }  set { setBit(9, value);  }}
       public Boolean I0  { get { return getBit(8); }  set { setBit(8, value);  }}

       public Boolean X  { get { return getBit(4); } set { setBit(4, value); }}
       public Boolean N  { get { return getBit(3); } set { setBit(3, value); }}
       public Boolean Z  { get { return getBit(2); } set { setBit(2, value); }}
       public Boolean V  { get { return getBit(1); } set { setBit(1, value); }}
       public Boolean C  { get { return getBit(0); } set { setBit(0, value); }}



        public CPU_Control_Register() : base()
        {

        }

        public override string ToString()
        {
            string str = " ============ Status Register ============ \n";
            Byte traceMode = (Byte)(((Byte)((T1 ? 1 : 0) << 1)) | ((Byte)(T1 ? 1 : 0)));
            Byte IPM       = (Byte)(((Byte)((I2 ? 1 : 0) << 2)) | ((Byte)((I1 ? 1 : 0) << 1)) | ((Byte)(T0 ? 1 : 0)));
            Byte AS        = (Byte)(((Byte)((S ? 1 : 0) << 1)) | ((Byte)(M ? 1 : 0)));

            str += "[TRACE MODE]: ";
            switch(traceMode) {
                case 0: str += "NO TRACE"; break;
                case 1: str += "TRACE ON ANY INSTRUCTION"; break;
                case 2: str += "TRACE ON CHANGE OF FLOW"; break;
                case 3: str += "UNDEFINED"; break;
            }

            str += "\n[ACTIVE STACK]: ";

            switch(AS) {
                case 0: 
                case 1: str += "USP"; break;
                case 2: str += "ISP"; break;
                case 3: str += "MSP"; break;
            }

            str += "\n[INTERRUPT PRIORITY MASK]: " + IPM;
            str += "\n[EXTEND]: "   + (Byte)((Byte)(X ? 1 : 0));
            str += "\n[NEGATIVE]: " + (Byte)((Byte)(N ? 1 : 0));
            str += "\n[ZERO]: "     + (Byte)((Byte)(Z ? 1 : 0));
            str += "\n[OVERFLOW]: " + (Byte)((Byte)(V ? 1 : 0));
            str += "\n[CARRY]: "    + (Byte)((Byte)(C ? 1 : 0));
            str += "\n=========================================\n";
            


            return str;
        }
    }

}